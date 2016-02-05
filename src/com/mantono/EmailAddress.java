package com.mantono;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * This class in a representation of an e-mail address. Using this class
 * instead of a plain {@link String} for storing the e-mail address
 * delivers some extra syntax checking of the address, but offers no extra
 * functionality beyond that.
 * 
 * @author Anton &Ouml;sterberg
 *
 */
public class EmailAddress extends InternetAddress
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8135650626319703293L;
	private final String prefix;
	private final String suffix;

	/**
	 * @param email the e-mail address as a {@link String}.
	 * @throws AddressException if the given address was considered
	 * invalid in some way.
	 */
	public EmailAddress(String email) throws AddressException
	{
		super(email, true);
		if(!(email.contains("@")))
			throw new InvalidEmailAddressException("The e-mail address does not contain a @ smybol");
		String[] adressDivided = email.split("@");
		int indexSuffix = adressDivided.length - 1;
		suffix = adressDivided[indexSuffix];
		String tempPrefix = "";
		for(int i = 0; i < indexSuffix; i++)
		{
			tempPrefix += adressDivided[i];
			if(i + 1 < indexSuffix)
				tempPrefix += "@";
		}
		prefix = tempPrefix;
		if(!isValidAddress())
			throw new AddressException();
	}

	/**
	 * This method checks for some (but not all) of the ways an e-mail
	 * address can be badly formatted.
	 * @return false if the e-mail address was not illegally formatted
	 * in some way, else true.
	 * @throws AddressException if {@link InternetAddress#validate()}
	 * found some errors in the address syntax.
	 */
	private boolean isValidAddress() throws AddressException
	{
		validate();
		final boolean doubleDotAfterAt = suffix.contains("..");
		final boolean singleDot = suffix.contains(".");
		final boolean multipleAts = prefix.contains("@");
		final boolean prefixUnEscapedCharacters = prefix.matches("[(),;:<>]");
		final boolean suffixUnEscapedCharacters = suffix.matches("[(),;:<>]");
		return !(doubleDotAfterAt || multipleAts || prefixUnEscapedCharacters || suffixUnEscapedCharacters || !singleDot);
	}

	@Override
	public String toString()
	{
		return prefix + "@" + suffix;
	}
}
