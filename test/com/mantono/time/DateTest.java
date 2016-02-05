package com.mantono.time;

import static org.junit.Assert.*;

import java.time.DayOfWeek;

import org.junit.Before;
import org.junit.Test;

import com.mantono.time.Date;
import com.mantono.time.Time;

public class DateTest
{
	private final static Date dec15_1981_0333 = new Date(1981, 12, 15, 3, 33, 33);
	private final static Date feb7_1989_0430 = new Date(1989, 2, 7, 4, 30, 11);
	private final static Date july31_1994_0700 = new Date(1994, 7, 31, 7, 0, 0);
	private final static Date aug1_2012_0510 = new Date(2012, 8, 1, 5, 10, 13);
	
	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testDateWithLongConstructor()
	{
		Date date = new Date(1430126852L);
		assertEquals(1430126852L, date.getTime());
	}
	
	@Test
	public void testDateWithDetailedConstructor()
	{
		Date zero = new Date(1970, 1, 1, 0, 0);
		assertEquals(0, zero.getTime());
		
		Date dateFirstJan1971 = new Date(1971, 1, 1, 0, 0);
		assertEquals(Date.YEAR, dateFirstJan1971.getTime());
		
		Date dateYear1972 = new Date(1972, 1, 1, 0, 0);
		assertEquals(63072000, dateYear1972.getTime());
		
		Date dateYear1980 = new Date(1980, 1, 1, 0, 0);
		assertEquals(315532800, dateYear1980.getTime());
		
		Date dateYear2015 = new Date(2015, 1, 1, 0, 0);
		assertEquals(1420070400, dateYear2015.getTime());
		
		Date date2015Jan03 = new Date(1420243200L);
		Date dateDetailed2015Jan03 = new Date(2015, 1, 3, 0, 0);
		assertEquals("Difference is " + Math.abs(date2015Jan03.getTime() - dateDetailed2015Jan03.getTime()) + " seconds, ", date2015Jan03, dateDetailed2015Jan03);
		
		Date date2015April27 = new Date(1430134140L);
		Date dateDetailed2015April27 = new Date(2015, 4, 27, 11, 29);
		assertEquals("Difference is " + Math.abs(date2015April27.getTime() - dateDetailed2015April27.getTime()) + " seconds, ", date2015April27, dateDetailed2015April27);
	}
	
	@Test
	public void testDateWithDetailedConstrcutorAndSeconds()
	{
		Date dateLong = new Date(1430134146L);
		Date dateDetailed = new Date(2015, 4, 27, 11, 29, 6);
		assertEquals(dateLong, dateDetailed);
	}

	@Test
	public void testDateLong()
	{
		Date date = new Date(1337L);
		assertEquals(1337, date.getTime());
	}
	
	@Test
	public void testLeapYearCheck()
	{
		assertTrue(Date.isLeapYear(2012));
		assertTrue(Date.isLeapYear(2008));
		assertTrue(Date.isLeapYear(2016));
		assertTrue(Date.isLeapYear(2020));
		assertFalse(Date.isLeapYear(2001));
		assertFalse(Date.isLeapYear(1700));
		assertFalse(Date.isLeapYear(1800));
		assertFalse(Date.isLeapYear(1900));
		assertTrue(Date.isLeapYear(2000));
		assertTrue(Date.isLeapYear(1972));
	}

	@Test
	public void testGetDifference()
	{
		Date dateLong = new Date(1430126946L);
		assertTrue(dateLong.getDifference() > 0);
	}

	@Test
	public void testGetTimestamp()
	{
		Date dateDetailed = new Date(2015, 4, 27, 11, 29, 6);
		assertEquals(1430134146L, dateDetailed.getTime());
	}

	@Test
	public void testCompareTo()
	{
		Date first = new Date(0);
		Date second = new Date(1);
		Date last = new Date();
		
		assertTrue(first.compareTo(second) < 0);
		assertTrue(first.compareTo(last) < 0);
		assertTrue(second.compareTo(first) > 0);
		assertTrue(last.compareTo(first) > 0);
		assertTrue(first.compareTo(first) == 0);
	}
	
	@Test
	public void testGetYear()
	{
		assertEquals(1989, feb7_1989_0430.getYear());
		assertEquals(1994, july31_1994_0700.getYear());
		final Date year4000 = new Date(4000, 1, 1, 1, 1, 1);
		assertEquals(4000, year4000.getYear());
	}
	
