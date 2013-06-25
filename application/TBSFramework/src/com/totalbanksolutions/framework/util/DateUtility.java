package com.totalbanksolutions.framework.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;

public class DateUtility {
	
	private static Logger				log						= Logger.getLogger(AppUtility.class);
	
    public static Date getLastWeekdayInMonth(Date date)
    {
    	int lastDay   = 1;
    	int dayOfWeek = 0;
    	
    	log.debug("getLastWeekdayInMonth for: " + date);
		Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
		    lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH); 
		    log.debug("Last Day of the Month is: " + lastDay);
		   
	    do {
	    	cal.add(Calendar.DAY_OF_MONTH, -1);
	    	dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	    } while (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
	    log.debug("last weekday found is: "+cal.getTime() );
	    return cal.getTime();
    }
	
    
    public static String convertDateToString(Date dateIn, String formatString)
    {
	    SimpleDateFormat dmj = new SimpleDateFormat(formatString);	            
	    String thisDate = dmj.format(dateIn);
	    return thisDate;
    }   

}
