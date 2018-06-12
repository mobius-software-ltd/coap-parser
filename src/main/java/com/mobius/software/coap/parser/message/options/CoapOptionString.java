package com.mobius.software.coap.parser.message.options;

public class CoapOptionString extends CoapOptionValue
{
	public CoapOptionString(int type, int length, byte[] value)
	{
		super(type, length, value);
	}

	public String getStringValue()
	{
		return new String(value);
	}
}
