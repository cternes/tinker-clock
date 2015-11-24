package de.slackspace.tinkerclock.logic;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import de.slackspace.tinkerclock.device.Led;

@Component
public class TestmodeTimeLedProvider extends TimeLedProvider {

	private int delay = 5;
	private int hours = 0;
	private int minutes = 0;
	
	private Clock increaseTime() {
		LocalDateTime dateTime = LocalDateTime.of(2015, 1, 1, hours, minutes);
		Instant fixedInstant = Instant.from(dateTime.atZone(TIMEZONE));
		Clock clock = Clock.fixed(fixedInstant, TIMEZONE);
		
		// delay change of time
		if(delay < 5) {
			delay = delay + 1;
			return clock;
		}
		else {
			delay = 0;
		}
		
		if(minutes == 59) {
			minutes = 0;
			hours = hours + 1;
		}
		else {
			minutes = minutes +1;
		}
		
		return clock;
	}
	
	@Override
	public List<Led> getTimeLeds() {
		setClock(increaseTime());
		return super.getTimeLeds();
	}
}
