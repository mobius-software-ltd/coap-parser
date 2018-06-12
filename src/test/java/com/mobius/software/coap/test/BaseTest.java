package com.mobius.software.coap.test;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.mobius.software.coap.parser.CoapParser;
import com.mobius.software.coap.parser.message.options.CoapOption;
import com.mobius.software.coap.parser.tlv.CoapCode;
import com.mobius.software.coap.parser.tlv.CoapMessage;
import com.mobius.software.coap.parser.tlv.CoapType;

import io.netty.buffer.ByteBuf;

@RunWith(Parameterized.class)
public abstract class BaseTest
{
	@Parameterized.Parameters
	public static Object[][] data()
	{
		//return new Object[1][0];
		return new Object[Testsuite.REPEAT_COUNT][0];
	}

	protected void checkEncodeDecode(CoapMessage message)
	{
		ByteBuf buf = CoapParser.encode(message);
		CoapMessage actual = CoapParser.decode(buf);
		assertEquals(message, actual);
	}

	protected String generateStringWithLength(int length)
	{
		String chars = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		for (int i = 0; i < length; i++)
			sb.append(chars.charAt(r.nextInt(chars.length())));
		return sb.toString();
	}

	protected CoapMessage generateGenericMessage()
	{
		return CoapMessage.builder()//
				.code(CoapCode.GET)//
				.type(CoapType.NON_CONFIRMABLE)//
				.messageID(1)//
				.payload("payload".getBytes())//
				.token("12345678".getBytes())//
				.option(new CoapOption(1, 5, "value".getBytes()))//
				.build();
	}
}
