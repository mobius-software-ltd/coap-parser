package com.mobius.software.coap.parser.message.options;

public class CoapOptionOpaque extends CoapOptionValue
{
	public CoapOptionOpaque(int type, int length, byte[] value)
	{
		super(type, length, value);
	}
}
