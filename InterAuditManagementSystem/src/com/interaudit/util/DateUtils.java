package com.interaudit.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * This class contains methods to handle dates.
 *
 * @author B.Blocail
 * @version $Id: DateUtils.java,v 1.2 2006/01/19 18:06:43 detienne Exp $
 * <pre>
 * Changes
 * =======
 * Date            Name       Issue Id    Description
 * </pre>
 */

/**
*
*/



public class DateUtils {
	private static GregorianCalendar theCalendar = new GregorianCalendar();

	/**
	* Default date format.<BR>
	* It is specified by the parameter DEFAULT_DATE_FORMAT in the properties file
	* If not given it equals to  dd/MM/yyyy.
	*/
	public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";


	/**
	* Default constructor
	*/
	public DateUtils()
	{
	}
	/**
	* Builds a date from the year, month, day
	* @param int year of the date
	* @param int month le mois (between 1 and 12)
	* @param int day  (between 1 and 31)
	* @return date, the date
	*/
	public static Date getDate(int year, int month, int day)
	{
		GregorianCalendar calendar = new GregorianCalendar(year,month-1,day);

		return calendar.getTime();
	}
	/**
	* Builds a date from the year, month, day
	* @param int year of the date
	* @param int month le mois (between 1 and 12)
	* @param int day  (between 1 and 31)
	* @param int, hours
	* @param int, minutes
	* @param int, seconds
	* @return date, the date
	*/
	public static Date getDate(int year, int month, int day, int hour, int min, int sec)
	{
		GregorianCalendar calendar = new GregorianCalendar(year,month-1,day,hour,min,sec);

		return calendar.getTime();
	}

	/**
  * Returns the day of the month represented by this Date object. <BR>
  * The value returned is between 1 and 31 representing the day of the
  * month that contains or begins with the instant in time represented by
  * this Date object, as interpreted in the local time zone.
  * @param date whose day one wants to know
	* @return int, day number
	*/
	public static int getDay(Date date) {
		if (date != null) {
			synchronized (theCalendar) {
				theCalendar.setTime(date);
				return theCalendar.get(GregorianCalendar.DAY_OF_MONTH);
		}
		}
		else
			return 1;
	}
	
	
	/**
	* Returns a number representing the month that contains or begins with the
	* instant in time represented by this Date object. The value returned is
	* between 1 and 12.
	* @param date whose month one wants to know
	* @return int, month number
	*/
	public static int getMonth(Date date) {
		if (date != null) {
			synchronized (theCalendar) {
				theCalendar.setTime(date);
				return (theCalendar.get(GregorianCalendar.MONTH)+1);
			}
		}
		else
			return 1;
	}
	/**
  * Returns the year that contains or begins with the instant in time
  * represented by this Date object, as interpreted in the local time zone.
	* @param date whose year one wants to know
	* @return int, year
	*/
	public static int getYear(Date date)
	{
		synchronized(theCalendar) {
		if (date != null) {
			synchronized (theCalendar) {
				theCalendar.setTime(date);
				return (theCalendar.get(GregorianCalendar.YEAR));
			}
		}
		else
			return 2000;
		}
	}

	/**
	* Returns the hour represented by this Date object. The returned value is a
	* number (0 through 23) representing the hour within the day that contains
	* or begins with the instant in time represented by this Date object
	* @param date given date
	* @return hour between 0 and 23
	*/
	public static int getHour(Date date)
	{
		if (date != null) {
			synchronized (theCalendar) {
				theCalendar.setTime(date);
				return (theCalendar.get(GregorianCalendar.HOUR_OF_DAY));
			}
		}	else
			return 0;
	}

	/**
  * Returns the number of minutes past the hour represented by this date,
  * as interpreted in the local time zone.
  * The value returned is between 0 and 59.
  * @param date la date dont on extrait l'année
	* @return minutes de l'heure de la date
	*/
	public static int getMinute(Date date)
	{
		if (date != null)	{
			synchronized (theCalendar) {
				theCalendar.setTime(date);
				return (theCalendar.get(GregorianCalendar.MINUTE));
			}
		}
		else
			return 0;
	}

