package com.mantono.www;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager
{
	private String username;
	private final Database database;
	private static final char seedSource[] = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM!@#%/=-_$[]+;:<>,.0123456789".toCharArray();
	private final SecureRandom random = new SecureRandom();
	
	public AccountManager(String username, Database database) throws SQLException
	{
		this.username = username;
		this.database = database;
	}
	
	public boolean createAccount(String password, Email mail) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		if(!mail.isValidAddress())
			throw new InvalidEmailException("The submitted e-mail address contains errors");
		if(userExists())
			throw new IllegalArgumentException("User " + username + " already exists");
		Hash email = new Hash(mail.getAddress(), "", "SHA-256");
		if(emailAddressIsAlreadyUsed(email))
			throw new InvalidEmailException("The submitted e-mail address is already in use");
		String salt = generateRandomSalt();
		Hash hashedPassword = new Hash(password, salt, "SHA-512");
		return insertIntoDatabase(salt, hashedPassword, email);
	}

	public boolean emailAddressIsAlreadyUsed(Hash emailHash)
	{
		try
		{
			PreparedStatement statement = database.prepareStatement("SELECT COUNT(DISTINCT mail) FROM User WHERE mail = ?");
			statement.setString(1, emailHash.asHexString());
			ResultSet result = statement.executeQuery();
			result.first();
			return result.getInt(1) != 0;
		}
		catch(SQLException exception)
		{
			exception.printStackTrace();
		}
		return true;
	}

	private boolean insertIntoDatabase(String salt, Hash hash, Hash email)
	{
		System.out.println(hash);
		return false;
	}

	public boolean userExists()
	{
		try
		{
			PreparedStatement statement = database.prepareStatement("SELECT COUNT(DISTINCT username) FROM User WHERE username = ?");
			statement.setString(1, username);
			ResultSet result = statement.executeQuery();
			result.first();
			return result.getInt(1) != 0;
		}
		catch(SQLException exception)
		{
			exception.printStackTrace();
		}
		return true;
	}

	private String generateRandomSalt(int length)
	{
		StringBuilder salt = new StringBuilder();
		for(int i = 0; i < length; i++)
			salt.append(seedSource[random.nextInt()]);
		return salt.toString();
	}
	
	private String generateRandomSalt()
	{
		return generateRandomSalt(32);
	}
}
