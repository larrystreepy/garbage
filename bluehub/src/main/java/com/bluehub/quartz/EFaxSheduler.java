package com.bluehub.quartz;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bluehub.util.EfaxInbound;


public class EFaxSheduler extends QuartzJobBean{
	private static Logger logger = Logger.getLogger(QuartzJobBean.class);
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
		logger.info("Triggering method invoked in EFaxSheduler");
		try {
			EfaxInbound.callEFaxInbound();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	}
	
	

}
