package com.mantono.time;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DurationTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testGetStart()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetEnd()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetTime()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetMinutes()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetHours()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetDays()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testIsNow()
	{
		final long now = System.currentTimeMillis() / 1000;
		final Date tenMinutesAgo = new Date(now - 10*60);
		final Date tenMinutesInTheFuture = new Date(now + 10*60);
		final Duration currently = new Duration(tenMinutesAgo, tenMinutesInTheFuture);
		
		assertTrue(currently.hasStarted());
		assertFalse(currently.hasEnded());
		assertTrue(currently.isNow());
	}

	@Test
	public void testOverlap()
	{
		fail("Not yet implemented");
	}

}
