package com.mantono.www;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.regex.Pattern;

public abstract class Header
{	
	private Socket connection;
	private String path;
	private HashMap<String, String> getMap = new HashMap<String, String>();
	private HashMap<String, String> postMap = new HashMap<String, String>();

	public void addSocket(Socket socket)
	{
		this.connection = socket;
	}
	
	protected void parseHeader() throws IOException
	{
		getMap.clear();
		postMap.clear();
		BufferedReader dataBuffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		while(dataBuffer.ready())
		{
			String currentLine = readLine(dataBuffer);
			path = getPath(currentLine);
			checkForGetData(currentLine);
			if(!dataBuffer.ready())
				checkForPostData(currentLine);

			System.out.println(currentLine);
		}
	}
	
	public String getPath(String line)
	{
		if(!line.contains("HTTP/1.1"))
			return null;
		
		int start = line.indexOf('/') +  1;
		int end = line.indexOf('?');
		if(end == -1)
		end = line.length()-9;
		
		return line.substring(start, end);
	}
	
	public int checkForGetData(String line)
	{
		if(!line.contains("HTTP/1.1"))
			return 0;
		if(line.indexOf('?') == -1 || line.indexOf('=') == -1)
			return 0;
		line = line.substring(line.indexOf('?') + 1, line.length()-8);
		line = line.replaceAll("&&*", "&");
		String[] pairs = line.split("[&]");
		for(String pair : pairs)
		{
			String[] combo = pair.split("[=]");
			if(combo.length == 2)
				getMap.put(combo[0], combo[1]);
		}
		
		return pairs.length;
	}
	
	public int checkForPostData(String line)
	{
		if(line.indexOf('=') == -1)
			return 0;
		String[] pairs = line.split("[&]");
		for(String pair : pairs)
		{
			String[] combo = pair.split("[=]");
			if(combo.length == 2)
				postMap.put(combo[0], combo[1]);
		}
		
		return pairs.length;
	}
	
	protected boolean hasPostData()
	{
		return !postMap.isEmpty();
	}
	
	protected boolean hasGetData()
	{
		return !getMap.isEmpty();
	}
	
	private String readLine(BufferedReader reader) throws IOException
	{
		StringBuilder line = new StringBuilder();
		while(reader.ready())
		{
			line.append((char) reader.read());
		}
		return line.toString();
	}
}
