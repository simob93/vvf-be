package it.vvfriva.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class Utils {
	/**
	 * 
	 * @param id
	 */
	public static boolean isValidId(Integer id) {
		return (id != null) && (id > 0);
	}
	public static boolean isValidDate(Date data) {
		return data != null;
	}
	public static boolean geZero(Integer number) {
		return number != null && integerCompareTo(number, 0) >= 0;
	}
	public static boolean gtZero(Integer number) {
		return integerCompareTo(number, 0) > 0;
	}
	
	/**
	 * 
	 * @param chart
	 */
	public static boolean isTrueValue(String value) {
		if (isEmptyString(value)) {
			return false;
		}
		return CostantiVVF.TRUE_CHAR.equals(value);
	}
	/**
	 * 
	 * @param date
	 * @param dateTime
	 * @return
	 */
	public static Date setTime(Date date, Date dateTime) {
		LocalDateTime ldt = new LocalDateTime(dateTime);
		return new LocalDateTime(date).withTime(ldt.getHourOfDay(), ldt.getMinuteOfHour(), ldt.getSecondOfMinute(), 0).toDate();
	}
	/**
	 * 
	 * @param date
	 * @param format
	 */
	public static String parseDate(Date date, String format) {
		if (isValidDate(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}
		return "";
	}
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String parseDate(Date date) {
		return parseDate(date, CostantiVVF.DEFAULT_DATE_FORMAT);
	}
	/**
	 * 
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> List<String> convertListToString(List<T> list) {
		return list.stream().map(cmp -> cmp.toString()).collect(Collectors.toList());
	}
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static List<Integer> convertStringToIntegerList(String str) { 
		if (!isEmptyString(str)) {
			return Arrays.asList(str.split(","))
					.stream().map(r -> Integer.parseInt(r))
					.collect(Collectors.toList());
		}
		return new ArrayList<Integer>();
	}
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	public static <T> String implodeListToSql(List<T> list) {
		return implodeList(list, ",");  
	}
	/**
	 * 
	 * @param list
	 * @param separator
	 * @return
	 */
	public static <T> String implodeList(List<T> list) {
		return implodeList(list, ";");  
	}
	public static <T> String implodeList(List<T> list, String separator) {
		return Strings.join(convertListToString(list), separator.charAt(0));  
	}
	/**
	 * 
	 * @param list
	 * @return
	 */
	public static <T> boolean isEmptyList(List<T> list) {
		return list == null || (list != null && list.size() == 0);
	}
	/**
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isEmptyString(String string) {
		return string == null || (string != null && string.isEmpty());
	}
	/**
	 * Metodo di comparazione tra stringhe
	 * @param target1
	 * @param target2
	 * @return
	 */
	public static int stringCompareTo(String target1, String target2) {
		
		if (target1 == null && target2 == null) {
			return 0;
		} else {
			if (target1 == null) {
				return 1;
			}
			if (target2 == null) {
				return -1;
			}
		}
		//pulisco eventuali spazi
		String appoTarget1 = target1.trim();
		String appoTarget2 = target2.trim();
		return appoTarget1.compareTo(appoTarget2);
	}
	/**
	 * 
	 * @param target1
	 * @param target2
	 * @return
	 */
	public static int integerCompareTo(Integer target1, Integer target2) {
		
		if (target1 == null && target2 == null) {
			return 0;
		} else {
			if (target1 == null) {
				return 1;
			}
			if (target2 == null) {
				return -1;
			}
		}
		return target1.compareTo(target2);
	}
	/**
	 * 
	 * @param date
	 * @param type
	 * @param amount
	 * @return
	 */
	public static Date dateAdd(Date date, Integer type,  Integer amount) {
		Date dateResult = null;
		amount = amount != null ? amount : 0;
		Calendar calendar = null;
		if (date != null) {
			  calendar = Calendar.getInstance();
			  calendar.setTime(date);
			  calendar.add(type, amount);
			  dateResult = calendar.getTime();
		}
		return dateResult; 
	}
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static Date startOfDay(Date data) {
		LocalDateTime localDate = new LocalDateTime(data);
		localDate.withTime(0, 0, 0, 0);
		return localDate.toDate();
	}
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static Date endOfDay(Date data) {
		LocalDateTime localDate = new LocalDateTime(data);
		localDate.withTime(23, 59, 59, 59);
		return localDate.toDate();
	}
	/**
	 * 
	 * @param y
	 * @param m
	 * @param d
	 * @return
	 */
	public static Date encodeDate(Integer y, Integer m, Integer d) {
		return encodeDate(y, m, d, null, null);
	}
	/**
	 * 
	 * @param y
	 * @param m
	 * @param d
	 * @param h
	 * @param mm
	 * @return
	 */
	public static Date encodeDate(Integer y, Integer m, Integer d, Integer h, Integer mm) {
		y = isValidId(y) ? y : 1899;
		m = isValidId(m) ? m : 12;
		d = isValidId(d) ? d : 31;
		h = isValidId(h) ? h : 0;
		mm = isValidId(mm) ? mm : 0;
		Calendar cal = Calendar.getInstance();
		cal.set(y, (m - 1), d, h, mm, 0);
		return cal.getTime();
	}
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isTrueOrFalse(Integer value) {
		return (integerCompareTo(value, CostantiVVF.TRUE) == 0) ? true : false;
	}
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static Integer getIntegerBoolValue(boolean value) {
		return value ? CostantiVVF.TRUE: CostantiVVF.FALSE;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T coalesce(T param, T...values) {
		if(param == null) {
			return Arrays.asList(values).stream().filter(v -> v != null).findFirst().get();
		}
		return param;
	}
	/**
	 * ritorna il primo lunedi della settimana in base alla data passata
	 * @param data
	 * @return
	 */
	public static Date getMonday(Date data) {
		return new LocalDate(data).withDayOfWeek(DateTimeConstants.MONDAY).toDate();
	}
	/**
	 * ruitorna la prima domenica della settimana in basa alla data passata
	 * @param data
	 * @return
	 */
	public static Date getSunday(Date data) {
		return new LocalDate(data).withDayOfWeek(DateTimeConstants.SUNDAY).toDate();
	}
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static Date getSaturday(Date data) {
		return new LocalDate(data).withDayOfWeek(DateTimeConstants.SATURDAY).toDate();
	}
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static Date getFriday(Date data) {
		return new LocalDate(data).withDayOfWeek(DateTimeConstants.FRIDAY).toDate();
	}
	/**
	 * ritorna il primo giorno del mese
	 * @param data
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date data) {
		return new LocalDate(data).dayOfMonth().withMinimumValue().toDate();
	}
	/**
	 * 
	 * @param data
	 * @param nr
	 * @return
	 */
	public static Date addDays(Date data, Integer nr) {
		return new LocalDate(data).plusDays(nr).toDate();
	}
	/**
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int dateCompareTo(Date d1, Date d2) {
		LocalDate target1 = new LocalDate(d1);
		LocalDate target2 = new LocalDate(d2);
		return target1.compareTo(target2);
	}
	/**
	 * 
	 * @param data
	 * @param nr
	 * @return
	 */
	public static Date addMonths(Date data, Integer nr) {
		return new LocalDate(data).plusMonths(nr).toDate();
	}
	/**
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int daysBetween(Date d1, Date d2) {
		return Days.daysBetween(new LocalDate(d1), new LocalDate(d2)).getDays();
	}
	/**
	 * 
	 * @param msg
	 * @return
	 */
	public static String errorInMethod(String msg) {
		StackTraceElement el = new Throwable().getStackTrace()[1];
		 return el.getClassName() + "." + el.getMethodName() + ":" + msg;
	}
	/**
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean stringEguals(String str1, String str2) {
		return stringEquals(str1, str2, true);
	}
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isFestivo(Date data) {
		
		if (!isValidDate(data)) return false;
		
		LocalDate dt = new LocalDate(data);
		
		int giorno = dt.getDayOfMonth();
		int mese = dt.getMonthOfYear();
		int anno = dt.getYear();
		
		switch (mese) {
		case DateTimeConstants.DECEMBER:
			if(giorno == 25 || giorno == 26 || giorno == 8) {
				return true;
			}
			break;
		case DateTimeConstants.NOVEMBER:
			if(giorno == 1) {
				return true;
			}
			break;
		case DateTimeConstants.AUGUST:
			if(giorno == 15) {
				return true;
			}
			break;
		case DateTimeConstants.JUNE:
			if(giorno == 2) {
				return true;
			}
			break;
		case DateTimeConstants.MAY:
			if(giorno == 1) {
				return true;
			}
			break;
		case DateTimeConstants.APRIL:
			if(giorno == 25) {
				return true;
			}
			break;
		case DateTimeConstants.JANUARY:
			if(giorno == 1 || giorno == 6) {
				return true;
			}
			break;
		default:
			
		}
		// calcolo della pasqua secondo Meeus
        int a = anno % 19,
            b = anno / 100,
            c = anno % 100,
            d = b / 4,
            e = b % 4,
            g = (8 * b + 13) / 25,
            h = (19 * a + b - d - g + 15) % 30,
            j = c / 4,
            k = c % 4,
            m = (a + 11 * h) / 319,
            r = (2 * e + 2 * j - k - h + m + 32) % 7,
            n = (h - m + r + 90) / 25,
            p = (h - m + r + n + 19) % 32;
    	LocalDate easter =  new LocalDate(anno, n, p);
    	if (dt.compareTo(easter) == 0) {
    		return true;
    	}
    	if(easter.plusWeeks(1).withDayOfWeek(DateTimeConstants.MONDAY).compareTo(dt) == 0) {
    		// lunedi di pasquetta
    		return true;
    	}
		return false;
	}
	/**
	 * 
	 * @param int1
	 * @param int2
	 * @return
	 */
	public static boolean integerEquals(Integer int1, Integer int2) {
		if (!isValidId(int1) || !isValidId(int2)) {
			return false;
		}
		return int1.equals(int2);
	}
	/** 
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean stringEquals(String str1, String str2, boolean ignoreCase) {
		if (isEmptyString(str1) || isEmptyString(str2)) {
			return false;
		}
		if (ignoreCase) {
			return str1.equalsIgnoreCase(str2);	
		} else {
			return str1.equals(str2);
		}
		
	}
}

