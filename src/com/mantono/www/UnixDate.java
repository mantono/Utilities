package com.mantono.www;

public class UnixDate implements Comparable<UnixDate>
{
	public static final short HOUR = 3600;
	public static final int DAY = 86400;
	public static final int WEEK = 604800;
	private final long timestamp;
	
	public UnixDate()
	{
		this.timestamp = System.currentTimeMillis() / 1000L;
	}
	
	public UnixDate(long timestamp)
	{
		this.timestamp = timestamp;
	}
	
	public long getDifference()
	{
		long unixTime = System.currentTimeMillis() / 1000L;
		return (int) (unixTime - timestamp);
	}
	
	public long getTimestamp()
	{
		return timestamp;
	}
	
	public String getHumanReadable()
	{
		int days = (int)(getDifference() / DAY);
		int hours = (int)((getDifference() % DAY) / 3600);
		int minutes = Math.round((getDifference() % 3600) / 60);
		String time = "";
		if(days != 0)
		{
			time = days + " day";
			if(days > 1 || days < -1)
				time += "s";
		}
		else if(hours != 0 )
		{
			time = hours + " hour";
			if(hours > 1 || hours < -1)
				time += "s";
		}
		else if(minutes != 0 )
		{
			time = minutes + " minute";
			if(minutes > 1 || minutes < -1)
				time += "s";
		}
		else
			time = "0";

		return time;
	}

	@Override
	public int compareTo(UnixDate other)
	{
		if(this.timestamp < other.timestamp)
			return -1;
		else if(this.timestamp > other.timestamp)
			return 1;
		return 0;
	}
}
