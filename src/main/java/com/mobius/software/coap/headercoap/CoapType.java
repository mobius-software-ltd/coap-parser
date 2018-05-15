package com.mobius.software.coap.headercoap;

import java.util.HashMap;
import java.util.Map;

public enum CoapType
{
	CONFIRMABLE(0), NON_CONFIRMABLE(1), ACKNOWLEDGEMENT(2), RESET(3);

	private final int value;

	private static Map<Integer, CoapType> map = new HashMap<Integer, CoapType>();
	static
	{
		for (CoapType item : CoapType.values())
			map.put(item.value, item);
	}

	private CoapType(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return this.value;
	}

	public static CoapType valueOf(int value)
	{
		return map.get(value);
	}
}
