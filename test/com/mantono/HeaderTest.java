package com.mantono;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HeaderTest
{
	private MockHeader header;
	
	@Before
	public void setup()
	{
		header = new MockHeader();
	}
	
	@Test
	public void checkForPathTest()
	{		
		String result = header.getPath("GET /forgot_password.html?asda=2edq&ddd=adsasdasd HTTP/1.1");
		assertEquals("forgot_password.html", result);
		
		result = header.getPath("POST /login?asda=2edq&ddd=adsasdasd HTTP/1.1");
		assertEquals("login", result);
		
		result = header.getPath("GET /blahablahalff HTTP/1.1");
		assertEquals("blahablahalff", result);
		
		result = header.getPath("GET /blahablahalff HTTP");
		assertEquals(null, result);
	}
	
	@Test
	public void checkForGetDataTest()
	{
		assertEquals(2, header.checkForGetData("GET /forgot_password.html?asda=2edq&ddd=adsasdasd HTTP/1.1"));
		assertEquals(2, header.checkForGetData("POST /login?asda=2edq&ddd=adsasdasd HTTP/1.1"));
		assertEquals(2, header.checkForGetData("POST /login?asda=2edq&ddd==adsasdasd HTTP/1.1"));
		assertEquals(0, header.checkForGetData("GET /blahablahalff HTTP/1.1"));
		assertEquals(0, header.checkForGetData("GET /blahablahalff?jaj=true HTTP"));
		assertEquals(0, header.checkForGetData("GET /blahablahalff?jajue HTTP/1.1"));
		assertEquals(2, header.checkForGetData("POST /login?asda=2edq&&ddd=adsasdasd HTTP/1.1"));
		assertEquals(2, header.checkForGetData("POST /login?asda=2edq&&&&ddd=adsasdasd HTTP/1.1"));
	}
	
	@Test
	public void checkForPostDataTest()
	{
		assertEquals(2, header.checkForPostData("username=sss&password=s%C3%B6%C3%A4%C3%A5"));
		assertEquals(3, header.checkForPostData("username=sss&password=asdasd&entill=jaajaja"));
		assertEquals(2, header.checkForPostData("x=1&y=2"));
	}

}
