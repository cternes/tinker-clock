package de.slackspace.tinkerclock.device;

import java.awt.Color;

public class Led {

	private int index;
	private short red;
	private short green;
	private short blue;
	
	public Led(int index, String colorHexTriplet) {
		this(index, Color.decode(colorHexTriplet));
	}
	
	public Led(int index, Color color) {
		this(index, (short) color.getRed(), (short) color.getGreen(), (short) color.getBlue());
	}
	
	public Led(int index, short red, short green, short blue) {
		this.index = index;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public short getRed() {
		return red;
	}

	public void setRed(short red) {
		this.red = red;
	}

	public short getGreen() {
		return green;
	}

	public void setGreen(short green) {
		this.green = green;
	}

	public short getBlue() {
		return blue;
	}

	public void setBlue(short blue) {
		this.blue = blue;
	}
	
	public void turnOff() {
		setRed((short)0);
		setBlue((short)0);
		setGreen((short)0);
	}

	@Override
	public String toString() {
		return "Led [index=" + index + ", red=" + red + ", green=" + green + ", blue=" + blue + "]";
	}
	
}
