package com.mantono.time;

public class Time extends Number implements Comparable<Time>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7068436598663081129L;
	private final long seconds;
	private final int nanoseconds;
	private final static int ONE_BILLION = 1_000_000_000;

	public Time(long seconds, int nanoseconds)
	{
		if(seconds < 0 || nanoseconds < 0)
			throw new IllegalArgumentException("Time may not be negative.");
		
		while(nanoseconds > ONE_BILLION)
		{
			seconds += 1;
			nanoseconds -= ONE_BILLION;
		}
		
		if(seconds < 0 || nanoseconds < 0)
			throw new ArithmeticException("Overflow occured.");

		this.seconds = seconds;
		this.nanoseconds = nanoseconds;
	}

	public Time(final long seconds)
	{
		this(seconds, 0);
	}

	public Time(final Time time1, final Time time2)
	{
		this(Math.addExact(time1.seconds, time2.seconds), Math.addExact(time1.nanoseconds, time2.nanoseconds));
	}

	public long getDays()
	{
		return seconds / Date.DAY;
	}

	public long getHours()
	{
		return seconds / Date.HOUR;
	}

	public long getMinutes()
	{
		return seconds / 60;
	}

	public long getSeconds()
	{
		return seconds;
	}

	public int getNanoseconds()
	{
		return nanoseconds;
	}

	public int getModuloHours()
	{
		return (int) (seconds % Date.DAY) / Date.HOUR;
	}

	public long getModuloMinutes()
	{
		return (int) (seconds % Date.HOUR) / 60;
	}

	public long getModuloSeconds()
	{
		return (int) seconds % 60;
	}

	@Override
	public String toString()
	{
		String output = humanReadableDetailed(seconds);
		if(nanoseconds != 0 && addComma(output))
			output += ", " + nanoseconds + " nanoseconds";
		return output;
	}
	
	public static String humanReadable(final long seconds)
	{
		int days = (int) (seconds / Date.DAY);
		int hours = (int) ((seconds % Date.DAY) / 3600);
		int minutes = Math.round((seconds % 3600) / 60);
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
	
	public static String humanReadableDetailed(final long seconds)
	{
		final long[] times = new long[4];
		times[0] = seconds / Date.DAY;
		times[1] = (seconds % Date.DAY) / 3600;
		times[2] = (seconds % 3600) / 60;
		times[3] = seconds % 60;
		
		final String[] timeUnit = new String[4];
		timeUnit[0] = "day";
		timeUnit[1] = "hour";
		timeUnit[2] = "minute";
		timeUnit[3] = "second";
		
		final StringBuilder timeString = new StringBuilder();
		
		for(int i = 0; i < 4; i++)
		{
			if(times[i] != 0)
			{
				if(addComma(timeString))
					timeString.append(", ");
				timeString.append(times[i] + " " + timeUnit[i]);
				if(times[i] > 1)
					timeString.append("s");
			}
		}
		return timeString.toString();
	}
	
	private static boolean addComma(CharSequence timeString)
	{
		if(timeString.length() == 0)
			return false;
		if(timeString.subSequence(timeString.length()-2, timeString.length()).equals(", "))
			return false;
		return true;
	}

	@Override
	public int compareTo(Time other)
	{
		final int diff = (int) (this.seconds - other.seconds) % Integer.MAX_VALUE;
		return (diff != 0) ? diff : this.nanoseconds - other.nanoseconds;
	}

	@Override
	public double doubleValue()
	{
		return (double) (seconds % Double.MAX_VALUE) + (nanoseconds / ONE_BILLION);
	}

	@Override
	public float floatValue()
	{
		return (float) (seconds % Float.MAX_VALUE) + (nanoseconds / ONE_BILLION);
	}

	@Override
	public int intValue()
	{
		return (int) seconds % Integer.MAX_VALUE;
	}

	@Override
	public long longValue()
	{
		return seconds;
	}
	
	@Override
	public boolean equals(Object object)
	{
		if(object == null)
			return false;
		if(!object.getClass().equals(this.getClass()))
			return false;
	
		final Time otherTime = (Time) object;
		
		final boolean sameSeconds = this.seconds == otherTime.seconds;
		final boolean sameNanoseconds = this.nanoseconds == otherTime.nanoseconds;
		
		return sameSeconds && sameNanoseconds;
	}
	
	@Override
	public int hashCode()
	{
		return (int) (seconds + nanoseconds);
	}
}
