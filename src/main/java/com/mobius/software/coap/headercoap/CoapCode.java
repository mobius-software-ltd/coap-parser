package com.mobius.software.coap.headercoap;

import java.util.HashMap;
import java.util.Map;

public enum CoapCode
{
	GET(1), POST(2), PUT(3), DELETE(4), CREATED(65), DELETED(66), VALID(67), //
	CHANGED(68), CONTENT(69), BAD_REQUEST(128), UNAUTHORIZED(129), //
	BAD_OPTION(130), FORBIDDEN(131), NOT_FOUND(132), METHOD_NOT_ALLOWED(133), //
	NOT_ACCEPTABLE(134), PRECONDITION_FAILED(140), REQUEST_ENTITY_TOO_LARGE(141), //
	UNSUPPORTED_CONTENT_FORMAT(143), INTERNAL_SERVER_ERROR(160), NOT_IMPLEMENTED(161), //
	BAD_GATEWAY(162), SERVICE_UNAWAILABLE(163), GATEWAY_TIMEOUT(164), //
	PROXYING_NOT_SUPPORTED(165);

	private final int value;

	private static Map<Integer, CoapCode> map = new HashMap<Integer, CoapCode>();
	static
	{
		for (CoapCode legEnum : CoapCode.values())
			map.put(legEnum.value, legEnum);
	}

	private CoapCode(final int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return this.value;
	}

	public static CoapCode valueOf(int value)
	{
		return map.get(value);
	}
}
