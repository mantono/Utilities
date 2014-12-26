package com.mantono.www;

public class InvalidEmailException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	  public InvalidEmailException() { super(); }
	  public InvalidEmailException(String message) { super(message); }
	  public InvalidEmailException(String message, Throwable cause) { super(message, cause); }
	  public InvalidEmailException(Throwable cause) { super(cause); }
}
