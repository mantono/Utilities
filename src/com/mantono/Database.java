package com.mantono;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database
{
	private final Connection connection;
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static int openConnections = 0;
	public final static byte MAX_CONNECTIONS = 10;

	private Database(String url, String database, String username, String password) throws SQLException
	{
		try
		{
			Class.forName(DRIVER).newInstance();
		}
		catch(InstantiationException | IllegalAccessException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection = DriverManager.getConnection(url+database, username, password);
		openConnections++;
	}
	
	public static Database create(String url, String database, String username, String password) throws SQLException
	{
		if(openConnections < MAX_CONNECTIONS)
			return new Database(url, database, username, password);
		throw new IllegalStateException("Reached maximum amount of allowed connections ("+MAX_CONNECTIONS+"), close current database connections before opening new ones");
	}
	
	public void close() throws SQLException
	{
		connection.close();
		openConnections--;
	}
	
	public PreparedStatement prepareStatement(String query) throws SQLException
	{
		return connection.prepareStatement(query);
	}
	
	public static synchronized int getNumberOfOpenConnections()
	{
		return openConnections;
	}
	
	public static synchronized boolean hasFreeConnection()
	{
		return openConnections < MAX_CONNECTIONS;
	}
}

