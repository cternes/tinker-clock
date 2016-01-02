package de.slackspace.tinkerclock.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.slackspace.tinkerclock.device.Led;
import de.slackspace.tinkerclock.logic.ClockService;

@RestController
@RequestMapping("/")
public class TinkerClockResource {

	private Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	ClockService clockService;
	
	@PostConstruct
	public void init() {
		logger.debug("Successfully started TinkerClockResource. Starting clock again.");
		
		// make startup more robust
		stop();
		start();
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/stop")
	public void stop() {
		clockService.stop();
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/start")
	public void start() {
		clockService.start();
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/color") 
	public String getColor() {
		return clockService.getColor();
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/color")
	public void setColor(@RequestParam(value="colorHexTriplet", required=true) String colorHexTriplet) {
		if(!colorHexTriplet.contains("#")) {
			colorHexTriplet = "#" + colorHexTriplet;
		}
		
		logger.debug("Changing color to " + colorHexTriplet);
		
		clockService.setColor(colorHexTriplet);
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/status")
	public String getStatus() {
		List<Led> leds = clockService.getStatus();
		
		StringBuilder sb = new StringBuilder();
		for (Led led : leds) {
			sb.append(led.toString());
			sb.append("<br/>");
		}
		
		return sb.toString();
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/testmode/start")
	public void startTestMode() {
		clockService.enableTestmode();
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/testmode/stop")
	public void stopTestMode() {
		clockService.disableTestmode();
	}
	
	@PreDestroy
	public void onShutdown() throws Exception {
		logger.debug("System is going to halt. Turning off leds.");
		clockService.stop();
	}
}
