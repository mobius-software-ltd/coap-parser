package com.mobius.software.coap.parser.message.options;

import java.nio.ByteBuffer;

public class OptionParser
{
	private static final byte[] emptyArray = "".getBytes();

	public static CoapOption encode(CoapOptionType type, Object value)
	{
		validateType(type, value.getClass());
		byte[] encoded = encodeWithType(type, value);
		return new CoapOption(type.getValue(), encoded.length, encoded);
	}

	private static byte[] encodeWithType(CoapOptionType type, Object value)
	{
		byte[] encoded = null;
		switch (type)
		{
		case URI_PORT:
		case CONTENT_FORMAT:
		case ACCEPT:
			if (value instanceof Byte)
				encoded = ByteBuffer.allocate(2).putShort((Byte) value).array();
			else if (value instanceof Short)
				encoded = ByteBuffer.allocate(2).putShort((Short) value).array();
			else if (value instanceof Integer)
				encoded = ByteBuffer.allocate(2).putShort(((Integer) value).shortValue()).array();
			else if (value instanceof Long)
				encoded = ByteBuffer.allocate(2).putShort(((Long) value).shortValue()).array();
			break;

		case MAX_AGE:
		case SIZE1:
		case OBSERVE:
			encoded = ByteBuffer.allocate(4).putInt((Integer) value).array();
			break;

		case NODE_ID:
		case IF_MATCH:
		case URI_HOST:
		case ETAG:
		case URI_PATH:
		case LOCATION_PATH:
		case URI_QUERY:
		case LOCATION_QUERY:
		case PROXY_SCHEME:
		case PROXY_URI:
			encoded = ((String) value).getBytes();
			break;

		case IF_NONE_MATCH:
			encoded = emptyArray;
			break;
		default:
			throw new IllegalArgumentException("unsupported coap option type:" + type + " and value " + value.getClass());
		}
		return encoded;
	}

	private static void validateType(CoapOptionType type, Class<?> clazz)
	{
		switch (type)
		{
		case URI_PORT:
		case ACCEPT:
		case CONTENT_FORMAT:
			if (!clazz.equals(Long.class) && !clazz.equals(long.class) && !clazz.equals(Integer.class) && !clazz.equals(int.class) && !clazz.equals(Short.class) && !clazz.equals(short.class) && !clazz.equals(Byte.class) && !clazz.equals(byte.class))
				throw new IllegalArgumentException(generateErrorClassMessage(type, "Short", clazz));
			break;

		case MAX_AGE:
		case SIZE1:
		case OBSERVE:
			if (!clazz.equals(Integer.class) && !clazz.equals(int.class))
				throw new IllegalArgumentException(generateErrorClassMessage(type, "Integer", clazz));
			break;

		case NODE_ID:
		case IF_MATCH:
		case URI_HOST:
		case ETAG:
		case URI_PATH:
		case LOCATION_PATH:
		case URI_QUERY:
		case LOCATION_QUERY:
		case PROXY_SCHEME:
		case PROXY_URI:
			if (!clazz.equals(String.class))
				throw new IllegalArgumentException(generateErrorClassMessage(type, "String", clazz));
			break;
		case IF_NONE_MATCH:
			if (!clazz.equals(String.class) && !clazz.equals(byte[].class))
				throw new IllegalArgumentException(generateErrorClassMessage(type, "byte array", clazz));
			break;
		default:
			throw new IllegalArgumentException("unsupported coap option type:" + type + " and value " + clazz);
		}
	}

	private static String generateErrorClassMessage(CoapOptionType type, String expectedType, Class<?> clazz)
	{
		return type + " option is expected to be of " + expectedType + " type, actual type " + clazz;
	}

	public static Object decode(CoapOptionType type, byte[] encoded)
	{
		validateLength(type, encoded.length);
		return decodeWithType(type, encoded);
	}

	private static Object decodeWithType(CoapOptionType type, byte[] encoded)
	{
		switch (type)
		{
		case URI_PORT:
		case ACCEPT:
		case CONTENT_FORMAT:
			return ByteBuffer.wrap(encoded).getShort();

		case MAX_AGE:
		case SIZE1:
		case OBSERVE:
			return ByteBuffer.wrap(encoded).getInt();

		case NODE_ID:
		case IF_MATCH:
		case URI_HOST:
		case ETAG:
		case URI_PATH:
		case LOCATION_PATH:
		case URI_QUERY:
		case LOCATION_QUERY:
		case PROXY_SCHEME:
		case PROXY_URI:
			return new String(encoded);

		case IF_NONE_MATCH:
			return emptyArray;

		default:
			throw new IllegalArgumentException("unsupported coap option type:" + type);
		}
	}

	private static void validateLength(CoapOptionType type, int length)
	{
		switch (type)
		{
		case URI_PORT:
		case ACCEPT:
		case CONTENT_FORMAT:
			if (length > 2)
				throw new IllegalArgumentException(generatedErrorLenghtMessage(type, "0-2", length));
			break;

		case MAX_AGE:
		case SIZE1:
		case OBSERVE:
			if (length > 4)
				throw new IllegalArgumentException(generatedErrorLenghtMessage(type, "0-4", length));
			break;

		case IF_MATCH:
			if (length > 8)
				throw new IllegalArgumentException(generatedErrorLenghtMessage(type, "0-8", length));
			break;

		case ETAG:
			if (length > 8)
				throw new IllegalArgumentException(generatedErrorLenghtMessage(type, "1-8", length));
			break;

		case NODE_ID:
		case URI_PATH:
		case LOCATION_PATH:
		case URI_QUERY:
		case LOCATION_QUERY:
			if (length > 255)
				throw new IllegalArgumentException(generatedErrorLenghtMessage(type, "0-255", length));
			break;

		case URI_HOST:
		case PROXY_SCHEME:
			if (length == 0 || length > 255)
				throw new IllegalArgumentException(generatedErrorLenghtMessage(type, "1-255", length));
			break;

		case PROXY_URI:
			if (length == 0 || length > 1034)
				throw new IllegalArgumentException(generatedErrorLenghtMessage(type, "1-1034", length));
			break;

		case IF_NONE_MATCH:
			if (length > 0)
				throw new IllegalArgumentException(generatedErrorLenghtMessage(type, "0", length));
			break;
		default:
			throw new IllegalArgumentException("unsupported coap option type:" + type);
		}
	}

	public static String generatedErrorLenghtMessage(CoapOptionType type, String expectedLength, int actualLength)
	{
		return type + " option type is expected to be of " + expectedLength + " length but is " + actualLength;
	}
}
