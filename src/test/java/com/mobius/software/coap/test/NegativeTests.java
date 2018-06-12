package com.mobius.software.coap.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.mobius.software.coap.exception.CoapParsingException;
import com.mobius.software.coap.parser.CoapParser;
import com.mobius.software.coap.parser.tlv.CoapMessage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NegativeTests extends BaseTest
{
	@Test
	public void testInvalidVersion()
	{
		CoapMessage message = generateGenericMessage();
		message.setVersion(2);

		ByteBuf buf = CoapParser.encode(message);

		try
		{
			CoapParser.decode(buf);
			fail();
		}
		catch (CoapParsingException e)
		{
			assertEquals("Invalid version:2", e.getMessage());
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testInvalidTokenLength()
	{
		CoapMessage message = generateGenericMessage();

		ByteBuf orig = CoapParser.encode(message);
		byte firstByte = orig.readByte();
		firstByte += 1;
		ByteBuf buf = Unpooled.buffer();
		buf.writeByte(firstByte);
		buf.writeBytes(orig);

		try
		{
			CoapParser.decode(buf);
			fail();
		}
		catch (CoapParsingException e)
		{
			assertEquals("Invalid token length:9", e.getMessage());
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testInvalidCode()
	{
		CoapMessage message = generateGenericMessage();

		ByteBuf orig = CoapParser.encode(message);
		byte firstByte = orig.readByte();
		ByteBuf buf = Unpooled.buffer();
		buf.writeByte(firstByte);
		buf.writeByte(5);
		buf.writeBytes(orig);

		try
		{
			CoapParser.decode(buf);
			fail();
		}
		catch (CoapParsingException e)
		{
			assertEquals("Unsupported code value:5", e.getMessage());
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testInvalidOptionDelta()
	{
		CoapMessage message = generateGenericMessage();

		ByteBuf orig = CoapParser.encode(message);
		byte[] headBytes = new byte[4 + message.getToken().length];
		orig.readBytes(headBytes);
		orig.readByte();
		int optionFirstByte = (15 << 4) + message.getOptions().get(0).getLength();
		ByteBuf buf = Unpooled.buffer();
		buf.writeBytes(headBytes);
		buf.writeByte(optionFirstByte);
		buf.writeBytes(orig);

		try
		{
			CoapParser.decode(buf);
			fail();
		}
		catch (CoapParsingException e)
		{
			assertEquals("Invalid option delta value:15", e.getMessage());
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testInvalidOptionLength()
	{
		CoapMessage message = generateGenericMessage();

		ByteBuf orig = CoapParser.encode(message);
		byte[] headBytes = new byte[4 + message.getToken().length];
		orig.readBytes(headBytes);
		orig.readByte();
		int optionFirstByte = 15;
		ByteBuf buf = Unpooled.buffer();
		buf.writeBytes(headBytes);
		buf.writeByte(optionFirstByte);
		buf.writeBytes(orig);

		try
		{
			CoapParser.decode(buf);
			fail();
		}
		catch (CoapParsingException e)
		{
			assertEquals("Invalid option length value:15", e.getMessage());
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			fail();
		}
	}
}
