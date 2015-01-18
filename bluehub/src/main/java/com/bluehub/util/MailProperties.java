package com.bluehub.util;

import org.apache.log4j.Logger;

public class MailProperties {
	private static java.util.Properties prop = new java.util.Properties();
	private static Logger logger = Logger.getLogger(MailProperties.class);

	private static void loadProperties() {
		logger.info("MailProperties loadProperties() start");
		// get class loader
		ClassLoader loader = MailProperties.class.getClassLoader();
		if (loader == null)
			loader = ClassLoader.getSystemClassLoader();
		String propFile = "mail.properties";
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