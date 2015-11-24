package de.slackspace.tinkerclock.logic;

import static org.junit.Assert.assertFalse;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.slackspace.tinkerclock.device.Led;
import de.slackspace.tinkerclock.properties.PropertyHandler;

public class TimeLedProviderTest {

	private static final ZoneId TIMEZONE = ZoneId.of("Europe/Berlin");
	
	private TimeLedProvider cut;
	
	@Before
	public void setup() {
		cut = new TimeLedProvider();
		cut.setPropertyHandler(new PropertyHandler("/mapping.properties"));
	}
	
	@Test
	public void shouldReturnLedsForNoon() {
		checkFullHour(12, 15, 14, 13, 12, 11);
	}
	
	@Test
	public void shouldReturnLedsForOnePm() {
		checkFullHour(13, 44, 45, 46);
	}
	
	@Test
	public void shouldReturnLedsForOneAm() {
		checkFullHour(1, 44, 45, 46);
	}
	
	@Test
	public void shouldReturnLedsForTwoPm() {
		checkFullHour(14, 51, 52, 53, 54);
	}
	
	@Test
	public void shouldReturnLedsForThreePm() {
		checkFullHour(15, 43, 42, 41, 40);
	}
	
	@Test
	public void shouldReturnLedsForFourPm() {
		checkFullHour(16, 36, 35, 34, 33);
	}
	
	@Test
	public void shouldReturnLedsForFivePm() {
		checkFullHour(17, 58, 57, 56, 55);
	}
	
	@Test
	public void shouldReturnLedsForSixPm() {
		checkFullHour(18, 22, 23, 24, 25, 26);
	}
	
	@Test
	public void shouldReturnLedsForSevenPm() {
		checkFullHour(19, 21, 20, 19, 18, 17, 16);
	}
	
	@Test
	public void shouldReturnLedsForEightPm() {
		checkFullHour(20, 29, 30, 31, 32);
	}
	
	@Test
	public void shouldReturnLedsForNinePm() {
		checkFullHour(21, 3, 4, 5, 6);
	}
	
	@Test
	public void shouldReturnLedsForTenPm() {
		checkFullHour(22, 0, 1, 2, 3);
	}
	
	@Test
	public void shouldReturnLedsForElevenPm() {
		checkFullHour(23, 60, 59, 58);
	}
	
	@Test
	public void shouldReturnLedsForMidnight() {
		checkFullHour(0, 15, 14, 13, 12, 11);
	}
	
	@Test
	public void shouldReturnLedsForFivePastNoon() {
		checkAfterMinutes(0, 5, 102, 101, 100, 99); //5
		checkAfterMinutes(0, 5, 15, 14, 13, 12, 11); //12
	}
	
	@Test
	public void shouldReturnLedsForTenPastNoon() {
		checkAfterMinutes(0, 10, 88, 89, 90, 91); //10
		checkAfterMinutes(0, 10, 15, 14, 13, 12, 11); //12
	}
	
	@Test
	public void shouldReturnLedsForQuarterPastNoon() {
		setTime(0, 15);
		
		List<Led> leds = cut.getTimeLeds();
		assertThatLedsIncludeItIs(leds);
		assertThatLedsIncludeQuarter(leds);
		assertThatLedsForOneIncluded(leds);
	}
	
	@Test
	public void shouldReturnLedsForTwentyPastNoon() {
		checkAfterMinutes(0, 20, 92, 93, 94, 95, 96, 97, 98); //20
		checkAfterMinutes(0, 20, 15, 14, 13, 12, 11); //12
	}
	
	@Test
	public void shouldReturnLedsForTwentyFivePastNoon() {
		setTime(0, 25);
		
		List<Led> leds = cut.getTimeLeds();
		assertThatLedsIncludeItIs(leds);
		assertThatLedsForFiveIncluded(leds);
		assertThatLedsIncludeBefore(leds);
		assertThatLedsIncludeHalf(leds);
		assertThatLedsForOneIncluded(leds);
	}
	
	@Test
	public void shouldReturnLedsForTwentyFivePastEleven() {
		setTime(11, 25);
		
		List<Led> leds = cut.getTimeLeds();
		assertThatLedsIncludeItIs(leds);
		assertThatLedsForFiveIncluded(leds);
		assertThatLedsIncludeBefore(leds);
		assertThatLedsIncludeHalf(leds);
		asserThatLedsForTwelveIncluded(leds);
	}

	@Test
	public void shouldReturnLedsForHalfPastNoon() {
		setTime(0, 30);
		
		List<Led> leds = cut.getTimeLeds();
		assertThatLedsIncludeItIs(leds);
		assertThatLedsIncludeHalf(leds);
		assertThatLedsForOneIncluded(leds);
	}
	
