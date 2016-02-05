package com.mantono.security;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.mail.internet.AddressException;

import com.mantono.Database;
import com.mantono.EmailAddress;
import com.mantono.InvalidEmailAddressException;

public class AccountManager
{
	private String username;
	private final Database database;
	private final SecureRandom random = new SecureRandom();
	private static final char seedSource[] = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789!@#%/=-_$[]+;:<>,.".toCharArray();
	private static final byte sourceSize = (byte) seedSource.length;
	private final static String userDatabase = "User";
	
	public AccountManager(String username, Database database) throws SQLException
	{
		this.username = username;
		this.database = database;
	}
	
	public boolean createAccount(String password, String repeatedPassword, EmailAddress mail) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException, AddressException
	{
		if(!mail.isValidAddress())
			throw new InvalidEmailAddressException("The submitted e-mail address contains errors");
		if(!password.equals(repeatedPassword))
			throw new IllegalArgumentException("Password did not match.");
		if(userExists())
			throw new IllegalArgumentException("User " + username + " already exists");
		Hash email = new Hash(mail.getAddress(), "", "SHA-256");
		if(emailAddressIsAlreadyUsed(email))
			throw new InvalidEmailAddressException("The submitted e-mail address is already in use");
		final String salt = generateRandomSalt();
		final String token = generateRandomToken(); 
		final Hash hashedPassword = new Hash(password, salt, "SHA-512");
		return insertIntoDatabase(salt, token, hashedPassword, email);
	}

	public boolean emailAddressIsAlreadyUsed(Hash emailHash)
	{
		try
		{
			PreparedStatement statement = database.prepareStatement("SELECT COUNT(DISTINCT mail) FROM " + userDatabase + " WHERE mail = ?");
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

	private boolean insertIntoDatabase(String salt, String token, Hash hash, Hash email) throws SQLException
	{
		System.out.println(hash.asHexString());
		System.out.println(email.asHexString());
		final String query = "INSERT INTO `" + userDatabase + "` (`username`, `salt`, `token`, `password`, `mail`) VALUES (?, ?, ?, ?, ?);";
		PreparedStatement statement = database.prepareStatement(query);
		statement.setString(1, username);
		statement.setString(2, salt);
		statement.setString(3, token);
		statement.setString(4, hash.asHexString());
		statement.setString(5, email.asHexString());
		return statement.executeUpdate() == 1;
	}

	public boolean userExists()
	{
		try
		{
			PreparedStatement statement = database.prepareStatement("SELECT COUNT(DISTINCT username) FROM " + userDatabase + " WHERE username = ?");
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

	private String generateRandomSalt(int length, int sourceLength)
	{
		StringBuilder salt = new StringBuilder();
		for(int i = 0; i < length; i++)
			salt.append(seedSource[random.nextInt(sourceLength)]);
		return salt.toString();
	}
	
	private String generateRandomToken()
	{
		return generateRandomSalt(32, sourceSize-18);
	}
	
	private String generateRandomSalt()
	{
		return generateRandomSalt(32, sourceSize);
	}
}
