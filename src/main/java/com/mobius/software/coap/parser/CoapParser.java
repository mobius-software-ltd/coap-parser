package com.mobius.software.coap.parser;

import com.mobius.software.coap.exception.CoapParsingException;
import com.mobius.software.coap.parser.message.options.CoapOption;
import com.mobius.software.coap.parser.tlv.CoapCode;
import com.mobius.software.coap.parser.tlv.CoapMessage;
import com.mobius.software.coap.parser.tlv.CoapMessage.Builder;
import com.mobius.software.coap.parser.tlv.CoapType;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class CoapParser
{
	public static CoapMessage decode(ByteBuf buf)
	{
		Builder builder = CoapMessage.builder();

		int firstByte = buf.readUnsignedByte();

		int version = firstByte >> 6;
		if (version != 1)
			throw new CoapParsingException("Invalid version:" + version);
		builder.version(version);

		int typeValue = (firstByte >> 4) & 3;
		builder.type(CoapType.valueOf(typeValue));

		int tokenLength = firstByte & 0xf;
		if (tokenLength > 8)
			throw new CoapParsingException("Invalid token length:" + tokenLength);

		int codeByte = buf.readUnsignedByte();
		int codeValue = (codeByte >> 5) * 100;
		codeValue += codeByte & 0x1F;
		CoapCode code = CoapCode.valueOf(codeValue);
		if (code == null)
			throw new CoapParsingException("Unsupported code value:" + codeValue);
		builder.code(code);

		builder.messageID(buf.readUnsignedShort());

		if (tokenLength > 0)
		{
			byte[] token = new byte[tokenLength];
			buf.readBytes(token, 0, tokenLength);
			builder.token(token);
		}

		int number = 0;

		while (buf.isReadable())
		{
			int nextByte = buf.readUnsignedByte();
			if (Integer.compare(nextByte, 0xFF) == 0)
				break;

			int delta = (nextByte >> 4) & 15;
			if (delta == 13)
				delta = buf.readByte() + 13;
			else if (delta == 14)
				delta = buf.readShort() + 269;
			else if (delta > 14)
				throw new CoapParsingException("Invalid option delta value:" + delta);

			number += delta;

			int optionLength = nextByte & 15;
			if (optionLength == 13)
				optionLength = buf.readByte() + 13;
			else if (optionLength == 14)
				optionLength = buf.readShort() + 269;
			else if (optionLength > 14)
				throw new CoapParsingException("Invalid option length value:" + optionLength);

			byte[] optionValue = new byte[optionLength];
			if (optionLength > 0)
				buf.readBytes(optionValue, 0, optionLength);

			builder.option(new CoapOption(number, optionLength, optionValue));
		}

		if (buf.isReadable())
		{
			byte[] payload = new byte[buf.readableBytes()];
			buf.readBytes(payload);
			builder.payload(payload);
		}

		return builder.build();
	}

	public static ByteBuf encode(CoapMessage message)
	{
		ByteBuf buf = Unpooled.buffer();

		byte firstByte = 0;

		firstByte += message.getVersion() << 6;

		firstByte += message.getType().getValue() << 4;

		if (message.getToken() != null)
			firstByte += message.getToken().length;

		buf.writeByte(firstByte);

		int codeMsb = (message.getCode().getValue() / 100);
		int codeLsb = (byte) (message.getCode().getValue() % 100);
		int codeByte = ((codeMsb << 5) + codeLsb);
		buf.writeByte(codeByte);

		buf.writeShort(message.getMessageID());

		if (message.getToken() != null)
			buf.writeBytes(message.getToken());

		int previousNumber = 0;
		for (CoapOption option : message.getOptions())
		{
			int delta = option.getNumber() - previousNumber;
			int nextByte = 0;

			Integer extendedDelta = null;
			if (delta < 13)
				nextByte += delta << 4;
			else
			{
				extendedDelta = delta;
				if (delta < 0xFF)
					nextByte = 13 << 4;
				else
					nextByte = 14 << 4;
			}

			Integer extendedLength = null;
			if (option.getLength() < 13)
				nextByte += option.getLength();
			else
			{
				extendedLength = option.getLength();
				if (option.getLength() < 0xFF)
					nextByte += 13;
				else
					nextByte += 14;
			}

			buf.writeByte(nextByte);
			if (extendedDelta != null)
			{
				if (extendedDelta < 0xFF)
					buf.writeByte(extendedDelta - 13);
				else
					buf.writeShort(extendedDelta - 269);
			}

			if (extendedLength != null)
			{
				if (extendedLength < 0xFF)
					buf.writeByte(extendedLength - 13);
				else
					buf.writeShort(extendedLength - 269);
			}

			buf.writeBytes(option.getValue());
			previousNumber = option.getNumber();
		}

		buf.writeByte((byte) 0xFF);

		if (message.getPayload() != null && message.getPayload().length > 0)
			buf.writeBytes(message.getPayload());

		return buf;
	}
}
