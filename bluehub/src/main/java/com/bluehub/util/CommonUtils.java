package com.bluehub.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.CharacterIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CommonUtils {
	private static Logger logger = Logger.getLogger(CommonUtils.class);

	/**
	 * Method to parse String to Timestamp
	 * 
	 * @param dateAsString
	 * @return TimeStamp
	 */
	
public static String convertMaptoJsonForGrid(List finalReportList,Long totalValue,Integer sEcho){
		
        Gson gson = new Gson();
		
		JsonObject jsonResponse = new JsonObject();		
		jsonResponse.addProperty("sEcho", sEcho);
		jsonResponse.addProperty("iTotalRecords", totalValue);
		jsonResponse.addProperty("iTotalDisplayRecords", totalValue);			
		jsonResponse.add("aaData", gson.toJsonTree(finalReportList));
		
		String finalJson = jsonResponse.toString();
		
		return finalJson;
	}
	public static java.sql.Date parseDateFromString(String dateAsString) {
		logger.info("CommonUtils parseDateFromString() starts.");
		Date date = null;
		java.sql.Date sqlDate = null;
		try {
			if (StringUtils.isNotBlank(dateAsString)) {
				date = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
						.parse(dateAsString);
				sqlDate = new java.sql.Date(date.getTime());
			}
		} catch (ParseException e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		return sqlDate;
	}
	
	public static java.sql.Date parseDateFromStringTodate(String dateAsString) throws Exception {/*
		logger.info("CommonUtils parseDateFromString() starts.");
		Date date = null;
		java.sql.Date sqlDate = null;
		try {
			if (StringUtils.isNotBlank(dateAsString)) {
				date = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
						.parse(dateAsString);
				sqlDate = new java.sql.Date(date.getTime());
			}
		} catch (ParseException e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		return sqlDate;
	*/
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = format.parse(dateAsString);
        java.sql.Date sql = new java.sql.Date(parsed.getTime());
		return sql;
		}

	public static String parseStringFromDate(Date date) {
		logger.info("CommonUtils parseStringFromDate() starts.");
		String dateAsString = null;
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy",
					Locale.ENGLISH);
			dateAsString = dateFormat.format(date);
		}
		return dateAsString;
	}
	
	public static String parseStringFromDateForDTTGraph(Date date) {
		logger.info("CommonUtils parseStringFromDate() starts.");
		String dateAsString = null;
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy(EEE)",
					Locale.ENGLISH);
			dateAsString = dateFormat.format(date);
		}
		return dateAsString;
	}

	public static Float parseFloatFromString(String floatAsString) {
		logger.info("CommonUtils parseFloatFromString() starts.");
		Float floatNo = null;
		if (StringUtils.isNotBlank(floatAsString)) {
			floatNo = Float.parseFloat(floatAsString);
		}
		return floatNo;
	}

	public static String getUploadFilePath() throws IOException {
		logger.info("CommonUtils getUploadFilePath() starts.");
		String filePath = Constants
				.getPropertyValue(Constants.FILE_UPLOAD_PATH);
		String syntax = Constants
				.getPropertyValue(Constants.FILE_UPLOAD_SYNTAX);
		String saveDirectory = filePath + Constants.ADMIN_MAIL_ID + syntax;
		;
		return saveDirectory;
	}

	public static Integer parseIntFromString(String intAsString) {
		logger.info("CommonUtils parseIntFromString() starts.");
		Integer integer = null;
		if (StringUtils.isNotBlank(intAsString)) {
			integer = Integer.parseInt(intAsString);
		}
		return integer;
	}

	public static Double parseDoubleFromString(String doubleAsString) {
		logger.info("CommonUtils parseDoubleFromString() starts.");
		Double doubleNo = null;
		if (StringUtils.isNotBlank(doubleAsString)) {
			doubleNo = Double.parseDouble(doubleAsString);
		}
		return doubleNo;
	}

	public static java.sql.Timestamp parseTimestampFromString(
			String timestampAsString) {
		logger.info("CommonUtils parseTimestampFromString() starts.");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		java.sql.Timestamp timestamp = null;
		try {
			if (StringUtils.isNotBlank(timestampAsString)) {
				java.util.Date date = sdf.parse(timestampAsString);
				timestamp = new java.sql.Timestamp(date.getTime());
			}
		} catch (ParseException e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		return timestamp;
	}

	public static java.sql.Timestamp parseTimestampFromStringTwentyFourHourFormat(
			String timestampAsString) {
		logger.info("CommonUtils parseTimestampFromStringTwentyFourHourFormat() starts.");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		java.sql.Timestamp timestamp = null;
		try {
			if (StringUtils.isNotBlank(timestampAsString)) {
				java.util.Date date = sdf.parse(timestampAsString);
				timestamp = new java.sql.Timestamp(date.getTime());
			}
		} catch (ParseException e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		return timestamp;
	}

	public static String parseStringFromTimeStamp(Timestamp timestamp) {
		logger.info("CommonUtils parseStringFromTimeStamp() starts.");
		String timestampAsString = null;
		if (timestamp != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH);
			timestampAsString = dateFormat.format(timestamp);
		}
		return timestampAsString;
	}

	public static java.sql.Date getSQLDate(String date) throws ParseException {
		logger.info("CommonUtils getSQLDate() starts.");
		java.util.Date utilDate = new SimpleDateFormat("MM/dd/yyyy",
				Locale.ENGLISH).parse(date);
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	}

	public static boolean checkDateGreaterNot(Date newDate)
			throws ParseException {
		logger.info("CommonUtils checkDateGreaterNot() starts.");
		boolean valid = false;
		Calendar userDate = Calendar.getInstance();
		Calendar currentDate = Calendar.getInstance();
		userDate.setTime(newDate);
		if (!userDate.before(currentDate)) {
			valid = true;
		} else {
			valid = false;
		}
		return valid;
	}
	
	public static String getUnSubscribedDate(int validDays)
			throws ParseException {
		Date subscribeDate = new Date();
		logger.info("CommonUtils getUnSubscribedDate() starts.");
		Calendar c = Calendar.getInstance();
		c.setTime(subscribeDate);
		c.add(Calendar.DATE, validDays);
		Date newDate = c.getTime();
		String expiredDat = parseStringFromDate(newDate);
		return expiredDat;
	}
	private static void addCharEntity(Integer aIdx, StringBuilder aBuilder){
	    String padding = "";
	    if( aIdx <= 9 ){
	       padding = "00";
	    }
	    else if( aIdx <= 99 ){
	      padding = "0";
	    }
	    else {
	      //no prefix
	    }
	    String number = padding + aIdx.toString();
	    aBuilder.append("&#" + number + ";");
	  }
	
	 
	 public static String forHTML(String aText){
	     final StringBuilder result = new StringBuilder();
	     final StringCharacterIterator iterator = new StringCharacterIterator(aText);
	     char character =  iterator.current();
	     while (character != CharacterIterator.DONE ){
	       if (character == '<') {
	         result.append("&lt;");
	       }
	       else if (character == '>') {
	         result.append("&gt;");
	       }
	       else if (character == '&') {
	         result.append("&amp;");
	      }
	       else if (character == '\"') {
	         result.append("&quot;");
	       }
	       else if (character == '\t') {
	         addCharEntity(9, result);
	       }
	       else if (character == '!') {
	         addCharEntity(33, result);
	       }
	       else if (character == '#') {
	         addCharEntity(35, result);
	       }
	       else if (character == '$') {
	         addCharEntity(36, result);
	       }
	       else if (character == '%') {
	         addCharEntity(37, result);
	       }
	       else if (character == '\'') {
	         addCharEntity(39, result);
	       }
	       else if (character == '(') {
	         addCharEntity(40, result);
	       }
	       else if (character == ')') {
	         addCharEntity(41, result);
	       }
	       else if (character == '*') {
	         addCharEntity(42, result);
	       }
	       else if (character == '+') {
	         addCharEntity(43, result);
	       }
	       else if (character == ',') {
	         addCharEntity(44, result);
	       }
	       else if (character == '-') {
	         addCharEntity(45, result);
	       }
	       else if (character == '.') {
	         addCharEntity(46, result);
	       }
	       else if (character == '/') {
	         addCharEntity(47, result);
	       }
	       else if (character == ':') {
	         addCharEntity(58, result);
	       }
	       else if (character == ';') {
	         addCharEntity(59, result);
	       }
	       else if (character == '=') {
	         addCharEntity(61, result);
	       }
	       else if (character == '?') {
	         addCharEntity(63, result);
	       }
	       else if (character == '@') {
	         addCharEntity(64, result);
	       }
	       else if (character == '[') {
	         addCharEntity(91, result);
	       }
	       else if (character == '\\') {
	         addCharEntity(92, result);
	       }
	       else if (character == ']') {
	         addCharEntity(93, result);
	       }
	       else if (character == '^') {
	         addCharEntity(94, result);
	       }
	       else if (character == '_') {
	         addCharEntity(95, result);
	       }
	       else if (character == '`') {
	         addCharEntity(96, result);
	       }
	       else if (character == '{') {
	         addCharEntity(123, result);
	       }
	       else if (character == '|') {
	         addCharEntity(124, result);
	       }
	       else if (character == '}') {
	         addCharEntity(125, result);
	       }
	       else if (character == '~') {
	         addCharEntity(126, result);
	       }
	       else {
	         //the char is not a special one
	         //add it to the result as is
	         result.append(character);
	       }
	       character = iterator.next();
	     }
	     return result.toString();
	  }
	
	public static String getFileContent(String filepath) throws IOException{
		BufferedReader br = null;
		String sCurrentLine = null;
		StringBuffer strBuff=new StringBuffer();
		try {			
			File f = new File(filepath);
			if(f.exists()){
				//br = new BufferedReader(new FileReader(filepath));
				br = new BufferedReader(
				         new InputStreamReader(new FileInputStream(filepath),"ISO-8859-1"));
				while ((sCurrentLine = br.readLine()) != null) {	
					//strBuff.append("\n");
					if(sCurrentLine.isEmpty()){
						strBuff.append("<br>");
					}else{
						strBuff.append(sCurrentLine+"<br>");
					}
					//sCurrentLine=forHTML(sCurrentLine);
					}
				br.close();
			}else{
				logger.info("file not found");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 

		return strBuff.toString();
	}
	
	public static String parseStringFromTimeStampForDateFormate(Timestamp timestamp) {
		String timestampAsString = null;
		if (timestamp != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd", Locale.ENGLISH);
			timestampAsString = dateFormat.format(timestamp);
		}
		return timestampAsString;
	}
	
	public static String parseStringFromDateForMonthlyGraph(Date date) {
		logger.info("CommonUtils parseStringFromDate() starts.");
		String dateAsString = null;
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy(MMM)",
					Locale.ENGLISH);
			dateAsString = dateFormat.format(date);
		}
		return dateAsString;
	}
	
	public static java.sql.Date getPreviousDate(java.sql.Date baselineTo) {
		java.sql.Date oneDayBefore = null;
		if(null != baselineTo){
			Calendar cal = Calendar.getInstance();
			cal.setTime(baselineTo);
			cal.add(Calendar.DATE, -1);
			oneDayBefore = new java.sql.Date(cal.getTime().getTime());
		}
		return oneDayBefore;
	}
	
	public static java.sql.Date parseDateFromStringForDttComaprison(String dateAsString) {
		logger.info("CommonUtils parseDateFromString() starts.");
		Date date = null;
		java.sql.Date sqlDate = null;
		try {
			if (StringUtils.isNotBlank(dateAsString)) {
				date = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
						.parse(dateAsString);
				sqlDate = new java.sql.Date(date.getTime());
			}
		} catch (ParseException e) {
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}
		return sqlDate;
	}
	
	public static java.sql.Date getNextDate(java.sql.Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		java.sql.Date nextDate = new java.sql.Date(cal.getTime().getTime());
		return nextDate;
	}

	public static List<java.sql.Date> getScheduleDatesForActivityCalendar(
			java.sql.Date activityDate, java.sql.Date uptoDate) {
		List<java.sql.Date> scheduleDates = new ArrayList<java.sql.Date>();
		//java.sql.Date dateAfterActivityDate = getNextDate(activityDate);
		
		while(activityDate.compareTo(uptoDate) <= 0){
			scheduleDates.add(activityDate);
			activityDate = getNextDate(activityDate);
		}
		return scheduleDates;
	}

	public static List<java.sql.Date> getScheduleDatesForRoutineActivities(
			java.sql.Date activityDate, java.sql.Date scheduleUpto) {
		List<java.sql.Date> scheduleDates = new ArrayList<java.sql.Date>();
		activityDate = getNextDate(activityDate);
		while(activityDate.compareTo(scheduleUpto) <= 0){
			scheduleDates.add(activityDate);
			activityDate = getNextDate(activityDate);
		}
		return scheduleDates;
	}
	
	public static long calculateDays(String startDate, String endDate)
    {
        Date sDate = new Date(startDate);
        Date eDate = new Date(endDate);
        Calendar cal3 = Calendar.getInstance();
        cal3.setTime(sDate);
        Calendar cal4 = Calendar.getInstance();
        cal4.setTime(eDate);
        return daysBetween(cal3, cal4);
    }
	public static long daysBetween(Calendar startDate, Calendar endDate) {
        Calendar date = (Calendar) startDate.clone();
        long daysBetween = 0;
        while (date.before(endDate)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }
	
	public static String parseStringFromTimeStampData(Timestamp timestamp) {
		String timestampAsString = null;
		if (timestamp != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"MM/dd/yyyy", Locale.ENGLISH);
			timestampAsString = dateFormat.format(timestamp);
		}
		return timestampAsString;
	}
	
	public static int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) {
	    Calendar startCal = Calendar.getInstance();
	    startCal.setTime(startDate);        

	    Calendar endCal = Calendar.getInstance();
	    endCal.setTime(endDate);

	    int fridays = 0;

	   
	    if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
	        return 0;
	    }

	    if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
	    	
	        startCal.setTime(endDate);
	        endCal.setTime(startDate);
	    }
	    
	    //If u want to include the start date in count
	    if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
	    	fridays++;
	    }

	    do {
	     
	        startCal.add(Calendar.DAY_OF_MONTH, 1);
	        if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
	            ++fridays;
	        }
	    } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); 

	    return fridays;
	}


	public static java.sql.Date getStartDateFromIntiated(Date startDate) throws ParseException {	 
		logger.info("getStartDateFromIntiated==>"+startDate);
		Calendar startCal = Calendar.getInstance();
	    startCal.setTime(startDate);        
	    java.sql.Date sqlStartDate = null;
	    int count=0; 
	    if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
	    	count=1;
	    }
	   if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
	    	count=2;
	    }
	   if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
	    	count=3;
	    }
	   if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
	    	count=4;
	    }
	   if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
	    	count=5;
	    }
	   if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
	    	count=6;
	    }
	   if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
	    	count=0;
	    }
	
	   Calendar cal = Calendar.getInstance();
       cal.setTime(startCal.getTime());
       cal.add(Calendar.DATE, count);
            
       Calendar finalStartDate = Calendar.getInstance();
       finalStartDate.setTime(cal.getTime());
   
       //SimpleDateFormat formatt=new SimpleDateFormat("dd/MM/yyyy");
       SimpleDateFormat formatt=new SimpleDateFormat("MM/dd/yyyy");
       String starDateString = formatt.format(finalStartDate.getTime());
       sqlStartDate=parseDateFromString(starDateString);
       logger.info("getStartDateFromIntiated==>"+sqlStartDate);    
	   return sqlStartDate;
	}
	
	
	public static java.sql.Date getEndDateFromMastered(Date endDate) throws ParseException {	
		  logger.info("getEndDateFromMastered==>"+endDate);     
		  Calendar endCal = Calendar.getInstance();
		  endCal.setTime(endDate);
		  int county=0; 
		  java.sql.Date sqlEndDate = null;
	 try{		 
		  if (endCal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
		    	county=0;
		    }
		   if (endCal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
		    	county=-6;
		    }
		   if (endCal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
		    	county=-5;
		    }
		   if (endCal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
		    	county=-4;
		    }
		   if (endCal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
		    	county=-3;
		    }
		   if (endCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
		    	county=-2;
		    }
		   if (endCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
		    	county=-1;
		    }	       
	       Calendar end = Calendar.getInstance();
	       end.setTime(endCal.getTime());
	       end.add(Calendar.DATE, county);	       	      
	       Calendar finalEndDate = Calendar.getInstance();
	       finalEndDate.setTime(end.getTime());
	
	       //SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
	       SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
	       String endDateString = format.format(finalEndDate.getTime());
	       sqlEndDate=parseDateFromString(endDateString);
	       logger.info("getEndDateFromMastered==>"+sqlEndDate);     
	      
	 }catch (Exception e) {
		// TODO: handle exception
	}
	return sqlEndDate;
}
	
	public static int getConditionDaysBetweenTwoDates(Date startDate, Date endDate,Date findDate) throws ParseException {
	    Calendar startCal = Calendar.getInstance();
	    startCal.setTime(startDate);        

	    Calendar endCal = Calendar.getInstance();
	    endCal.setTime(endDate);
	    int retCnt = 0;
	   
	    if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
	        return 0;
	    }

	    if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {	    	
	        startCal.setTime(endDate);
	        endCal.setTime(startDate);
	    }
	    
	    if (findDate.after(startCal.getTime()) && findDate.before(endCal.getTime())) {
         // logger.info("inside the given date");
	    	retCnt=1;
        }
        else {
          // logger.info("outside given date");
        	retCnt=0;
        }
	    return retCnt;
	}
	
	
	public static int getBetweenTwoDates(Date startDate, Date endDate) throws ParseException {
	    Calendar startCal = Calendar.getInstance();
	    startCal.setTime(startDate);        

	    Calendar endCal = Calendar.getInstance();
	    endCal.setTime(endDate);
	    int retCnt = 0;
	  	
	    if (startCal.getTimeInMillis() >endCal.getTimeInMillis()) {	    	
	        startCal.setTime(endDate);
	        endCal.setTime(startDate);
	        retCnt=1;
	    }
	   
	    return retCnt;
	}
	
	
	public static int getGreaterDates(Date startDate, Date endDate) throws ParseException {
	    Calendar startCal = Calendar.getInstance();
	    startCal.setTime(startDate);        

	    Calendar endCal = Calendar.getInstance();
	    endCal.setTime(endDate);
	    int retCnt = 0;
	  	
	    if (startCal.getTimeInMillis() <endCal.getTimeInMillis()) {	    	
	        startCal.setTime(endDate);
	        endCal.setTime(startDate);
	        retCnt=1;
	    }
	   
	    return retCnt;
	}
	
	public static String getColour(int code){
		  String color =""; 
		  Map<Integer, String> colors = new java.util.HashMap<Integer, String>();
		  colors.put(0, "blue");
		  colors.put(1, "green");
		  colors.put(2, "red");
		  colors.put(3, "yellow");
		  colors.put(4, "rosybrown");
		  colors.put(5, "indigo");
		  colors.put(6, "orange");
		  colors.put(7, "teal");
		  colors.put(8, "darkturquoise");
		  colors.put(9, "firebrick");
		  color = colors.get(code);
		  return color;
		 }
}
