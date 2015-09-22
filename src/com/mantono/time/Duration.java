package com.mantono.time;


public class Duration implements Comparable<Duration>
{
	private final Date start;
	private final Date end;
	private final long timeSpan;

	public Duration(Date start, Date end)
	{
		if(start.getTime() > end.getTime())
			throw new IllegalArgumentException("End time can not be after start time!");
		this.start = start;
		this.end = end;
		timeSpan = end.getTime() - start.getTime();
	}

	Date getStart()
	{
		return start;
	}

	Date getEnd()
	{
		return end;
	}

	long getTime()
	{
		return timeSpan;
	}
	
	long getMinutes()
	{
		return timeSpan / 60;
	}
	
	long getHours()
	{
		return timeSpan / Date.HOUR;
	}
	
	int getDays()
	{
		return (int) (timeSpan / Date.DAY);
	}
	

	@Override
	public int compareTo(Duration other)
	{
		final int diff = (int) (start.getTime() - other.start.getTime());
		if(diff == 0)
			return (int) (end.getTime() - other.end.getTime());
		return diff;
	}
	
	@Override
	public String toString()
	{
		return Time.humanReadable(timeSpan);
	}
	
	public boolean overlap(Duration other)
	{
		if(this.start.compareTo(other.end) > 0)
			return false;
		if(this.end.compareTo(other.start) < 0)
			return false;
		return true;
	}
}
