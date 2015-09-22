package com.mantono;

public class Luhn
{
	public static byte generateControlNumber(long input)
	{
		return generateControlNumber(Long.toString(input));
	}
	
	public static byte generateControlNumber(String input)
	{
		CharSequence sum = controlCount(input, false);
		return (byte) (10 - getSum(sum) % 10);
	}
	
	public static boolean isValidNumber(long input)
	{
		return isValidNumber(Long.toString(input));
	}
	
	public static boolean isValidNumber(String input)
	{
		CharSequence stringSum = controlCount(input, true);
		return getSum(stringSum) % 10 == 0;
	}
	
	private static CharSequence controlCount(String input, boolean containsControlNumber)
	{
		StringBuilder stringSum = new StringBuilder();
		
		int multiplier = 1;
		if(!containsControlNumber)
			multiplier++;
		
		for(int i = input.length() -1; i >= 0; i--)
		{
			final char nextChar = input.charAt(i);
			if(Character.isDigit(nextChar))
				stringSum.append(getNextDigit(nextChar, ++multiplier));
		}
		return stringSum;
	}
	
	private static int getNextDigit(final char digit, final int multiplier)
	{
		return Byte.parseByte(Character.toString(digit))*(1 + multiplier % 2);
	}
	
	public static int getSum(CharSequence input)
	{
		int sum = 0;
		for(int i = 0; i < input.length(); i++)
			sum += Byte.parseByte(Character.toString(input.charAt(i)));
		return sum;
	}
}
