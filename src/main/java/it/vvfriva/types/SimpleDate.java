package it.vvfriva.types;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * tipo utilizzato per data
 * @author simone
 *
 */
public class SimpleDate {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	Date localDateTime = null;
	public SimpleDate(String dateTime) throws ParseException {
		// Format for input
		this.localDateTime = sdf.parse(dateTime);
	}
	public Date toDate() {
		return this.localDateTime;
	}
}
