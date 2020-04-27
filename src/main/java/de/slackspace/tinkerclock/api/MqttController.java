package de.slackspace.tinkerclock.api;

import java.awt.Color;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import de.slackspace.tinkerclock.logic.TimeLedProvider;

@Component
public class MqttController {

    private static final String OFF = "OFF";

    private static final String ON = "ON";

    private Log logger = LogFactory.getLog(getClass());

    private static final String COLOR_TOPIC = "eg/wordclock/rgb/set";

    private static final String COLOR_STATUS_TOPIC = "eg/wordclock/rgb/status";

    private static final String SWITCH_TOPIC = "eg/wordclock/light/switch";

    private static final String SWITCH_STATUS_TOPIC = "eg/wordclock/light/status";

    private IMqttClient client;

    private TimeLedProvider provider;

    @PostConstruct
    public void init() {
        String publisherId = "0f7f7719-1986-49cf-98b5-c81f84b82726";
        try {
            client = new MqttClient("tcp://192.168.0.26:1883", publisherId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            options.setUserName("");
            options.setPassword("".toCharArray());
            client.connect(options);
        } catch (MqttException e) {
            logger.error("Could not connect to mqtt server: " + e);
        }

        try {
            client.subscribe(COLOR_TOPIC, (topic, msg) -> {
                byte[] payload = msg.getPayload();

                String rawString = new String(payload);
                setColor(rawString);
            });

            client.subscribe(SWITCH_TOPIC, (topic, msg) -> {
                byte[] payload = msg.getPayload();

                String rawString = new String(payload);
                setSwitch(rawString);
            });
        } catch (MqttException e) {
            logger.error("Could not connect to mqtt server: " + e);
        }
    }

    public void setTimeLedProvider(TimeLedProvider provider) {
        this.provider = provider;
    }

    public void sendSwitchStatus(boolean isOn) {
        String msg = isOn ? ON : OFF;

        try {
            client.publish(SWITCH_STATUS_TOPIC, new MqttMessage(msg.getBytes()));
        } catch (MqttException e) {
            logger.error("Could not send switch status to mqtt server: " + e);
        }
    }

    public void sendColorStatus() {
        try {
            Color color = provider.getColor();
            String msg = color.getRed() + "," + color.getGreen() + "," + color.getBlue();
            client.publish(COLOR_STATUS_TOPIC, new MqttMessage(msg.getBytes()));
        } catch (MqttException e) {
            logger.error("Could not send color status to mqtt server: " + e);
        }
    }

    private void setColor(String rawString) {
        String[] splitted = rawString.split(",");
        Color color = new Color(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]));

        provider.setColor(color);
        sendColorStatus();
    }

    private void setSwitch(String rawString) {
        if(rawString.equalsIgnoreCase(ON)) {
            provider.switchOn();
            sendSwitchStatus(true);
        }
        else if(rawString.equalsIgnoreCase(OFF)) {
            provider.switchOff();
            sendSwitchStatus(false);
        }
    }
}
