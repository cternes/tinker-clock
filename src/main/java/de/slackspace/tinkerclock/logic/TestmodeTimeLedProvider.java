package de.slackspace.tinkerclock.logic;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import de.slackspace.tinkerclock.device.Led;

@Component
public class TestmodeTimeLedProvider extends TimeLedProvider {

	private int hours = 0;
	private int minutes = 0;
	
	private Clock increaseTime() {
		LocalDateTime dateTime = LocalDateTime.of(2015, 1, 1, hours, minutes);
		Instant fixedInstant = Instant.from(dateTime.atZone(TIMEZONE));
		Clock clock = Clock.fixed(fixedInstant, TIMEZONE);
		
		if(minutes > 60) {
			minutes = 0;
			hours = hours++;
		}
		else {
			minutes = minutes++;
		}
		
		return clock;
	}
	
	@Override
	public List<Led> getTimeLeds() {
		setClock(increaseTime());
		return super.getTimeLeds();
	}
}
