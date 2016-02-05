package com.mantono;

public class InvalidEmailAddressException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	  public InvalidEmailAddressException() { super(); }
	  public InvalidEmailAddressException(String message) { super(message); }
	  public InvalidEmailAddressException(String message, Throwable cause) { super(message, cause); }
	  public InvalidEmailAddressException(Throwable cause) { super(cause); }
}
