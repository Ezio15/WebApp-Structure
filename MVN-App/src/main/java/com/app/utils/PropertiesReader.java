package com.twitter.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author 
 *
 */
public class PropertiesReader {
	private Properties configProp = new Properties();
	private static PropertiesReader props = null;

	public static PropertiesReader getInstance() {
		if(props == null){
			props=new PropertiesReader();
		}
		return props;
	}

	public String getInput(String key) {
		try {
			InputStream inputStream = getClass().getResourceAsStream("/application.properties");
			configProp.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return configProp.getProperty(key);	
	}
	
}
