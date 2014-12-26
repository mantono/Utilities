package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.mantono.www.Database;

public class DatabaseTest
{
	Database dbConnection;
	
	@Before
	public void testSetup() throws SQLException
	{
		dbConnection = new Database("jdbc:mysql://192.168.0.101:3306", "168654-taskmate", "lighttpd", "4fsf2win");
	}
	
	@Test
	public void testConnectAndGetMailHash() throws SQLException
	{
		assertEquals("dcbfadcedaed448642070be973529bcca7ec06368b8fba49388862ccb0c6be3d", dbConnection.query("SELECT mail FROM User WHERE id = '3'"));
	}

}

