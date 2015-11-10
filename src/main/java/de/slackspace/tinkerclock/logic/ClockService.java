package de.slackspace.tinkerclock.logic;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;

import de.slackspace.tinkerclock.device.EnhancedLedStrip;
import de.slackspace.tinkerclock.device.Led;
import de.slackspace.tinkerclock.led.LedStripManager;

@Component
public class ClockService implements FrameRenderedListener {

	private Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	TimeLedProvider timeLedProvider;

	@Autowired
	LedStripManager manager;
	
	@PostConstruct
	public void init() {
		boolean connected = manager.connect();
		if(connected) {
			logger.debug("Successfully connected to tinkerforge brick");
			manager.getLedStrip().addFrameRenderedListener(this);
		}
	}
	
	@Override
	public void frameRendered(int length) {
		EnhancedLedStrip ledStrip = manager.getLedStrip();
		
		ledStrip.turnOff();
		
		List<Led> leds = timeLedProvider.getTimeLeds();
		ledStrip.setLeds(leds);
	}
	
	public void stop() {
		EnhancedLedStrip ledStrip = manager.getLedStrip();
		ledStrip.removeFrameRenderedListener(this);
		ledStrip.turnOff();
	}
	
	public void start() {
		EnhancedLedStrip ledStrip = manager.getLedStrip();
		ledStrip.addFrameRenderedListener(this);
	}

	public List<Led> getStatus() {
		List<Led> leds = timeLedProvider.getTimeLeds();
		return leds;
	}

}