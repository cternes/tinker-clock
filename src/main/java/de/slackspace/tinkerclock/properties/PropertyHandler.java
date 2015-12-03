package de.slackspace.tinkerclock.properties;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class PropertyHandler {

	private static String PROPERTIES_FILE = "mapping.properties";
	private Properties properties = new Properties();
	
	public PropertyHandler() {
		readPropertiesFromDisk();
	}
	
	// used for testing
	public PropertyHandler(String propertiesFile) {
		PROPERTIES_FILE = propertiesFile;
		readPropertiesFromClasspath();
	}
	
	private void readPropertiesFromClasspath() {
		try {
			InputStream stream = getClass().getResourceAsStream(PROPERTIES_FILE);
			properties.load(stream);
		} catch (IOException | NullPointerException e) {
			throw new RuntimeException("The properties file ("+PROPERTIES_FILE+") was not found. Please provide one.", e);
		}
	}

	private void readPropertiesFromDisk() {
		try {
			properties.load(new FileReader(PROPERTIES_FILE));
		} catch (IOException | NullPointerException e) {
			throw new RuntimeException("The properties file ("+PROPERTIES_FILE+") was not found. Please provide one.", e);
		}
	}
	
	public int[] getLedList(String key) {
		String ledString = properties.getProperty(key);
		
		if(ledString == null || ledString.isEmpty()) {
			throw new RuntimeException("Property for '" + key + "' is not defined!");
		}
		
		String[] splittedString = ledString.split(",");
		
		int[] ledArray = new int[splittedString.length];
		for (int i = 0; i < ledArray.length; i++) {
			ledArray[i] = Integer.valueOf(splittedString[i].trim());
		}
		
		return ledArray;
	}
	
}
