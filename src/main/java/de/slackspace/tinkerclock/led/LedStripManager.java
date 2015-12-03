package de.slackspace.tinkerclock.led;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickletLEDStrip;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import de.slackspace.tinkerclock.device.DeviceManager;
import de.slackspace.tinkerclock.device.EnhancedLedStrip;

@Component
public class LedStripManager {

	private Log logger = LogFactory.getLog(getClass());
	
	private static final String HOST = "localhost";
    private static final int PORT = 4223;
    private static final String UID = "oVS"; // UID of LED Strip Bricklet
	
    private IPConnection ipcon = new IPConnection();
    private EnhancedLedStrip ledStrip;
    
    public boolean connect() {
        ledStrip = new EnhancedLedStrip(UID, ipcon);
        try {
			ipcon.connect(HOST, PORT);
			ledStrip.setChipType(BrickletLEDStrip.CHIP_TYPE_WS2812);
			
			DeviceManager deviceManager = new DeviceManager(ipcon);
			return deviceManager.isDeviceConnected(UID);
		} catch (AlreadyConnectedException | IOException | TimeoutException | NotConnectedException e) {
			logger.error("Could not connect to led strip", e);
			return false;
		}
    }
    
    public void disconnect() {
    	try {
    		ledStrip.turnOff();
			ipcon.disconnect();
		} catch (NotConnectedException e) {
			logger.error("Could not disconnect to led strip", e);
		}
    }
    
    public EnhancedLedStrip getLedStrip() {
    	return ledStrip;
    }
    
}
