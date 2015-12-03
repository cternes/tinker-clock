package de.slackspace.tinkerclock.led;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.slackspace.tinkerclock.device.Led;

public class LedStripManagerIT {

	@Test
	public void showSingleLed() throws InterruptedException {
		LedStripManager manager = new LedStripManager();
		boolean connect = manager.connect();
		assertTrue(connect);
		
		List<Led> leds = new ArrayList<Led>();
		leds.add(new Led(110, "#ff0000"));
		leds.add(new Led(111, "#ff0000"));
		leds.add(new Led(112, "#ff0000"));
		leds.add(new Led(113, "#ff0000"));
		manager.getLedStrip().setLeds(leds);
		
		Thread.sleep(5000);
		
		manager.disconnect();
	}
}
