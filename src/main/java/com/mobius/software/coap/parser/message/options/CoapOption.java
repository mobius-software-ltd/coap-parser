package com.mobius.software.coap.parser.message.options;

import java.util.Arrays;

public class CoapOption
{
	private int number;
	private int length;
	private byte[] value;

	public CoapOption(int number, int length, byte[] value)
	{
		this.number = number;
		this.length = length;
		this.value = value;
	}

	@Override
	public String toString()
	{
		return "CoapOption [number=" + number + ", length=" + length + ", value=" + Arrays.toString(value) + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + length;
		result = prime * result + number;
		result = prime * result + Arrays.hashCode(value);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoapOption other = (CoapOption) obj;
		if (length != other.length)
			return false;
		if (number != other.number)
			return false;
		if (!Arrays.equals(value, other.value))
			return false;
		return true;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public byte[] getValue()
	{
		return value;
	}

	public void setValue(byte[] value)
	{
		this.value = value;
	}

}
