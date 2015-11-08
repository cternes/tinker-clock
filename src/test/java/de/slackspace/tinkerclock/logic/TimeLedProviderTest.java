package de.slackspace.tinkerclock.logic;

import static org.junit.Assert.assertFalse;

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
		checkFullHour(12, 16, 15, 14, 13, 12);
	}
	
	@Test
	public void shouldReturnLedsForOnePm() {
		checkFullHour(13, 45, 46, 47);
	}
	
	@Test
	public void shouldReturnLedsForOneAm() {
		checkFullHour(1, 45, 46, 47);
	}
	
	@Test
	public void shouldReturnLedsForTwoPm() {
		checkFullHour(14, 52, 53, 54, 55);
	}
	
	@Test
	public void shouldReturnLedsForThreePm() {
		checkFullHour(15, 44, 43, 42);
	}
	
	@Test
	public void shouldReturnLedsForFourPm() {
		checkFullHour(16, 37, 36, 35, 34);
	}
	
	@Test
	public void shouldReturnLedsForFivePm() {
		checkFullHour(17, 59, 58, 57, 56);
	}
	
	@Test
	public void shouldReturnLedsForSixPm() {
		checkFullHour(18, 23, 24, 25, 26, 27);
	}
	
	@Test
	public void shouldReturnLedsForSevenPm() {
		checkFullHour(19, 22, 21, 20, 19, 18, 17);
	}
	
	@Test
	public void shouldReturnLedsForEightPm() {
		checkFullHour(20, 30, 31, 32, 33);
	}
	
	@Test
	public void shouldReturnLedsForNinePm() {
		checkFullHour(21, 4, 5, 6, 7);
	}
	
	@Test
	public void shouldReturnLedsForTenPm() {
		checkFullHour(22, 1, 2, 3, 4);
	}
	
	@Test
	public void shouldReturnLedsForElevenPm() {
		checkFullHour(23, 61, 60, 59);
	}
	
	@Test
	public void shouldReturnLedsForMidnight() {
		checkFullHour(0, 16, 15, 14, 13, 12);
	}
	
	@Test
	public void shouldReturnLedsForFivePastNoon() {
		checkAfterMinutes(0, 5, 103, 102, 101, 100); //5
		checkAfterMinutes(0, 5, 16, 15, 14, 13, 12); //12
	}
	
	@Test
	public void shouldReturnLedsForTenPastNoon() {
		checkAfterMinutes(0, 10, 89, 90, 91, 92); //10
		checkAfterMinutes(0, 10, 16, 15, 14, 13, 12); //12
	}
	
	@Test
	public void shouldReturnLedsForTwentyPastNoon() {
		checkAfterMinutes(0, 20, 93, 94, 95, 96, 97, 98, 99); //20
		checkAfterMinutes(0, 20, 16, 15, 14, 13, 12); //12
	}
	
	private void checkAfterMinutes(int hour, int minutes, int... indices) {
		setTime(hour, minutes);
		
		List<Led> leds = cut.getTimeLeds();
		assertThatLedsIncludeItIs(leds);
		assertThatLedsIncluded(leds, indices); 
		assertThatLedsIncludeAfter(leds);
	}
	
	private void checkFullHour(int hour, int... indices) {
		setTime(hour, 0);
		
		List<Led> leds = cut.getTimeLeds();
		assertThatLedsIncludeItIs(leds);
		assertThatLedsIncluded(leds, indices);
		assertThatLedsIncludeClockWord(leds);
	}
	
	private void assertThatLedsIncludeAfter(List<Led> leds) {
		assertThatLedsIncluded(leds, 74, 75, 76, 77);
	}
	
	private void assertThatLedsIncludeItIs(List<Led> leds) {
		assertThatLedsIncluded(leds, 110, 109, 107, 106, 105);
	}
	
	private void assertThatLedsIncludeClockWord(List<Led> leds) {
		assertThatLedsIncluded(leds, 9, 10, 11);
	}

	private void setTime(int hours, int minutes) {
		LocalDateTime dateTime = LocalDateTime.of(2015, 1, 1, hours, minutes);
		Instant fixedInstant = Instant.from(dateTime.atZone(TIMEZONE));
		cut.setClock(Clock.fixed(fixedInstant, TIMEZONE));
	}
	
	private void assertThatLedsHaveIndex(List<Led> leds, int expectedIndex) {
		for (Led led : leds) {
			if(led.getIndex() == expectedIndex) {
				return;
			}
		}
		
		assertFalse("Expected index '" + expectedIndex + "' was not found", true);
	}
	
	private void assertThatLedsIncluded(List<Led> leds, int... expectedIndices) {
		for (int index : expectedIndices) {
			assertThatLedsHaveIndex(leds, index);
		}
	}
	
}
