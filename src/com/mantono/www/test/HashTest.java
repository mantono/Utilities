package com.mantono.www.test;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mantono.www.Hash;

public class HashTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@Test
	public void testHashCodeConstructorFromString() throws NoSuchAlgorithmException
	{
		Hash strHash = new Hash("7d9b13fcb8dc6d931bb9d33c134b79d0db697ed2673b874bbd010dab2d139cd4b900556f67622812538a171bc7768dabc1eb39843803850c9fb0fbfb575aa4cc");
		Hash byteHash = new Hash("test", "#d0g:7ih]Wp@4NrZ");
		assertEquals(strHash, byteHash);
	}
	
	@Test
	public void testHashCode() throws NoSuchAlgorithmException
	{
		Hash output = new Hash("test", "someSalt", "SHA-256");
		assertEquals(386, output.hashCode());
	}

	@Test
	public void testBytes()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testAsHexString() throws NoSuchAlgorithmException
	{
		String expectedOutput = "fa6a2185b3e0a9a85ef41ffb67ef3c1fb6f74980f8ebf970e4e72e353ed9537d593083c201dfd6e43e1c8a7aac2bc8dbb119c7dfb7d4b8f131111395bd70e97f";
		Hash result = new Hash("password", "salt", "SHA-512");
		assertEquals(expectedOutput, result.asHexString());
	}

	@Test
	public void testEqualsObject() throws NoSuchAlgorithmException
	{
		Hash result = new Hash("password", "salt", "SHA-512");
		Hash oracle = null;
		oracle = new Hash("fa6a2185b3e0a9a85ef41ffb67ef3c1fb6f74980f8ebf970e4e72e353ed9537d593083c201dfd6e43e1c8a7aac2bc8dbb119c7dfb7d4b8f131111395bd70e97f");
		assertEquals(oracle, result);
	}
}
