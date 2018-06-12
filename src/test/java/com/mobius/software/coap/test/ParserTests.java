package com.mobius.software.coap.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.mobius.software.coap.parser.CoapParser;
import com.mobius.software.coap.parser.message.options.CoapOption;
import com.mobius.software.coap.parser.message.options.CoapOptionType;
import com.mobius.software.coap.parser.tlv.CoapCode;
import com.mobius.software.coap.parser.tlv.CoapMessage;
import com.mobius.software.coap.parser.tlv.CoapType;

import io.netty.buffer.ByteBuf;

public class ParserTests
{
	@Test
	public void testTypes()
	{
		try
		{
			for (CoapType type : CoapType.values())
			{
				CoapMessage message = CoapMessage.builder()//
						.code(CoapCode.GET)//
						.type(type)//
						.messageID(1)//
						.payload("payload".getBytes())//
						.token("12345678".getBytes())//
						.option(new CoapOption(1, 5, "value".getBytes()))//
						.build();
				checkEncodeDecode(message);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testCodes()
	{
		try
		{
			for (CoapCode code : CoapCode.values())
			{
				CoapMessage message = CoapMessage.builder()//
						.code(code)//
						.type(CoapType.CONFIRMABLE)//
						.messageID(1)//
						.payload("payload".getBytes())//
						.token("12345678".getBytes())//
						.option(new CoapOption(1, 5, "value".getBytes()))//
						.build();
				checkEncodeDecode(message);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testEmptyToken()
	{
		try
		{
			CoapMessage message = CoapMessage.builder()//
					.code(CoapCode.BAD_GATEWAY)//
					.type(CoapType.CONFIRMABLE)//
					.messageID(1)//
					.payload("payload".getBytes())//
					.option(new CoapOption(1, 5, "value".getBytes()))//
					.build();
			checkEncodeDecode(message);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testEmptyOptions()
	{
		try
		{
			CoapMessage message = CoapMessage.builder()//
					.code(CoapCode.BAD_GATEWAY)//
					.type(CoapType.CONFIRMABLE)//
					.messageID(1)//
					.payload("payload".getBytes())//
					.token("12345678".getBytes())//
					.build();
			checkEncodeDecode(message);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testEmptyPayload()
	{
		try
		{
			CoapMessage message = CoapMessage.builder()//
					.code(CoapCode.BAD_GATEWAY)//
					.type(CoapType.CONFIRMABLE)//
					.messageID(1)//
					.token("12345678".getBytes())//
					.option(new CoapOption(1, 5, "value".getBytes()))//
					.build();
			checkEncodeDecode(message);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAllEmpty()
	{
		try
		{
			CoapMessage message = CoapMessage.builder()//
					.code(CoapCode.BAD_GATEWAY)//
					.type(CoapType.CONFIRMABLE)//
					.messageID(1)//
					.build();
			checkEncodeDecode(message);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testMultipleOptions()
	{
		try
		{
			CoapMessage message = CoapMessage.builder()//
					.code(CoapCode.BAD_GATEWAY)//
					.type(CoapType.CONFIRMABLE)//
					.messageID(1)//
					.payload("payload".getBytes())//
					.token("12345678".getBytes())//
					.option(new CoapOption(1, 6, "value1".getBytes()))//
					.option(new CoapOption(2, 6, "value2".getBytes()))//
					.build();
			checkEncodeDecode(message);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testMultipleOptionsOfSameNumber()
	{
		try
		{
			CoapMessage message = CoapMessage.builder()//
					.code(CoapCode.BAD_GATEWAY)//
					.type(CoapType.CONFIRMABLE)//
					.messageID(1)//
					.payload("payload".getBytes())//
					.token("12345678".getBytes())//
					.option(new CoapOption(1, 6, "value1".getBytes()))//
					.option(new CoapOption(1, 6, "value2".getBytes()))//
					.build();
			checkEncodeDecode(message);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testExtendedOptionSmallDelta()
	{
		try
		{
			CoapMessage message = CoapMessage.builder()//
					.code(CoapCode.BAD_GATEWAY)//
					.type(CoapType.CONFIRMABLE)//
					.messageID(1)//
					.payload("payload".getBytes())//
					.token("12345678".getBytes())//
					.option(new CoapOption(1, 6, "value1".getBytes()))//
					.option(new CoapOption(1, 6, "value2".getBytes()))//
					.build();
			checkEncodeDecode(message);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testExtendedOptionLength()
	{
		try
		{

		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testExtendedOptionDeltaAndLength()
	{
		try
		{

		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testComplex()
	{
		try
		{

		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testMessageBuilderOptionOrder()
	{
		try
		{
			CoapMessage message = CoapMessage.builder()//
					.code(CoapCode.BAD_GATEWAY)//
					.type(CoapType.CONFIRMABLE)//
					.messageID(1)//
					.payload("payload".getBytes())//
					.token("12345678".getBytes())//
					.option(CoapOptionType.NODE_ID, "node_id")//
					.option(CoapOptionType.ACCEPT, 10)//
					.build();

			assertEquals(CoapOptionType.ACCEPT.getValue(), message.getOptions().get(0).getNumber());
			assertEquals(CoapOptionType.NODE_ID.getValue(), message.getOptions().get(1).getNumber());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	private void checkEncodeDecode(CoapMessage message)
	{
		ByteBuf buf = CoapParser.encode(message);
		CoapMessage actual = CoapParser.decode(buf);
		//System.out.println("orig:   " + message);
		//System.out.println("decoded:" + actual);
		assertEquals(message, actual);
	}
}
