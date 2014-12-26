package com.mantono.www;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database
{
	private final String driver = "com.mysql.jdbc.Driver";
	private final Connection connection;

	public Database(String url, String database, String username, String password) throws SQLException
	{
		try
		{
			Class.forName(driver).newInstance();
		}
		catch(InstantiationException | IllegalAccessException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection = DriverManager.getConnection(url+database, username, password);
	}
	
	public void close() throws SQLException
	{
		connection.close();
	}
	
	public PreparedStatement prepareStatement(String query) throws SQLException
	{
		return connection.prepareStatement(query);
	}
}

