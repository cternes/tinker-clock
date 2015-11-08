package de.slackspace.tinkerclock.device;

public class LedGroup {

	private short[] red = new short[16];
	private short[] green = new short[16];
	private short[] blue = new short[16];
	
	public short[] getRed() {
		return red;
	}
	public void setRed(short[] red) {
		this.red = red;
	}
	public short[] getGreen() {
		return green;
	}
	public void setGreen(short[] green) {
		this.green = green;
	}
	public short[] getBlue() {
		return blue;
	}
	public void setBlue(short[] blue) {
		this.blue = blue;
	}
	
}
