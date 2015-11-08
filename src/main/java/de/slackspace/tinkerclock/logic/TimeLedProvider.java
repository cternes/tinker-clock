package de.slackspace.tinkerclock.logic;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Component;

import de.slackspace.tinkerclock.device.Led;

@Component
public class TimeLedProvider {

	private static final ZoneId TIMEZONE = ZoneId.of("Europe/Berlin");
	private Clock clock = Clock.systemDefaultZone();
	
	protected void setClock(Clock clock) {
		this.clock = clock;
	}
	
	public List<Led> getTimeLeds() {
		Instant now = Instant.now(clock);
		LocalTime time = LocalTime.from(now.atZone(TIMEZONE));
		
		System.out.println(time.getHour());
		
		return null;
	}
	
}
