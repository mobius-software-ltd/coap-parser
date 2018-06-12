package com.mobius.software.coap.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.UUID;

import org.junit.Test;

import com.mobius.software.coap.parser.message.options.CoapOption;
import com.mobius.software.coap.parser.message.options.CoapOptionType;
import com.mobius.software.coap.parser.tlv.CoapCode;
import com.mobius.software.coap.parser.tlv.CoapMessage;
import com.mobius.software.coap.parser.tlv.CoapType;

public class PositiveTests extends BaseTest
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
					.payload(new byte[0])//
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
	public void testEmptyPayloadNull()
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
	public void testExtendedOptionShortDelta()
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
					.option(new CoapOption(15, 6, "value2".getBytes()))//
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
	public void testExtendedOptionLongDelta()
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
					.option(new CoapOption(258, 6, "value2".getBytes()))//
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
	public void testExtendedOptionShortLength()
	{
		try
		{
			String optionValue1 = generateStringWithLength(15);
			String optionValue2 = generateStringWithLength(15);
			CoapMessage message = CoapMessage.builder()//
					.code(CoapCode.BAD_GATEWAY)//
					.type(CoapType.CONFIRMABLE)//
					.messageID(1)//
					.payload("payload".getBytes())//
					.token("12345678".getBytes())//
					.option(new CoapOption(1, optionValue1.length(), optionValue1.getBytes()))//
					.option(new CoapOption(2, optionValue2.length(), optionValue2.getBytes()))//
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
	public void testExtendedOptionLongLength()
	{
		try
		{
			String optionValue1 = generateStringWithLength(257);
			String optionValue2 = generateStringWithLength(257);
			CoapMessage message = CoapMessage.builder()//
					.code(CoapCode.BAD_GATEWAY)//
					.type(CoapType.CONFIRMABLE)//
					.messageID(1)//
					.payload("payload".getBytes())//
					.token("12345678".getBytes())//
					.option(new CoapOption(1, optionValue1.length(), optionValue1.getBytes()))//
					.option(new CoapOption(2, optionValue2.length(), optionValue2.getBytes()))//
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
	public void testExtendedOptionDeltaAndLength()
	{
		try
		{
			String optionValue1 = generateStringWithLength(257);
			String optionValue2 = generateStringWithLength(257);
			CoapMessage message = CoapMessage.builder()//
					.code(CoapCode.BAD_GATEWAY)//
					.type(CoapType.CONFIRMABLE)//
					.messageID(1)//
					.payload("payload".getBytes())//
					.token("12345678".getBytes())//
					.option(new CoapOption(1, optionValue1.length(), optionValue1.getBytes()))//
					.option(new CoapOption(257, optionValue2.length(), optionValue2.getBytes()))//
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
	public void testComplex()
	{
		try
		{
			String optionValue1 = generateStringWithLength(257);
			String optionValue2 = generateStringWithLength(257);
			CoapMessage message = CoapMessage.builder()//
					.code(CoapCode.GET)//
					.type(CoapType.NON_CONFIRMABLE)//
					.messageID(1)//
					.payload("payload".getBytes())//
					.token("12345678".getBytes())//
					.option(new CoapOption(1, optionValue1.length(), optionValue1.getBytes()))//
					.option(new CoapOption(257, optionValue2.length(), optionValue2.getBytes()))//
					.option(new CoapOption(258, 5, "11111".getBytes()))//
					.option(new CoapOption(258, 6, "222222".getBytes()))//
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
	public void testOptionsParsing()
	{
		try
		{
			CoapMessage message = CoapMessage.builder()//
					.code(CoapCode.GET)//
					.type(CoapType.NON_CONFIRMABLE)//
					.messageID(1)//
					.payload("payload".getBytes())//
					.token("12345678".getBytes())//
					.option(CoapOptionType.URI_PORT, 1)//
					.option(CoapOptionType.CONTENT_FORMAT, 2)//
					.option(CoapOptionType.ACCEPT, 3)//
					.option(CoapOptionType.MAX_AGE, 4)//
					.option(CoapOptionType.SIZE1, 5)//
					.option(CoapOptionType.OBSERVE, 6)//
					.option(CoapOptionType.NODE_ID, UUID.randomUUID().toString())//
					.option(CoapOptionType.IF_MATCH, UUID.randomUUID().toString())//
					.option(CoapOptionType.URI_HOST, UUID.randomUUID().toString())//
					.option(CoapOptionType.ETAG, UUID.randomUUID().toString())//
					.option(CoapOptionType.URI_PATH, UUID.randomUUID().toString())//
					.option(CoapOptionType.LOCATION_PATH, UUID.randomUUID().toString())//
					.option(CoapOptionType.URI_QUERY, UUID.randomUUID().toString())//
					.option(CoapOptionType.LOCATION_QUERY, UUID.randomUUID().toString())//
					.option(CoapOptionType.PROXY_SCHEME, UUID.randomUUID().toString())//
					.option(CoapOptionType.PROXY_URI, UUID.randomUUID().toString())//
					.option(CoapOptionType.IF_NONE_MATCH, new byte[0])//
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
	public void testFetchOptionValues()
	{
		try
		{
			Integer uriPort = 1;
			Integer contentFormat = 2;
			Integer accept = 3;
			Integer maxAge = 4;
			Integer size1 = 5;
			Integer observe = 6;
			String nodeID = UUID.randomUUID().toString();
			String ifMatch = "111111";
			String uriHost = UUID.randomUUID().toString();
			String eTag = "222222";
			String uriPath = UUID.randomUUID().toString();
			String locationPath = UUID.randomUUID().toString();
			String uriQuery = UUID.randomUUID().toString();
			String locationQuery = UUID.randomUUID().toString();
			String proxyScheme = UUID.randomUUID().toString();
			String proxyUri = UUID.randomUUID().toString();
			CoapMessage message = CoapMessage.builder()//
					.code(CoapCode.GET)//
					.type(CoapType.NON_CONFIRMABLE)//
					.messageID(1)//
					.payload("payload".getBytes())//
					.token("12345678".getBytes())//
					.option(CoapOptionType.URI_PORT, uriPort)//
					.option(CoapOptionType.CONTENT_FORMAT, contentFormat)//
					.option(CoapOptionType.ACCEPT, accept)//
					.option(CoapOptionType.MAX_AGE, maxAge)//
					.option(CoapOptionType.SIZE1, size1)//
					.option(CoapOptionType.OBSERVE, observe)//
					.option(CoapOptionType.NODE_ID, nodeID)//
					.option(CoapOptionType.IF_MATCH, ifMatch)//
					.option(CoapOptionType.URI_HOST, uriHost)//
					.option(CoapOptionType.ETAG, eTag)//
					.option(CoapOptionType.URI_PATH, uriPath)//
					.option(CoapOptionType.LOCATION_PATH, locationPath)//
					.option(CoapOptionType.URI_QUERY, uriQuery)//
					.option(CoapOptionType.LOCATION_QUERY, locationQuery)//
					.option(CoapOptionType.PROXY_SCHEME, proxyScheme)//
					.option(CoapOptionType.PROXY_URI, proxyUri)//
					.option(CoapOptionType.IF_NONE_MATCH, new byte[0])//
					.build();

			assertEquals(accept, message.options().fetchAccept());
			assertEquals(contentFormat, message.options().fetchContentFormat());
			assertEquals(uriPort, message.options().fetchUriPort());
			assertEquals(maxAge, message.options().fetchMaxAge());
			assertEquals(size1, message.options().fetchSize1());
			assertEquals(observe, message.options().fetchObserve());
			assertEquals(nodeID, message.options().fetchNodeID());
			assertEquals(ifMatch, message.options().fetchIfMatch());
			assertEquals(uriHost, message.options().fetchUriHost());
			assertEquals(eTag, message.options().fetchEtag());
			assertEquals(uriPath, message.options().fetchUriPath());
			assertEquals(locationPath, message.options().fetchLocationPath());
			assertEquals(uriQuery, message.options().fetchUriQuery());
			assertEquals(locationQuery, message.options().fetchLocationQuery());
			assertEquals(proxyScheme, message.options().fetchProxyScheme());
			assertEquals(proxyUri, message.options().fetchProxyUri());
			assertTrue(message.options().fetchIfNoneMatch());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}
}
