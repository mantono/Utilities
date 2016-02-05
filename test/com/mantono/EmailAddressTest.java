package com.mantono;

import static org.junit.Assert.*;

import javax.mail.internet.AddressException;

import org.junit.Before;
import org.junit.Test;

public class EmailAddressTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void test() throws AddressException
	{
		new EmailAddress("anton.osterberg@gmail.com");
	}
	
	@Test(expected=InvalidEmailAddressException.class)
	public void testMissingDot() throws AddressException
	{
		new EmailAddress("prefix@suffixcom");
	}

}