	@Test
	public void testGetMonth()
	{
		assertEquals(2, feb7_1989_0430.getMonth());
		assertEquals(7, july31_1994_0700.getMonth());
		assertEquals(8, aug1_2012_0510.getMonth());
	}
	
	@Test
	public void testGetDay()
	{
		assertEquals(7, feb7_1989_0430.getDay());
		assertEquals(31, july31_1994_0700.getDay());
		assertEquals(1, aug1_2012_0510.getDay());
	}
	
	@Test
	public void testGetHour()
	{
		assertEquals(4, feb7_1989_0430.getHour());
		assertEquals(7, july31_1994_0700.getHour());
		assertEquals(5, aug1_2012_0510.getHour());
	}
	
	@Test
	public void testGetMinute()
	{
		assertEquals(30, feb7_1989_0430.getMinute());
		assertEquals(0, july31_1994_0700.getMinute());
	}
	
	@Test
	public void testGetSecond()
	{
		assertEquals(11, feb7_1989_0430.getSecond());
		assertEquals(0, july31_1994_0700.getSecond());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IllegalDateNegativeYear()
	{
		Date date = new Date(-1, 1, 1, 1, 1, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IllegalDateYearBefore1970()
	{
		Date date = new Date(1969, 1, 1, 1, 1, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IllegalDateMonthIsZero()
	{
		Date date = new Date(2000, 0, 1, 1, 1, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IllegalDateDayIsZero()
	{
		Date date = new Date(2000, 1, 0, 1, 1, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IllegalDateHourIsNegative()
	{
		Date date = new Date(2000, 1, 1, -1, 1, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IllegalDateMinuteIsNegative()
	{
		Date date = new Date(2000, 1, 1, 1, -1, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IllegalDateSecondIsNegative()
	{
		Date date = new Date(2000, 1, 1, 1, 1, -1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IllegalDateDayIsGreaterThan31()
	{
		Date date = new Date(2000, 1, 32, 0, 0, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IllegalDateMonthIsGreaterThan12()
	{
		Date date = new Date(2000, 13, 1, 0, 0, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IllegalDateHourIsGreaterThan23()
	{
		Date date = new Date(2000, 1, 1, 24, 0, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IllegalDateMinuteIsGreaterThan59()
	{
		Date date = new Date(2000, 1, 1, 0, 60, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IllegalDateSecondsIsGreaterThan59()
	{
		Date date = new Date(2000, 1, 1, 0, 0, 60);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IllegalDateDayIs31In30Month()
	{
		Date date = new Date(2000, 4, 31, 0, 0, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IllegalDateDayIs30InFebruary()
	{
		Date date = new Date(2000, 4, 31, 0, 0, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IllegalDateDayIs29InFebruaryInCommonYear()
	{
		Date date = new Date(2001, 2, 29, 0, 0, 0);
	}
	
	@Test
	public void testGetDayOfWeek()
	{
		final Date monday = new Date(2015, 5, 4, 0, 0, 0);
		assertEquals(DayOfWeek.MONDAY, monday.getDayOfWeek());
		
		final Date tuesday = new Date(2015, 5, 5, 0, 0, 0);
		assertEquals(DayOfWeek.TUESDAY, tuesday.getDayOfWeek());
		
		final Date wednesday = new Date(2015, 5, 6, 0, 0, 0);
		assertEquals(DayOfWeek.WEDNESDAY, wednesday.getDayOfWeek());
		
		final Date thursday = new Date(2015, 5, 7, 0, 0, 0);
		assertEquals(DayOfWeek.THURSDAY, thursday.getDayOfWeek());
		
		final Date friday = new Date(2015, 5, 8, 0, 0, 0);
		assertEquals(DayOfWeek.FRIDAY, friday.getDayOfWeek());
		
		final Date saturday = new Date(2015, 5, 9, 0, 0, 0);
		assertEquals(DayOfWeek.SATURDAY, saturday.getDayOfWeek());
		
		final Date sunday = new Date(2015, 5, 10, 0, 0, 0);
		assertEquals(DayOfWeek.SUNDAY, sunday.getDayOfWeek());
		
		final Date aug01_2015 = new Date(2015, 8, 1, 14, 0, 0);
		assertEquals(DayOfWeek.SATURDAY, aug01_2015.getDayOfWeek());
		
		final Date aug02_2015 = new Date(2015, 8, 2, 15, 0, 0);
		assertEquals(DayOfWeek.SUNDAY, aug02_2015.getDayOfWeek());
	}
}
