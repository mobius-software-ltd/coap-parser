package com.mobius.software.coap.tlvcoap;

import java.util.HashMap;
import java.util.Map;

public enum CoapOptionType
{
	IF_MATCH(1), URI_HOST(3), ETAG(4), IF_NONE_MATCH(5), URI_PORT(7), LOCATION_PATH(8), URI_PATH(11), CONTENT_FORMAT(12), MAX_AGE(14), URI_QUERY(15), ACCEPT(17), LOCATION_QUERY(20), PROXY_URI(35), PROXY_SCHEME(39), SIZE1(60);

	private final int value;

	private static Map<Integer, CoapOptionType> map = new HashMap<Integer, CoapOptionType>();
	static
	{
		for (CoapOptionType item : CoapOptionType.values())
			map.put(item.value, item);
	}

	private CoapOptionType(final int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}

	public static CoapOptionType valueOf(int value)
	{
		return map.get(value);
	}
}
