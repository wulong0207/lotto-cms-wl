package com.hhly.cms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类；SimpleDateFormat实例属于非线程同步的，必须注意线程问题。
 * @author HouXB
 */
public class DateUtil
{

	public static final String DEFAULT_DATE_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";

	/**
	 * @return 根据默认格式(yyyy-MM-dd HH:mm:ss)获取当前时间
	 */
	public static String getNow()
	{
		return new SimpleDateFormat(DEFAULT_DATE_FORMAT_STRING).format(new Date());
	}

	/**
	 * @param format - 日期格式
	 * @return 根据参数日期格式获取当前时间
	 */
	public static String getNow(String format)
	{
		SimpleDateFormat sdf = null;
		if (null == format || "".equals(format))
			sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT_STRING);
		else
			sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	/**
	 * @param date - 字符型日期
	 * @return 根据默认格式(yyyy-MM-dd HH:mm:ss)获取日期
	 * @throws ParseException - 如果日期格式与默认格式不匹配不能进行转换
	 */
	public static Date convertStrToDate(String date) throws ParseException
	{
		if (null == date || "".equals(date))
			return null;
		return new SimpleDateFormat(DEFAULT_DATE_FORMAT_STRING).parse(date);
	}

	public static Date convertStrToDate(String date, String format) throws ParseException
	{
		if (null == date || "".equals(date))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}

	public static String convertDateToStr(Date date)
	{
		if (date == null)
			return null;
		else
			return new SimpleDateFormat(DEFAULT_DATE_FORMAT_STRING).format(date);
	}

	public static String convertDateToStr(Date date, String format)
	{
		SimpleDateFormat sdf = null;
		if (null == format || "".equals(format))
			sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT_STRING);
		else
			sdf = new SimpleDateFormat(format);

		return sdf.format(date);
	}

	public static int getSecondOfNow()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.SECOND);
	}

	public static int getMinuteOfNow()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.MINUTE);
	}

	public static String getYear(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return String.valueOf(cal.get(Calendar.YEAR)).substring(2, 4);

	}

	public static String getTimeByMillSecond(long ms)
	{
		if (ms >= 1000)
		{
			long s = ms / 1000;
			return getTimeBySecond(s);
		}
		else
		{
			return null;
		}
	}

	public static String getTimeBySecond(long second)
	{
		String h = String.valueOf(second / 3600);
		if (h.length() < 2)
			h = "0" + h;
		String m = String.valueOf(second % 3600 / 60);
		if (m.length() < 2)
			m = "0" + m;
		String s = String.valueOf(second % 3600 % 60);
		if (s.length() < 2)
			s = "0" + s;
		return h + ":" + m + ":" + s;
	}

	public static String getTimePlus(Date beginDate, Date endDate)
	{
		if (null == beginDate || null == endDate)
		{
			return "";
		}
		return getTimeByMillSecond(Math.abs(endDate.getTime() - beginDate.getTime()));
	}

	public static Date addSeconds(Date date, int seconds)
	{
		long ldate = date.getTime();
		ldate = ldate + seconds * 1000;
		return new Date(ldate);
	}

	public static Date addMinutes(Date date, int minutes)
	{
		long ldate = date.getTime();
		ldate = ldate + (long) minutes * 1000l * 60l;
		return new Date(ldate);
	}

	public static Date addHours(Date date, int hours)
	{
		long ldate = date.getTime();
		ldate = ldate + (long) hours * 1000l * 60l * 60l;
		return new Date(ldate);
	}

	public static Date addDays(Date date, int days)
	{
		long ldate = date.getTime();
		ldate = ldate + (long) days * 1000l * 60l * 60l * 24l;
		return new Date(ldate);
	}

	public static int offsetSeconds(Date date1, Date date2)
	{
		return (int) ((date1.getTime() - date2.getTime()) / 1000);
	}

	public static int offsetMinutes(Date date1, Date date2)
	{
		return (int) ((date1.getTime() - date2.getTime()) / 60000);
	}

	/**
	 * @see 去除Date参数的时间部分，即取当天的 0点0分0秒
	 * @param date
	 * @return
	 */
	public static Date removeTime(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * @see 去除Date参数的时间部分，即取当天的 59时59分59秒
	 * @param date
	 * @return
	 */
	public static Date getNeedTime(int hour,int minute,int second,int addDay,int ...args){
	    Calendar calendar = Calendar.getInstance();
	    if(addDay != 0){
	        calendar.add(Calendar.DATE,addDay);
	    }
	    calendar.set(Calendar.HOUR_OF_DAY,hour);
	    calendar.set(Calendar.MINUTE,minute);
	    calendar.set(Calendar.SECOND,second);
	    if(args.length==1){
	        calendar.set(Calendar.MILLISECOND,args[0]);
	    }
	    return calendar.getTime();
	}	

	public static int getDayOfWeekByStrDate(String strDate, String format) throws Exception
	{
		Date date = convertStrToDate(strDate, format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int result = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (result == 0)
			result = 7;
		return result;
	}
	
	/**
	 * 判断当前时间是否在某一时间段
	 * @param format 时间格式  "HH:mm"
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 * @throws Exception
	 */
	public static boolean isCurDateAtTimeRange(String format,String startTime,String endTime) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String currentDate = sdf.format(new Date());
		Date currDate = sdf.parse(currentDate);//当前时间
	    Date startDate = sdf.parse(startTime);//每节开始时间
	    Date endDate = sdf.parse(endTime);//每节结束时间
	    if(currDate.after(startDate) && currDate.before(endDate)){
	        return true;
	    }else{
	    	return false;
	    }
	}
	
	public static void main(String[] args) {
		System.out.println(convertDateToStr(getNeedTime(23,59,59,0)));
	}
}
