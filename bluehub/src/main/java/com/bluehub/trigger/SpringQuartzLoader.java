package com.bluehub.trigger;



import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringQuartzLoader {
	private static Logger logger = Logger.getLogger(SpringQuartzLoader.class);

	public static void main(String[] args) throws Exception {
		logger.info("SpringQuartzLoader intialize");
		new ClassPathXmlApplicationContext("Spring-Quartz.xml");
	}
}