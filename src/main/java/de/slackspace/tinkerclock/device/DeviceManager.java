package de.slackspace.tinkerclock.device;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tinkerforge.IPConnection;
import com.tinkerforge.IPConnection.EnumerateListener;
import com.tinkerforge.NotConnectedException;

public class DeviceManager implements EnumerateListener {

	private IPConnection ipConnection;
	private List<String> devices = new ArrayList<String>();
	
	public DeviceManager(IPConnection ipConnection) {
		this.ipConnection = ipConnection;
		ipConnection.addEnumerateListener(this);
	}
	
	public List<String> getDevices() {
		try {
			ipConnection.enumerate();
			return devices;
		} catch (NotConnectedException e) {
			throw new RuntimeException("You must connect to the ip connection first!");
		}
	}

	@Override
	public void enumerate(String uid, String connectedUid, char position, short[] hardwareVersion,
			short[] firmwareVersion, int deviceIdentifier, short enumerationType) {
		devices.add(uid);
	}

	public boolean isDeviceConnected(String uid) {
		Optional<String> foundDevice = getDevices().stream().filter(i -> i.equals(uid)).findFirst();
		
		return foundDevice.isPresent();
	}
}
