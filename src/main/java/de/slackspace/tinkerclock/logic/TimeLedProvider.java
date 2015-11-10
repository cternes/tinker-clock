package de.slackspace.tinkerclock.logic;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import de.slackspace.tinkerclock.device.Led;

@Component
public class TimeLedProvider {

	private static final ZoneId TIMEZONE = ZoneId.of("Europe/Berlin");
	private static String COLOR = "#0066FF";
	
	private Clock clock = Clock.systemDefaultZone();
	
	protected void setClock(Clock clock) {
		this.clock = clock;
	}
	
	public List<Led> getTimeLeds() {
		List<Led> leds = new ArrayList<Led>();
		
		Instant now = Instant.now(clock);
		LocalTime time = LocalTime.from(now.atZone(TIMEZONE));

		int hours = time.getHour();
		int minutes = time.getMinute();
		
		if(hours >= 12) {
			hours = hours - 12;
		}
		
		addItIs(leds);
		
		boolean increaseHours = setMinuteLeds(leds, minutes);
		
		if(increaseHours) {
			hours = hours + 1;
		}
		
		setHourLeds(leds, hours, minutes);
		
		return leds;
	}

	private void setHourLeds(List<Led> leds, int hours, int minutes) {
		boolean isFullHour = false;
		
		if(minutes < 5) {
			isFullHour = true;
			addClockWord(leds);
		}
		
		if(hours == 0) {
			addTwelve(leds);
		}
		else if(hours == 1) {
			addOne(leds, !isFullHour);
		}
		else if(hours == 2) {
			addTwo(leds);
		}
		else if(hours == 3 ) {
			addThree(leds);
		}
		else if(hours == 4 ) {
			addFour(leds);
		}
		else if(hours == 5 ) {
			addFive(leds);
		}
		else if(hours == 6 ) {
			addSix(leds);
		}
		else if(hours == 7 ) {
			addSeven(leds);
		}
		else if(hours == 8 ) {
			addEight(leds);
		}
		else if(hours == 9 ) {
			addNine(leds);
		}
		else if(hours == 10 ) {
			addTen(leds);
		}
		else if(hours == 11 ) {
			addEleven(leds);
		}
		
		if(minutes < 5) {
			addClockWord(leds);
		}
	}

	private boolean setMinuteLeds(List<Led> leds, int minutes) {
		boolean increaseHours = false;
		
		if(minutes >= 5 && minutes < 10) {
			addFiveMinutes(leds);
			addAfter(leds);
		}
		else if(minutes >= 10 && minutes < 15) {
			addTenMinutes(leds);
			addAfter(leds);
		}
		else if(minutes >= 15 && minutes < 20) {
			addQuarterMinutes(leds);
			increaseHours = true;
		}
		else if(minutes >= 20 && minutes < 25) {
			addTwentyMinutes(leds);
			addAfter(leds);
		}
		else if(minutes >= 25 && minutes < 30) {
			addFiveMinutes(leds);
			addBefore(leds);
			addHalf(leds);
			increaseHours = true;
		}
		else if(minutes >= 30 && minutes < 35) {
			addHalf(leds);
			increaseHours = true;
		}
		else if(minutes >= 35 && minutes < 40) {
			addFiveMinutes(leds);
			addAfter(leds);
			addHalf(leds);
			increaseHours = true;
		}
		else if(minutes >= 40 && minutes < 45) {
			addTwentyMinutes(leds);
			addBefore(leds);
			increaseHours = true;
		}
		else if(minutes >= 45 && minutes < 50) {
			addThreeQuartersMinutes(leds);
			increaseHours = true;
		}
		else if(minutes >= 50 && minutes < 55) {
			addTenMinutes(leds);
			addBefore(leds);
			increaseHours = true;
		}
		else if(minutes >= 55) {
			addFiveMinutes(leds);
			addBefore(leds);
			increaseHours = true;
		}
		
		return increaseHours;
	}
	
	private void addThreeQuartersMinutes(List<Led> leds) {
		addLeds(leds, 88, 87, 86, 85, 84, 83, 82, 81, 80, 79, 78);
	}
	
	private void addHalf(List<Led> leds) {
		addLeds(leds, 66, 65, 64, 63);
	}

	private void addBefore(List<Led> leds) {
		addLeds(leds, 67, 68, 69);
	}

	private void addQuarterMinutes(List<Led> leds) {
		addLeds(leds, 84, 83, 82, 81, 80, 79, 78);
	}

	private void addTwentyMinutes(List<Led> leds) {
		addLeds(leds, 93, 94, 95, 96, 97, 98, 99);
	}

	private void addTenMinutes(List<Led> leds) {
		addLeds(leds, 89, 90, 91, 92);
	}

	private void addFiveMinutes(List<Led> leds) {
		addLeds(leds, 103, 102, 101, 100);
	}

	private void addAfter(List<Led> leds) {
		addLeds(leds, 74, 75, 76, 77);
	}

	private void addEleven(List<Led> leds) {
		addLeds(leds, 61, 60, 59);
	}
	
	private void addTen(List<Led> leds) {
		addLeds(leds, 1, 2, 3, 4);
	}
	
	private void addNine(List<Led> leds) {
		addLeds(leds, 4, 5, 6, 7);
	}

	private void addEight(List<Led> leds) {
		addLeds(leds, 30, 31, 32, 33);
	}

	private void addSeven(List<Led> leds) {
		addLeds(leds, 22, 21, 20, 19, 18, 17);
	}

	private void addSix(List<Led> leds) {
		addLeds(leds, 23, 24, 25, 26, 27);
	}

	private void addFive(List<Led> leds) {
		addLeds(leds, 59, 58, 57, 56);
	}

	private void addFour(List<Led> leds) {
		addLeds(leds, 37, 36, 35, 34);
	}

	private void addThree(List<Led> leds) {
		addLeds(leds, 44, 43, 42, 41);
	}

	private void addTwo(List<Led> leds) {
		addLeds(leds, 52, 53, 54, 55);
	}

	private void addOne(List<Led> leds, boolean useAppendix) {
		if(useAppendix) {
			addLeds(leds, 45, 46, 47, 48);
		}
		else {
			addLeds(leds, 45, 46, 47);
		}
	}

	private void addClockWord(List<Led> leds) {
		addLeds(leds, 9, 10, 11);
	}

	private void addItIs(List<Led> leds) {
		// it
		addLeds(leds, 109, 110);
		
		// is
		addLeds(leds, 105, 106, 107);
	}
	
	private void addTwelve(List<Led> leds) {
		addLeds(leds, 16, 15, 14, 13, 12);
	}
	
	private void addLeds(List<Led> leds, int... indices) {
		for (int i : indices) {
			leds.add(new Led(i, COLOR));
		}
	}
	
}
