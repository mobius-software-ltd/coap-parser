package com.mobius.software.coap.parser.message.options;

import java.nio.ByteBuffer;

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

	public Long getIntegerValue()
	{
		Integer localValue=ByteBuffer.wrap(value).getInt();
		return localValue.longValue() & 0x00FFFFFFFF;
	}

}
