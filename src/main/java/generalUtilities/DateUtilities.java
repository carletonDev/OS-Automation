package generalUtilities;

import static restAssuredUtilities.Reporting.logStep;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtilities {
	/**
	 * Gets current date/time and formats string response to return only the date in a month/day/year format
	 *
	 * @return String of current date with base format
	 */
	static String getCurrentDate() {
		logStep("Getting Current Date");
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		return format.format(new Date());
	}


	/**
	 * Gets current date/time and formats string response to return only the date in specified format from
	 * the parameter
	 *
	 * @param dateFormat date format to be used
	 * @return String of current date with specified format
	 */
	public static String getCurrentDate(String dateFormat)
	{
		logStep("Getting current date time in this format: "+dateFormat);
		DateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(new Date());
	}


	/**
	 * Gets future date/time and formats string response to return only date in a month/day/year format
	 *
	 * @param addMonths number added to Month
	 * @param addDays	number added to Day
	 * @param addYears	number added to Year
	 * @return formatted String of future date
	 */
	public static String getFutureDate(int addMonths, int addDays, int addYears) {
		logStep("Getting Future Date");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, addDays);
		c.add(Calendar.MONTH, addMonths);
		c.add(Calendar.YEAR, addYears);
		Date d = c.getTime();

		DateFormat format = new SimpleDateFormat("MM/dd/yyy");
        return format.format(d);
	}

	/**
	 * Converts Month (string) to an int
	 *
	 * @param month String of month to be converted to int
	 * @return Month as a number
	 */
	public static int convertMonthString(String month) {
		logStep("Converting "+month+" to integer");
		return Month.fromString(month).getValue();
	}
  
	/**
	 * Convert String to date
	 *
	 * @param dateString date in string to be converted
	 * @return date converted from String
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String dateString) throws ParseException{
		logStep("Convert String to date");
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        return formatter.parse(dateString);
	}
}