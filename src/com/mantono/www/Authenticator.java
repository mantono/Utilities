package com.mantono.www;

import gymconnector.DatabaseHandle;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authenticator
{
	private final Database database;
	private final Delayer delay = new Delayer();
	
	public Authenticator() throws SQLException
	{
		this.database = new DatabaseHandle();
		Thread delayThread = new Thread(delay);
		delayThread.start();
	}
	
	public boolean permitLogin(String username, String password) throws NoSuchAlgorithmException
	{
		delay.newLoginAttempt();
		Hash fromDatabase = getHashInDatabaseForUser(username);
		String salt = getSaltInDatabaseForUser(username);
		Hash fromInput = new Hash(password, salt, "SHA-512");
		delay.waitRandomTime(100);
		System.out.println(delay.getLoginAttempts());
		return isValid(fromDatabase, fromInput);
	}

	private boolean isValid(Hash fromDatabase, Hash fromInput)
	{
		return fromDatabase != null && fromDatabase.equals(fromInput);
	}

	private String getSaltInDatabaseForUser(String username)
	{
		try
		{
			PreparedStatement statement = database.prepareStatement("SELECT salt FROM User WHERE username = ?");
			statement.setString(1, username);
			ResultSet result = statement.executeQuery();
			if(!result.isBeforeFirst())
				return null;
			result.first();
			return result.getString("salt");
		}
		catch(SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Hash getHashInDatabaseForUser(String username)
	{
		try
		{
			PreparedStatement statement = database.prepareStatement("SELECT password FROM User WHERE username = ?");
			statement.setString(1, username);
			ResultSet result = statement.executeQuery();
			if(!result.isBeforeFirst())
				return null;
			result.first();
			return new Hash(result.getString("password"));
		}
		catch(SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