	@Test
	public void shouldReturnLedsForThirtyFivePastNoon() {
		setTime(0, 35);
		
		List<Led> leds = cut.getTimeLeds();
		assertThatLedsIncludeItIs(leds);
		assertThatLedsForFiveIncluded(leds);
		assertThatLedsIncludeAfter(leds);
		assertThatLedsIncludeHalf(leds);
		assertThatLedsForOneIncluded(leds);
	}
	
	@Test
	public void shouldReturnLedsForFourtyPastNoon() {
		setTime(0, 40);
		
		List<Led> leds = cut.getTimeLeds();
		assertThatLedsIncludeItIs(leds);
		assertThatLedsIncluded(leds, 92, 93, 94, 95, 96, 97, 98); //20
		assertThatLedsIncludeBefore(leds);
		assertThatLedsForOneIncluded(leds);
	}
	
	@Test
	public void shouldReturnLedsForFourtyFivePastNoon() {
		setTime(0, 45);
		
		List<Led> leds = cut.getTimeLeds();
		assertThatLedsIncludeItIs(leds);
		assertThatLedsIncludeThreeQuarters(leds);
		assertThatLedsForOneIncluded(leds);
	}
	
	@Test
	public void shouldReturnLedsForFiftyPastNoon() {
		setTime(0, 50);
		
		List<Led> leds = cut.getTimeLeds();
		assertThatLedsIncludeItIs(leds);
		assertThatLedsIncluded(leds, 88, 89, 90, 91); //10
		assertThatLedsIncludeBefore(leds);
		assertThatLedsForOneIncluded(leds);
	}
	
	@Test
	public void shouldReturnLedsForFiftyFivePastNoon() {
		setTime(0, 55);
		
		List<Led> leds = cut.getTimeLeds();
		assertThatLedsIncludeItIs(leds);
		assertThatLedsForFiveIncluded(leds);
		assertThatLedsIncludeBefore(leds);
		assertThatLedsForOneIncluded(leds);
	}
	
	@Test
	public void shouldReturnLedsForFivePastOne() {
		checkAfterMinutes(1, 5, 102, 101, 100, 99); //5
		checkAfterMinutes(1, 5, 44, 45, 46, 47); //1
	}
	
	@Test
	public void shouldReturnLedsForTenPastOne() {
		checkAfterMinutes(1, 10, 88, 89, 90, 91); //10
		checkAfterMinutes(1, 10, 44, 45, 46, 47); //1
	}
	
	@Test
	public void shouldReturnLedsForQuarterPastOne() {
		setTime(1, 15);
		
		List<Led> leds = cut.getTimeLeds();
		assertThatLedsIncludeItIs(leds);
		assertThatLedsIncludeQuarter(leds);
		assertThatLedsIncluded(leds, 51, 52, 53, 54); //2
	}
	
	@Test
	public void shouldReturnLedsForTwentyPastOne() {
		checkAfterMinutes(1, 20, 92, 93, 94, 95, 96, 97, 98); //20
		checkAfterMinutes(1, 20, 44, 45, 46, 47); //1
	}
	
	@Test
	public void shouldReturnLedsForTwentyFivePastOne() {
		setTime(1, 25);
		
		List<Led> leds = cut.getTimeLeds();
		assertThatLedsIncludeItIs(leds);
		assertThatLedsIncluded(leds,  102, 101, 100, 99); //5
		assertThatLedsIncludeBefore(leds);
		assertThatLedsIncludeHalf(leds);
		assertThatLedsIncluded(leds, 51, 52, 53, 54);//2
	}
	
	private void assertThatLedsIncludeBefore(List<Led> leds) {
		assertThatLedsIncluded(leds, 66, 67, 68);
	}

	private void assertThatLedsIncludeHalf(List<Led> leds) {
		assertThatLedsIncluded(leds, 65, 64, 63, 62);
	}

	private void assertThatLedsIncludeThreeQuarters(List<Led> leds) {
		assertThatLedsIncluded(leds, 87, 86, 85, 84, 83, 82, 81, 80, 79, 78, 77);
	}
	
	private void assertThatLedsIncludeQuarter(List<Led> leds) {
		assertThatLedsIncluded(leds, 83, 82, 81, 80, 79, 78, 77);
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
		assertThatLedsIncluded(leds, 73, 74, 75, 76);
	}
	
	private void assertThatLedsIncludeItIs(List<Led> leds) {
		assertThatLedsIncluded(leds, 109, 108, 106, 105, 104);
	}
	
	private void assertThatLedsIncludeClockWord(List<Led> leds) {
		assertThatLedsIncluded(leds, 8, 9, 10);
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
	
	private void assertThatLedsForOneIncluded(List<Led> leds) {
		assertThatLedsIncluded(leds, 44, 45, 46, 47); //1
	}

	private void assertThatLedsForFiveIncluded(List<Led> leds) {
		assertThatLedsIncluded(leds, 102, 101, 100, 99); //5
	}
	
	private void asserThatLedsForTwelveIncluded(List<Led> leds) {
		assertThatLedsIncluded(leds, 15, 14, 13, 12, 11); //12
	}
	
}
