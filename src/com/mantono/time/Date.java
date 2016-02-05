package com.mantono.time;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.concurrent.TimeUnit;

import org.hamcrest.core.SubstringMatcher;

public class Date implements Comparable<Date>, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8151256814932765592L;
	public static final short HOUR = 3600;
	public static final int DAY = 86_400;
	public static final int WEEK = 604_800;
	public static final int YEAR = 31_536_000;
	public static final int LEAP_YEAR = 31_622_400;
	private final long timestamp;
	private final TimeUnit timeUnit;
	
	public Date(final long timestamp, TimeUnit timeUnit)
	{
		this.timestamp = timestamp;
		this.timeUnit = timeUnit;
	}
	
	public Date(final long timestamp)
	{
		this(timestamp, TimeUnit.SECONDS);
	}
	
	public Date()
	{
		this(System.currentTimeMillis() / 1000L);
	}
	
	public Date(Timestamp timestamp)
	{
		this(timestamp.getTime()/1000);
	}
	
	public Date(final int year, final int month, final int day, final int hour, final int minute)
	{
		this(year, month, day, hour, minute, 0);
	}
	
	public Date(final int year, final int month, final int day, final int hour, final int minute, final int second)
	{
		checkForIllegalDate(year, month, day, hour, minute, second);
		
		Long accumulatedTime = countYears(year);
		accumulatedTime += countMonths(year, month);
		
		accumulatedTime += (day-1)*DAY;
		accumulatedTime += hour*HOUR;
		accumulatedTime += minute*60;
		accumulatedTime += second;
		this.timestamp = accumulatedTime;
		this.timeUnit = TimeUnit.SECONDS;
	}
	
	private void checkForIllegalDate(int year, int month, int day, int hour, int minute, int second)
	{
		if(year < 1970)
			throw new IllegalArgumentException("Year can not be less than 1970");
		if(month < 1 || month > 12)
			throw new IllegalArgumentException("Month must be between 1 and 12");
		if(day < 1 || day > 31)
			throw new IllegalArgumentException("Day must be between 1 and 31");
		if(hour < 0 || hour > 23)
			throw new IllegalArgumentException("Hour must be between 0 and 23");
		if(minute < 0 || minute > 59)
			throw new IllegalArgumentException("Minute must be between 0 and 59");
		if(second < 0 || second > 59)
			throw new IllegalArgumentException("Seconds must be between 0 and 59");
		if(month == 2 && day == 29 && !isLeapYear(year))
			throw new IllegalArgumentException("Combination of year " + year + " and month February and day " + day + " is not valid");
		if(month == 2 && day == 30)
			throw new IllegalArgumentException("February may never have 30 days");
		if(day == 31)
			if(month == 2 || month == 4 ||month == 6 || month == 9 ||month == 11)
				throw new IllegalArgumentException(month + " does not have 31 days");
	}

	private Long countMonths(int year, int month)
	{
		long monthInSeconds = 0;
		for(byte countedMonths = 1; countedMonths < month; countedMonths++)
		{
			if(countedMonths == 1 || countedMonths == 3 ||countedMonths == 5 || countedMonths == 7 ||countedMonths == 8||countedMonths == 10)
				monthInSeconds += 31*DAY;
			else if(countedMonths == 2)
			{
				if(isLeapYear(year))
					monthInSeconds += 29*DAY;
				else
					monthInSeconds += 28*DAY;
			}
			else
				monthInSeconds += 30*DAY;
		}
		return monthInSeconds;
	}

	private Long countYears(int year)
	{
		long yearsInSeconds = 0;
		for(int countedYears = 1970; countedYears < year; countedYears++)
		{
			if(isLeapYear(countedYears))
				yearsInSeconds += LEAP_YEAR;
			else
				yearsInSeconds += YEAR;
		}
		return yearsInSeconds;
	}

	public static boolean isLeapYear(final int year)
	{
		if(year < 0)
			throw new IllegalArgumentException("Negative values are not allowed for parameter year");
		if(year % 4 != 0)
			return false;
		if(year % 100 == 0)
			if(year % 400 == 0)
				return true;
			else
				return false;
		return true;
	}

	public long getDifference()
	{
		long unixTime = System.currentTimeMillis() / 1000L;
		return unixTime - timestamp;
	}
	
	public long getTime()
	{
		return timestamp;
	}
	
	public Timestamp getTimestamp()
	{
		return new Timestamp(timestamp*1000);
	}
	
	public String humanReadable()
	{
		return Time.humanReadable(timestamp);
	}
	
	public String humanReadableDetailed()
	{
		return Time.humanReadableDetailed(timestamp);
	}

	public int compareTo(Date other)
	{
		if(this.timestamp < other.timestamp)
			return -1;
		else if(this.timestamp > other.timestamp)
			return 1;
		return 0;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other == null)
			return false;
		if(!(other instanceof Date))
			return false;
		Date otherDate = (Date) other;
		return this.timestamp == otherDate.timestamp;
	}
	
	@Override
	public int hashCode()
	{
		return (int) (timestamp % Integer.MAX_VALUE);
	}
	
	@Override
	public String toString()
	{
		return "" + timestamp;
	}
	
	public byte daysInMonth(int year, int month)
	{	
		if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
			return 31;
		if(month == 4 || month == 6 || month == 9 || month == 11)
			return 30;
		if(isLeapYear(year))
			return 29;
		return 28;
	}
	
	private long subtractMonths()
	{
		final int year = getYear();
		long time = timestamp - countYears(year);
		assert time < YEAR : "The remaining time must be less than a year";
		byte month = 1;
		
		while(true)
		{
			if(time >= DAY*31 && daysInMonth(year, month) == 31)
				time -= 31*DAY;
			else if(time >= DAY*30 && daysInMonth(year, month) == 30)
				time -= 30*DAY;
			else if(time >= DAY*29 && daysInMonth(year, month) == 29)
				time -= 29*DAY;
			else if(time >= DAY*28 && daysInMonth(year, month) == 28)
				time -= 28*DAY;
			else
				return time;
			month++;
		}
	}

	public int getYear()
	{
		int year = 1970;
		long time = timestamp;
		while(time > YEAR)
		{
			if(isLeapYear(year))
				time -= LEAP_YEAR;
			else
				time -= YEAR;
			year++;
		}
		return year;
	}

	public byte getMonth()
	{
		final int year = getYear();
		long time = timestamp - countYears(year);
		assert time < YEAR : "The remaining time must be less than a year";
		byte month = 1;
		
		while(true)
		{
			if(time >= DAY*31 && daysInMonth(year, month) == 31)
				time -= 31*DAY;
			else if(time >= DAY*30 && daysInMonth(year, month) == 30)
				time -= 30*DAY;
			else if(time >= DAY*29 && daysInMonth(year, month) == 29)
				time -= 29*DAY;
			else if(time >= DAY*28 && daysInMonth(year, month) == 28)
				time -= 28*DAY;
			else
				return month;
			month++;
		}
	}
	
	public Month getMonthEnum()
	{
		return Month.of(getMonth());
	}

	public byte getDay()
	{
		long remainingTime = subtractMonths();
		byte day = 1;
		for(; remainingTime > DAY; remainingTime -= DAY)
			day++;
		
		return day;
	}

	public byte getHour()
	{
		long remainingTime = subtractMonths();
		remainingTime %= DAY;
		return (byte) (remainingTime /= HOUR);
	}

	public byte getMinute()
	{
		long remainingTime = subtractMonths();
		remainingTime %= DAY;
		remainingTime %= HOUR;
		return (byte) (remainingTime /= 60);
	}

	public byte getSecond()
	{
		long remainingTime = subtractMonths();
		remainingTime %= DAY;
		remainingTime %= HOUR;
		return (byte) (remainingTime %= 60);
	}
	
	public String getClock()
	{
		String hour = "" + getHour();
		String minute = "" + getMinute();
		if(hour.length() == 1)
			hour = 0 + hour;
		if(minute.length() == 1)
			minute = 0 + minute;
		return hour + ":" + minute;
	}

	public DayOfWeek getDayOfWeek()
	{
		final long days = timestamp/DAY;
		final int dayOfWeek = (int) ((days + 3) % 7)+1;
		return DayOfWeek.of(dayOfWeek);
	}
}
