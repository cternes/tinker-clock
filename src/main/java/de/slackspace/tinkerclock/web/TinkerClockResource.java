package de.slackspace.tinkerclock.web;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		logger.debug("Successfully started TinkerClockResource");
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/stop")
	public void stop() {
		clockService.stop();
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/start")
	public void start() {
		clockService.start();
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
}
