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

	public Duration(Date start, long timeSpan)
	{
		if(timeSpan < 0)
			throw new IllegalArgumentException("Timespan can not be negative! (" + timeSpan + ")");
		this.start = start;
		this.end = new Date(start.getTime() + timeSpan);
		this.timeSpan = timeSpan;
	}
	
	public Duration(long timeSpan, Date end)
	{
		if(timeSpan < 0)
			throw new IllegalArgumentException("Timespan can not be negative! (" + timeSpan + ")");
		this.end = end;
		this.start = new Date(end.getTime() - timeSpan);
		this.timeSpan = timeSpan;
	}

	public Date getStart()
	{
		return start;
	}

	public Date getEnd()
	{
		return end;
	}

	public long getTime()
	{
		return timeSpan;
	}

	public long getMinutes()
	{
		return timeSpan / 60;
	}

	public long getHours()
	{
		return timeSpan / Date.HOUR;
	}

	public int getDays()
	{
		return (int) (timeSpan / Date.DAY);
	}

	public boolean hasStarted()
	{
		return start.getDifference() >= 0;
	}

	public boolean hasEnded()
	{
		return end.getDifference() > 0;
	}

	public boolean isNow()
	{
		return hasStarted() && !hasEnded();
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
