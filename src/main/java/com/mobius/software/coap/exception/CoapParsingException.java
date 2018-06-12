package com.mobius.software.coap.exception;

public class CoapParsingException extends RuntimeException
{
	private static final long serialVersionUID = -9023378185516485331L;

	public CoapParsingException(String message)
	{
		super(message);
	}

	public CoapParsingException(Exception e)
	{
		super(e);
	}

	public static CoapParsingException invalidVersion(int version)
	{
		return new CoapParsingException("Invalid version:" + version);
	}
}
