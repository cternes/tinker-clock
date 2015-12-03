package de.slackspace.tinkerclock.logic;

import java.awt.Color;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.slackspace.tinkerclock.device.Led;
import de.slackspace.tinkerclock.properties.PropertyHandler;

@Component
public class TimeLedProvider {

	protected static final ZoneId TIMEZONE = ZoneId.of("Europe/Berlin");
	private String color = "#0066FF";
	
	private Clock clock = Clock.systemDefaultZone();
	
	@Autowired
	private PropertyHandler propertyHandler;
	
	public void setColor(String colorHexTriplet) {
		try {
			Color.decode(colorHexTriplet);
			this.color = colorHexTriplet;
		}
		catch(NumberFormatException e) {
			throw new RuntimeException("The given color '" + colorHexTriplet + "' is not a valid hex triplet.", e);
		}
	}
	
	public String getColor() {
		return color;
	}
	
	protected void setClock(Clock clock) {
		this.clock = clock;
	}
	
	protected void setPropertyHandler(PropertyHandler propertyHandler) {
		this.propertyHandler = propertyHandler;
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
		
		if(hours == 0 || hours == 12) {
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
		
		if(minutes % 5 == 1) {
			addOneMinute(leds);
		}
		else if(minutes % 5 == 2) {
			addOneMinute(leds);
			addTwoMinutes(leds);
		}
		else if(minutes % 5 == 3) {
			addOneMinute(leds);
			addTwoMinutes(leds);
			addThreeMinutes(leds);
		}
		else if(minutes % 5 == 4) {
			addOneMinute(leds);
			addTwoMinutes(leds);
			addThreeMinutes(leds);
			addFourMinutes(leds);
		}
		
		return increaseHours;
	}
	
	private void addThreeQuartersMinutes(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("ThreeQuarter"));
	}
	
	private void addHalf(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("Half"));
	}

	private void addBefore(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("Before"));
	}

	private void addQuarterMinutes(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("Quarter"));
	}

	private void addTwentyMinutes(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("TwentyMinutes"));
	}

	private void addTenMinutes(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("TenMinutes"));
	}

	private void addFiveMinutes(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("FiveMinutes"));
	}

	private void addAfter(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("After"));
	}

	private void addEleven(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("Eleven"));
	}
	
	private void addTen(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("Ten"));
	}
	
	private void addNine(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("Nine"));
	}

	private void addEight(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("Eight"));
	}

	private void addSeven(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("Seven"));
	}

	private void addSix(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("Six"));
	}

	private void addFive(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("Five"));
	}

	private void addFour(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("Four"));
	}

	private void addThree(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("Three"));
	}

	private void addTwo(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("Two"));
	}

	private void addOne(List<Led> leds, boolean useAppendix) {
		if(useAppendix) {
			addLeds(leds, propertyHandler.getLedList("OneExtended"));
		}
		else {
			addLeds(leds, propertyHandler.getLedList("One"));
		}
	}

	private void addClockWord(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("Clock"));
	}

	private void addItIs(List<Led> leds) {
		// it
		addLeds(leds, propertyHandler.getLedList("It"));
		
		// is
		addLeds(leds, propertyHandler.getLedList("Is"));
	}
	
	private void addTwelve(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("Twelve"));
	}
	
	private void addOneMinute(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("OneMinute"));
	}
	
	private void addTwoMinutes(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("TwoMinutes"));
	}
	
	private void addThreeMinutes(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("ThreeMinutes"));
	}
	
	private void addFourMinutes(List<Led> leds) {
		addLeds(leds, propertyHandler.getLedList("FourMinutes"));
	}
	
	private void addLeds(List<Led> leds, int... indices) {
		for (int i : indices) {
			leds.add(new Led(i, color));
		}
	}
	
}