	/**
	* Returns the number of seconds past the minute represented by this date.
	* The value returned is between 0 and 61. The values 60 and 61 can only
	* occur on those Java Virtual Machines that take leap seconds into account.
  * @param date la date dont on extrait l'année
	* @return secondes de l'heure de la date
	*/
	public static int getSecond(Date date)
	{
		if (date != null) {
			synchronized (theCalendar) {
				theCalendar.setTime(date);
				return (theCalendar.get(GregorianCalendar.SECOND));
			}
		}
		else
			return 0;
	}


	/**
	*	Converts this Date object to a String with the default format.<BR>
	* The default format is given by the parameter DEFAULT_DATE_FORMAT in the
	* properties file.
	* @param Date to convert into String
	* @return String the converted date
	*/
	public static String dateAsString(Date date) {
		return dateAsString(date,DEFAULT_DATE_FORMAT);
	}

	/**
	*	Converts the current date to a String with the default format.<BR>
	* The default format is given by the parameter DEFAULT_DATE_FORMAT in the
	* properties file.
	* @return String the converted date
	*/
	public static String dateAsString() {
		return dateAsString(new Date(),DEFAULT_DATE_FORMAT);
	}

	/**
	*	Converts this Date object to a String with the specified format.<BR>
	* @param Date to convert into String
	* @param String format to use to convert the date
	* @return String the converted date
	*/
	public static String dateAsString(Date date, String format) {
		try {
			SimpleDateFormat formatter	= new SimpleDateFormat(format);
			return formatter.format(date);
		} catch (Exception th) {
			return "";
		}
	}

	/**
	*	Converts the current date to a String with a specified format.<BR>
	* @param String format to use to convert the date
	* @return String the converted date
	*/
	public static String dateAsString(String  format) {
		return dateAsString(new Date(),format);
	}



	/**
	* Converts a String into a Date. <BR>
	* The format of the string must be the default format. The default format
	* is given by the parameter DEFAULT_DATE_FORMAT in the properties file.
	* @param String to convert into a Date
	* @return Date from String
	*/
	public static Date getDate(String string) {
		return getDate(string, DEFAULT_DATE_FORMAT);
	}

	/**
	* Converts a String into a Date according to a format <BR>
	* @param String to convert
	* @param format date format (ex : dd/MM/yyyy-HH:mm:ss)
	* @return Date from string
	*/
	public static Date getDate(String string, String format) {
		Date date;

		if (string == null) {
			return null;
		}

	 	SimpleDateFormat formatter	= new SimpleDateFormat(format);
		try {
				date = formatter.parse(string);
				return date;
				/*
				String test = dateAsString(date, format);
				if (test.equals(string)) {
					return date;
				} else {
					return null;
				}
				*/
		} catch (ParseException ex) {
				//Log.error("DateUtils can not parse date "+string+" with format "+format);
				return null;
		}
	}

	/**
	* Checks if the string corresponds to a valid date <BR>
	* The format of the string must be the default format. The default format
	* is given by the parameter DEFAULT_DATE_FORMAT in the properties file.
	* @param String to check
	* @return boolean true if the string can be converted into a date
	*/
	public static boolean isValid(String string) {
		return isValid(string, DEFAULT_DATE_FORMAT);
	}

	/**
	* Checks if the string corresponds to a valid date according
	* the given format <BR>
	* @param String to check
	* @param String date format (ex : dd/MM/yyyy-HH:mm:ss)
	* @return boolean
	*/
	public static boolean isValid(String string, String format) {
		boolean result = false;
		if (string != null) {
			Date date = getDate(string, format);
			if (date != null) {
				if (string.equals(dateAsString(date, format))) {
					result = true;
				}
			}
		}
		return result;
	}


