package com.mobius.software.coap.parserimpl;

import java.nio.ByteBuffer;

import com.mobius.software.coap.headercoap.CoapCode;
import com.mobius.software.coap.headercoap.CoapMessage;
import com.mobius.software.coap.headercoap.CoapType;
import com.mobius.software.coap.headercoap.CoapMessage.Builder;
import com.mobius.software.coap.tlvcoap.CoapOption;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class CoapParser
{
	public static CoapMessage decode(ByteBuf buf)
	{
		Builder builder = CoapMessage.builder();

		byte firstByte = buf.readByte();

		int version = firstByte >> 6;
		if (version != 1)
			throw new IllegalArgumentException("Invalid version:" + version);
		builder.version(version);

		int typeValue = (firstByte >> 4) & 2;
		CoapType type = CoapType.valueOf(typeValue);
		if (type == null)
			throw new IllegalArgumentException("Invalid type value:" + typeValue);
		builder.type(type);

		int tokenLength = firstByte & 0xf;
		if (tokenLength < 0 || tokenLength > 8)
			throw new IllegalArgumentException("Invalid token length:" + tokenLength);

		int codeValue = buf.readByte();
		CoapCode code = CoapCode.valueOf(codeValue);
		if (code == null)
			throw new IllegalArgumentException("Unsupported code value:" + codeValue);
		builder.code(code);

		int messageID = buf.readShort();
		if (messageID < 0 || messageID > 65535)
			throw new IllegalArgumentException("Invalid messageID value:" + messageID);
		builder.messageID(messageID);

		byte[] token = new byte[tokenLength];
		if (tokenLength > 0)
			buf.readBytes(token, 0, tokenLength);
		builder.token(token);

		int number = 0;
		while (buf.isReadable())
		{
			byte nextByte = buf.readByte();
			if (nextByte == 0xFF)
				break;

			int delta = ((nextByte >> 4) & 15);
			if (delta == 13)
				delta = (delta << 8 | buf.readByte()) - 13;
			else if (delta == 14)
				delta = (delta << 16 | buf.readByte() << 8 | buf.readByte()) - 269;
			else if (delta < 0 || delta > 14)
				throw new IllegalArgumentException("invalid option delta value:" + delta);

			number += delta;
			if (number < 0)
				throw new IllegalArgumentException("invalid negative option number:" + number + ", delta:" + delta);

			int optionLength = nextByte & 15;
			if (optionLength == 13)
				optionLength = (optionLength << 8 | buf.readByte()) - 13;
			else if (optionLength == 14)
				optionLength = (optionLength << 16 | buf.readByte() << 8 | buf.readByte()) - 269;
			else if (optionLength < 0 || optionLength > 14)
				throw new IllegalArgumentException("invalid option length");

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
		firstByte += (message.getType().getValue() << 4) & 2;
		firstByte |= message.getToken().length;
		buf.writeByte(firstByte);
		buf.writeByte(message.getCode().getValue());
		buf.writeShort(message.getMessageID());
		buf.writeBytes(message.getToken());
		
		Integer previousDelta = 0;
        for (CoapOption option : message.getOptions())
		{
        	int delta = option.getNumber() - previousDelta;
            int realDelta = delta;
            byte[] extraDeltaBytes = new byte[0];
            if (delta >= 269)
            {
                realDelta = 14;
                short remainingValue = (short)(delta - 269);                
                extraDeltaBytes = ByteBuffer.allocate(2).putShort(remainingValue).array();
            }
            else if (delta >= 13)
            {
                realDelta = 13;
                extraDeltaBytes = new byte[1];
                extraDeltaBytes[0] = (byte)(delta - 13);
            }

            int valueLength = option.getValue().length;
            int realLength = valueLength;
            byte[] extraLengthBytes = new byte[0];
            if (valueLength >= 269)
            {
                realLength = 14;
                short remainingValue = (short)(valueLength - 269);
                extraLengthBytes = ByteBuffer.allocate(2).putShort(remainingValue).array();
            }
            else if (valueLength >= 13)
            {
                realLength = 13;
                extraLengthBytes = new byte[1];
                extraLengthBytes[0] = (byte)(valueLength - 13);
            }

            buf.writeByte((byte)((realDelta << 4) | (realLength & 0x0F)));
            if (extraDeltaBytes.length > 0)
                buf.writeBytes(extraDeltaBytes);

            if (extraLengthBytes.length > 0)
                buf.writeBytes(extraLengthBytes);

            buf.writeBytes(option.getValue());
		}
		buf.writeByte(0xFF);
		if (message.getPayload() != null)
			buf.writeBytes(message.getPayload());

		return buf;
	}
}
