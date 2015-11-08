package de.slackspace.tinkerclock.logic;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.junit.Test;

import de.slackspace.tinkerclock.device.Led;

public class TimeLedProviderTest {

	private static final ZoneId TIMEZONE = ZoneId.of("Europe/Berlin");
	private TimeLedProvider cut = new TimeLedProvider();
	
	@Test
	public void shouldReturnLedsForNoon() {
		setTime(12, 0);
		
		List<Led> leds = cut.getTimeLeds();
		assertThat(leds, hasSize(2));
		
		//1
		//2
		//4,5,6
		
	}

	private void setTime(int hours, int minutes) {
		LocalDateTime dateTime = LocalDateTime.of(2015, 1, 1, hours, minutes);
		Instant fixedInstant = Instant.from(dateTime.atZone(TIMEZONE));
		cut.setClock(Clock.fixed(fixedInstant, TIMEZONE));
	}
	
	
}
