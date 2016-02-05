package com.mantono;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mantono.time.Date;
import com.mantono.time.Time;

public class TimeTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTimeLongIntNegative1()
	{
		new Time(-1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTimeLongIntNegative2()
	{
		new Time(1, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTimeLongNegative()
	{
		new Time(-1);
	}

	@Test
	public void testTimeTimeTime()
	{
		final Time t1 = new Time(10, 5);
		final Time t2 = new Time(20, 2);
		final Time sum = new Time(t1, t2);

		assertEquals(30, sum.getSeconds());
		assertEquals(7, sum.getNanoseconds());
	}

	@Test(expected = ArithmeticException.class)
	public void testTimeTimeTimeOverflowSeconds()
	{
		final Time t1 = new Time(Long.MAX_VALUE, 0);
		final Time t2 = new Time(1, 0);
		final Time sum = new Time(t1, t2);
	}

	@Test(expected = ArithmeticException.class)
	public void testTimeTimeTimeOverflowNanoseconds1()
	{
		final Time t1 = new Time(Long.MAX_VALUE, Integer.MAX_VALUE);
		final Time t2 = new Time(1, 0);
		final Time sum = new Time(t1, t2);
	}

	@Test(expected = ArithmeticException.class)
	public void testTimeTimeTimeOverflowNanoseconds2()
	{
		final Time t1 = new Time(Long.MAX_VALUE, 0);
		final Time t2 = new Time(0, Integer.MAX_VALUE);
		final Time sum = new Time(t1, t2);
	}

	@Test
	public void testGetDays()
	{
		final Time time = new Time(Date.DAY * 5);
		assertEquals(5, time.getDays());
	}

	@Test
	public void testGetHours()
	{
		final Time time = new Time(Date.HOUR * 18);
		assertEquals(18, time.getHours());
	}

	@Test
	public void testGetMinutes()
	{
		final Time time = new Time(60 * 120);
		assertEquals(120, time.getMinutes());
	}

	@Test
	public void testGetSeconds()
	{
		final Time time = new Time(100);
		assertEquals(100, time.getSeconds());
	}

	@Test
	public void testGetNanoseconds()
	{
		final Time time = new Time(0, 100);
		assertEquals(100, time.getNanoseconds());
	}

	@Test
	public void testGetModuloHours()
	{
		final Time time = new Time(Date.DAY * 3 + Date.HOUR * 6);
		assertEquals(6, time.getModuloHours());
	}

	@Test
	public void testGetModuloMinutes()
	{
		final Time time = new Time(Date.DAY * 3 + Date.HOUR * 6 + 60 * 20);
		assertEquals(20, time.getModuloMinutes());
	}

	@Test
	public void testGetModuloSeconds()
	{
		final Time time = new Time(Date.DAY * 3 + Date.HOUR * 6 + 60 * 20 + 42);
		assertEquals(42, time.getModuloSeconds());
	}

	@Test
	public void testToString()
	{
		final String expected = "5 days, 9 hours, 11 minutes, 42 seconds, 1051 nanoseconds";
		final Time time = new Time(Date.DAY * 5 + Date.HOUR * 9 + 11 * 60 + 42, 1051);
		assertEquals(expected, time.toString());
	}

	@Test
	public void testGetHumanReadableDetailed()
	{
		final String expected = "5 days, 9 hours, 11 minutes, 42 seconds";
		final String result = Time.humanReadableDetailed(Date.DAY * 5 + Date.HOUR * 9 + 11 * 60 + 42);
		assertEquals(expected, result);
	}

	@Test
	public void testGetHumanReadableDetailedWithSingleHour()
	{
		final String expected = "5 days, 1 hour, 11 minutes, 42 seconds";
		final String result = Time.humanReadableDetailed(Date.DAY * 5 + Date.HOUR * 1 + 11 * 60 + 42);
		assertEquals(expected, result);
	}

	@Test
	public void testGetHumanReadableDetailedWithNoHour()
	{
		final String expected = "5 days, 11 minutes, 42 seconds";
		final String result = Time.humanReadableDetailed(Date.DAY * 5 + 11 * 60 + 42);
		assertEquals(expected, result);
	}

	@Test
	public void testCompareToSame()
	{
		Time time1 = new Time(42, 1337);
		Time time2 = new Time(42, 1337);
		assertEquals(0, time1.compareTo(time2));

		time1 = new Time(0, 1337);
		time2 = new Time(0, 1337);
		assertEquals(0, time1.compareTo(time2));

		time1 = new Time(123);
		time2 = new Time(123);
		assertEquals(0, time1.compareTo(time2));
	}

	@Test
	public void testCompareTo()
	{
		Time time1 = new Time(42, 1337);
		Time time2 = new Time(41, 1338);
		assertEquals(1, time1.compareTo(time2));

		time1 = new Time(0, 1337);
		time2 = new Time(0, 1336);
		assertEquals(1, time1.compareTo(time2));
	}

	@Test
	public void testHashCode()
	{
		final Time time1 = new Time(42, 1337);
		final Time time2 = new Time(42, 1337);
		assertEquals(time1.hashCode(), time2.hashCode());
		assertEquals(1379, time1.hashCode());
	}

	@Test
	public void testEquals()
	{
		final Time time = new Time(20, 1000);
		assertFalse(time.equals(null));
		assertFalse(time.equals(new Integer(20)));
		assertFalse(time.equals(new Time(20, 1)));
		assertFalse(time.equals(new Time(5, 1000)));
		assertTrue(time.equals(new Time(20, 1000)));
	}

}