	/**
	* Gets the maximum between 2 days
	*	@param date1 first date to compare
	* @param date2 second date to compare
	*	@return date1 if date1>date2 otherwise returns date2
	*/
	public static Date max(Date date1, Date date2) {
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.after(date2)) {
			return date1;
		} else {
			return date2;
		}
	}

	/**
	* Gets the minimum between 2 days
	*	@param date1 first date to compare
	* @param date2 second date to compare
	*	@return date1 if date1<date2 otherwise returns date2
	*/
	public static Date min(Date date1, Date date2) {
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.after(date2)) {
			return date2;
		} else {
			return date1;
		}
	}

	/**
	* Checks if the date is the end of the term
	*@param date to check
	*@return true if the date is the end of the term
	*/
	public static boolean isEndOfATerm(Date date) {
		try {
			int day = getDay(date);
			int month = getMonth(date);
			if (((month == 3) || (month == 12)) && (day == 31) ) {
				return true;
			}
			if (((month == 6) || (month == 9)) && (day == 30) ) {
				return true;
			}
			return false;
		} catch (Exception th) {
			return false;
		}
	}

	/**
	* Checks if the date is the end of the month
	*@param date to check
	*@return true if the date is the end of the month
	*/
	public static boolean isEndOfAMonth(Date date) {
		try {
			int day = getDay(date);
			int month = getMonth(date);
			int year = getYear(date);
			if (((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12)) && (day == 31) ) {
					return true;
			}
			if (((month == 4) || (month == 6) || (month == 9) || (month == 11) ) && (day == 30) ) {
					return true;
			}

			if ((month == 2)) {
				String endOfMonth = "29/02/"+year;
				boolean isALeapYear = false;
				if (isValid(endOfMonth)) {
					isALeapYear = true;
				}
				if (isALeapYear && (day == 29)) {
					return true;
				}
				if (! isALeapYear && (day == 28)) {
					return true;
				}
			}
			return false;
		} catch (Exception th) {
			return false;
		}

	}
	/**
	* Checks if the date is the beginning of a month
	*@param date to check
	*@return true if the date is the beginning of the month
	*/
	public static boolean isBeginningOfAMonth(Date date) {
		int day = getDay(date);
		if (day == 1) {
			return true;
		}
		return false;
	}


	/**
	* Checks if the date is the beginning of a term
	*@param date to check
	*@return true if the date is the beginning of a term
	*/
	public static boolean isBeginningOfATerm(Date date) {
		int day = getDay(date);
		int month = getMonth(date);
		if (day != 1) {
			return false;
		} else {
			if ((month == 1) || (month == 4) || (month == 7) || (month == 10)) {
				return true;
			}
		}
		return false;
	}

	/**
	*	checks if the second date is the next day of the first date
	* @param date1 initial date
	* @param date2 day to compare
	* @return true si date2 is next day of date1
	*/
	public static boolean isNextDay(Date date1, Date date2) {

		Date nextDay = getNextDay(date1);
		return isSameDay(nextDay, date2);
	}

	/**
	*	Checks if two dates have the same day
	* @param date1 initial date
	* @param date2 date to compare
	* @return true si date2 is same day as date1
	*/
	public static boolean isSameDay(Date date1, Date date2) {
		int year;
		int month;
		int day;

		year = getYear(date1);
		month = getMonth(date1);
		day = getDay(date1);
		if ((year == getYear(date2)) &&
			  (month == getMonth(date2)) &&
			  (day == getDay(date2))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	* Gets a date corresponding to the next day of the given date
	* @param date initial date
	* @return next day of given date
	*/
	public static Date getNextDay(Date date) {
		long dateLong = date.getTime()+1000*24*3600;
		return new Date(dateLong);
	}

	/**
	*	Gets a date corresponding to the first day of next month
	* @return first day of next month
	*/
	public static Date getFirstDayOfNextMonth() {
		return getFirstDayOfNextMonth(new Date());
  }

  /**
	*	Gets the date corresponding to the last day of the current month
	* @return the date corresponding to the last day of the current month
	*/
	public static Date getLastDayOfCurrentMonth() {
	 	return getLastDayOfMonth(new Date());
  }


  /**
	*	Gets the date corresponding to the first day of the current month
	* @param date initiale date
	* @return Date corresponding to the first day of the next month of the given date
	*/
	public static Date getFirstDayOfNextMonth(Date date) {
		Date today = date;
   	for (int numday = 0 ;numday<31; numday++) {
			today = getNextDay(today);
			if (isBeginningOfAMonth(today)) {
				break;
			}
		}
		return today;
  }

  /**
	*	Gets the date corresponding to the last day of the month of the given date
	* @param date initiale date
	* @return Date corresponding to the last day of the month of the given date
	*/
  public static Date getLastDayOfMonth(Date date) {
	 	Date today = date;
		for (int numday = 0 ;numday<31; numday++) {
			if (isEndOfAMonth(today)) {
				break;
			}
			today = getNextDay(today);
		}
		return today;
  }

  /**
	*	Retourne le numéro de la semaine de la date courante
	* @return le numéro de la semaine dans l'année
	*/
  public static int getWeek() {
  	return getWeek(new Date());
  }

  /**
	*	Gets the week number of the current date
	* @param date
	* @return week number of the current date
	*/
  public static int getWeek(Date date) {
  	String week = dateAsString(date, "ww");
  	return Integer.valueOf(week).intValue();
  }

  /**
	*	Gets the week number of the given date
	* @return the week number of the given date
	*/
  public static int getDayInYear() {
  	return getDayInYear(new Date());
  }

  /**
	* Gets the day number within the year of the given date
	* @param date
	* @return the number of the day in the year
	*/
  public static int getDayInYear(Date date) {
  	String week = dateAsString(date, "DDD");
  	return Integer.valueOf(week).intValue();
  }

  /**
  * Adds a specific amount of time (experssed in days) to the current date
  * @param int , number of days to add
	* @return Date, the calculated date
	*/
	public static Date addDays(int nbDays) {
		return addDays(new Date(),nbDays);
	}

  /**
  * Adds a specific amount of time (experssed in days) to the given date
	* @param Date
  * @param int , number of days to add
	* @return Date, the calculated date
	*/
	public static Date addDays(Date date, int nbDays) {
		int year = getYear(date);
		int month = getMonth(date);
		int day = getDay(date);

		Date result = getDate(year, month, day + nbDays);
		return result;
	}

	/**
	* Gets the amount of time (given in days), between the given date and the current date
	* @param   theDate  date
	* @return  int, number of days between date and current date
	*/
 	public static int getNbDays(Date theDate) {
 		return getNbDays(new Date(), theDate);
 	}
 	
 	/**
 	 * @param annee
 	 * @param mois
 	 * @param jour
 	 * @return true if it is a valid date
 	 */
 	public boolean estValide(int annee, int mois, int jour){
 		Calendar c = Calendar.getInstance();
 		c.setLenient(false);
 		c.set(annee,mois,jour);        
 		try{
 		  c.getTime();  
 		}
 		catch(IllegalArgumentException iAE){
 		  return false;
 		}
 		
 		return true;
 	}


	/**
	* Gets the amount of time (given in days), between two dates
	* @param   theFirstDate  date
	* @param   theSecondDate  date
	* @return  int, number of days between the 2 dates
	*/
 	public static int getNbDays(Date theFirstDate, Date theSecondDate) {
 		Date higherDate = null;
 		Date lowerDate = null;
 		int result =0;

 		if (theFirstDate == null || theSecondDate == null) {
 			return result;
 		}	else {

 			if (isSameDay(theFirstDate, theSecondDate))
 				return 0;

 			higherDate = max(theFirstDate, theSecondDate);
 			lowerDate = min(theFirstDate, theSecondDate);

	 		while (!isSameDay(lowerDate, higherDate)) {
				lowerDate = getNextDay(lowerDate);
				result ++;
			}
 			return result;
 		}
 	}
 	
 	public static int getNumWeeksForYear(int year) {   
		  Calendar c = Calendar.getInstance();   
		    
		  c.set(year, 0, 1);
		  c.setFirstDayOfWeek(Calendar.MONDAY);

		 // return  c.getActualMaximum(Calendar.WEEK_OF_YEAR);

		  return c.getMaximum(Calendar.WEEK_OF_YEAR);    
		}  
 	
 	/*
 	 public static String now(String dateFormat) {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    return sdf.format(cal.getTime());

  }

  public static void  main(String arg[]) {
     System.out.println(DateUtils.now("dd MMMMM yyyy"));
     System.out.println(DateUtils.now("yyyyMMdd"));
     System.out.println(DateUtils.now("dd.MM.yy"));
     System.out.println(DateUtils.now("MM/dd/yy"));
     System.out.println(DateUtils.now("yyyy.MM.dd G 'at' hh:mm:ss z"));
     System.out.println(DateUtils.now("EEE, MMM d, ''yy"));
     System.out.println(DateUtils.now("h:mm a"));
     System.out.println(DateUtils.now("H:mm:ss:SSS"));
     System.out.println(DateUtils.now("K:mm a,z"));
     System.out.println(DateUtils.now("yyyy.MMMMM.dd GGG hh:mm aaa"));
  }

 	 */
}
