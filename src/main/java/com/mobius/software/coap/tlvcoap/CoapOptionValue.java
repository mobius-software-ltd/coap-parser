package com.mobius.software.coap.tlvcoap;

public abstract class CoapOptionValue
{
	protected CoapOptionType type;
	protected int length;
	protected byte[] value;

	public CoapOptionValue(int type, int length, byte[] value)
	{
		this.type = CoapOptionType.valueOf(type);
		this.length = length;
		this.value = value;
	}

	public CoapOptionType getType()
	{
		return type;
	}

	public int getLength()
	{
		return length;
	}

	public byte[] getValue()
	{
		return value;
	}

}
