package com.mobius.software.coap.test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.mobius.software.coap.headercoap.CoapMessage;
import com.mobius.software.coap.parserimpl.CoapParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ServerTest
{
	@Test
	public void testParser()
	{
		try
		{
			String[] files = StaticData.getResourcesList("coap", true);
			List<String> filesList = Arrays.asList(files);
			Collections.sort(filesList);
			for (String file : filesList)
			{
				System.out.println("parsing " + file);
				byte[] arr = StaticData.hexStringToByteArray(file);
				ByteBuf buf = Unpooled.copiedBuffer(arr);
				CoapMessage message = CoapParser.decode(buf);
				CoapParser.encode(message);
				System.out.println(message);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			fail();
		}
	}

}