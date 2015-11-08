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
	private static String COLOR = "#ffffff";
	
	private Clock clock = Clock.systemDefaultZone();
	
	protected void setClock(Clock clock) {
		this.clock = clock;
	}
	
	public List<Led> getTimeLeds() {
		List<Led> leds = new ArrayList<Led>();
		
		Instant now = Instant.now(clock);
		LocalTime time = LocalTime.from(now.atZone(TIMEZONE));

		int hours = time.getHour(); 
		
		if(hours >= 12) {
			hours = hours - 12;
		}
		
		if((hours == 0) && time.getMinute() == 0) {
			addItIs(leds);
			addTwelve(leds);
			addClockWord(leds);
		}
		else if(hours == 1 && time.getMinute() == 0) {
			addItIs(leds);
			addOne(leds);
			addClockWord(leds);
		}
		else if(hours == 2 && time.getMinute() == 0) {
			addItIs(leds);
			addTwo(leds);
			addClockWord(leds);
		}
		else if(hours == 3 && time.getMinute() == 0) {
			addItIs(leds);
			addThree(leds);
			addClockWord(leds);
		}
		else if(hours == 4 && time.getMinute() == 0) {
			addItIs(leds);
			addFour(leds);
			addClockWord(leds);
		}
		else if(hours == 5 && time.getMinute() == 0) {
			addItIs(leds);
			addFive(leds);
			addClockWord(leds);
		}
		else if(hours == 6 && time.getMinute() == 0) {
			addItIs(leds);
			addSix(leds);
			addClockWord(leds);
		}
		else if(hours == 7 && time.getMinute() == 0) {
			addItIs(leds);
			addSeven(leds);
			addClockWord(leds);
		}
		else if(hours == 8 && time.getMinute() == 0) {
			addItIs(leds);
			addEight(leds);
			addClockWord(leds);
		}
		else if(hours == 9 && time.getMinute() == 0) {
			addItIs(leds);
			addNine(leds);
			addClockWord(leds);
		}
		else if(hours == 10 && time.getMinute() == 0) {
			addItIs(leds);
			addTen(leds);
			addClockWord(leds);
		}
		else if(hours == 11 && time.getMinute() == 0) {
			addItIs(leds);
			addEleven(leds);
			addClockWord(leds);
		}
		
		return leds;
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

	private void addOne(List<Led> leds) {
		addLeds(leds, 45, 46, 47);
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
