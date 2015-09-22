package com.mantono;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mantono.Luhn;

public class LuhnTest
{

	@Test
	public void testGenerateControlNumber()
	{
		assertEquals(3, Luhn.generateControlNumber("120 150 800 890 207 041 900 00"));
		assertEquals(7, Luhn.generateControlNumber(130_333_29));
		assertEquals(9, Luhn.generateControlNumber("890207-041"));
	}

	@Test
	public void testIsValidNumber()
	{
		assertTrue(Luhn.isValidNumber("120 150 800 890 207 041 900 003"));
		assertTrue(Luhn.isValidNumber(130_333_297));
		assertTrue(Luhn.isValidNumber("890207-0419"));
	}
	
	@Test
	public void testIsInvalidNumber()
	{
		assertFalse(Luhn.isValidNumber(130_333_296));
		assertFalse(Luhn.isValidNumber("890207-0319"));
	}

}
