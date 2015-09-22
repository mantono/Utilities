package com.mantono.security;

import java.security.SecureRandom;

import com.mantono.time.Date;

public class Delayer implements Runnable
{
	private static final int ONEMINUTE = 60000;
	private Date timeForLastLoginAttempt = new Date();
	private final SecureRandom randomNumber = new SecureRandom();
	private long loginAttempts = 0;
	private boolean continueDecay = true;
	
	@Override
	public void run()
	{
		while(continueDecay)
		{
			final long timeSinceLastLoginAttempt = timeForLastLoginAttempt.getDifference(); 
			if(timeSinceLastLoginAttempt > Date.DAY)
				loginAttempts = 0;
			else if(timeSinceLastLoginAttempt > 2*Date.HOUR)
				loginAttempts *= 0.9;
			else if(timeSinceLastLoginAttempt > 60)
				loginAttempts -= 1;
			if(loginAttempts < 0)
				loginAttempts = 0;
			try
			{
				Thread.sleep(ONEMINUTE);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void kill()
	{
		continueDecay = false;
	}
	
	protected long getLoginAttempts()
	{
		return loginAttempts;
	}
	
	public long waitRandomTime(int factor)
	{
		try
		{
			final long timeToSleep = randomNumber.nextInt(200) + factor*loginAttempts;
			Thread.sleep(timeToSleep);
			return timeToSleep;
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	
	public long newLoginAttempt()
	{
		timeForLastLoginAttempt = new Date();
		return ++loginAttempts;
	}
}
