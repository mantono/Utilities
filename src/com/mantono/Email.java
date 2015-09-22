package com.mantono;

public class Email
{
	private final String prefix;
	private final String suffix;
	
	public Email(String email)
	{
		if(!(email.contains("@")))
			throw new InvalidEmailException("The e-mail address does not contain a @ smybol");
		String[] adressDivided = email.split("@");
		int indexSuffix = adressDivided.length - 1;
		suffix = adressDivided[indexSuffix];
		String tempPrefix = "";
		for(int i = 0; i < indexSuffix; i++)
		{
			tempPrefix += adressDivided[i];
			if(i+1 < indexSuffix)
				tempPrefix += "@";
		}
		prefix = tempPrefix;
	}
	
	public String getAddress()
	{
		return prefix + "@" + suffix;
	}
	
	@Override
	public String toString()
	{
		return getAddress();
	}
	
	public boolean isValidAddress()
	{
		final boolean doubleDotAfterAt = suffix.contains("..");
		final boolean multipleAts = prefix.contains("@");
		final boolean prefixUnEscapedCharacters = prefix.matches("[(),;:<>]");
		final boolean suffixUnEscapedCharacters = suffix.matches("[(),;:<>]");
		return !(doubleDotAfterAt || multipleAts || prefixUnEscapedCharacters || suffixUnEscapedCharacters);
	}
}
