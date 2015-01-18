package com.bluehub.util;

import org.apache.log4j.Logger;

public class SystemProperties {
	private static Logger logger = Logger.getLogger(SystemProperties.class);
	private static java.util.Properties prop = new java.util.Properties();

	private static void loadProperties() {
		logger.info("SystemProperties loadProperties() starts.");
		// get class loader
		ClassLoader loader = SystemProperties.class.getClassLoader();
		if (loader == null)
			loader = ClassLoader.getSystemClassLoader();
		String propFile = "Message.properties";
		java.net.URL url = loader.getResource(propFile);
		try {
			prop.load(url.openStream());
		} catch (Exception e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
	}

	public static String getProperty(final String name) {
		if (prop != null) {
			return (String) prop.get(name);
		}
		return null;
	}

	static {
		loadProperties();
	}
}