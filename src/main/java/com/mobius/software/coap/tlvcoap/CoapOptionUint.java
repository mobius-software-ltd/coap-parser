package com.mobius.software.coap.tlvcoap;

public class CoapOptionUint extends CoapOptionValue
{
	public CoapOptionUint(int type, int length, byte[] value)
	{
		super(type, length, value);
	}

	@Override
	public byte[] getValue()
	{
		return value;
	}

	public int getIntegerValue()
	{
		// TODO convert byte array to int
		return 0;
	}

}
