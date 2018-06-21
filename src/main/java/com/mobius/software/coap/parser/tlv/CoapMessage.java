package com.mobius.software.coap.parser.tlv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.mobius.software.coap.parser.message.options.CoapOption;
import com.mobius.software.coap.parser.message.options.CoapOptionType;
import com.mobius.software.coap.parser.message.options.CoapOptionsComparator;
import com.mobius.software.coap.parser.message.options.OptionParser;

public class CoapMessage
{
	private Integer version;
	private CoapType type;
	private CoapCode code;
	private Integer messageID;
	private byte[] token;
	private List<CoapOption> options;
	private byte[] payload;

	private CoapMessage(Integer version, CoapType type, CoapCode code, Integer messageID, byte[] token, List<CoapOption> options, byte[] payload)
	{
		this.version = version;
		this.type = type;
		this.code = code;
		this.messageID = messageID;
		this.token = token;
		this.options = options;
		this.payload = payload;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public Options options()
	{
		return new Options();
	}

	@Override
	public String toString()
	{
		return "CoapMessage [version=" + version + ", type=" + type + ", code=" + code + ", messageID=" + messageID + ", token=" + Arrays.toString(token) + ", options=" + options + ", payload=" + Arrays.toString(payload) + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((messageID == null) ? 0 : messageID.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result + Arrays.hashCode(payload);
		result = prime * result + Arrays.hashCode(token);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoapMessage other = (CoapMessage) obj;
		if (code != other.code)
			return false;
		if (messageID == null)
		{
			if (other.messageID != null)
				return false;
		}
		else if (!messageID.equals(other.messageID))
			return false;
		if (options == null)
		{
			if (other.options != null)
				return false;
		}
		else if (!options.equals(other.options))
			return false;
		if (!Arrays.equals(payload, other.payload))
		{
			if ((payload == null && other.payload.length != 0) || other.payload == null && payload.length != 0)
				return false;
		}
		if (!Arrays.equals(token, other.token))
			return false;
		if (type != other.type)
			return false;
		if (version == null)
		{
			if (other.version != null)
				return false;
		}
		else if (!version.equals(other.version))
			return false;
		return true;
	}

	public Integer getVersion()
	{
		return version;
	}

	public void setVersion(Integer version)
	{
		this.version = version;
	}

	public CoapType getType()
	{
		return type;
	}

	public void setType(CoapType type)
	{
		this.type = type;
	}

	public CoapCode getCode()
	{
		return code;
	}

	public void setCode(CoapCode code)
	{
		this.code = code;
	}

	public Integer getMessageID()
	{
		return messageID;
	}

	public void setMessageID(Integer messageID)
	{
		this.messageID = messageID;
	}

	public byte[] getToken()
	{
		return token;
	}

	public void setToken(byte[] token)
	{
		this.token = token;
	}

	public List<CoapOption> getOptions()
	{
		return options;
	}

	public void setOptions(List<CoapOption> options)
	{
		this.options = options;
	}

	public byte[] getPayload()
	{
		return payload;
	}

	public void setPayload(byte[] payload)
	{
		this.payload = payload;
	}

	public static class Builder
	{
		private Integer version = 1;
		private CoapType type;
		private CoapCode code;
		private Integer messageID;
		private byte[] token;
		private List<CoapOption> options = new ArrayList<>();
		private byte[] payload;
		private CoapOptionsComparator comparator=new CoapOptionsComparator();
		
		public CoapMessage build()
		{
			Collections.sort(options,comparator);
			return new CoapMessage(version, type, code, messageID, token, options, payload);
		}

		public Builder version(Integer version)
		{
			this.version = version;
			return this;
		}

		public Builder type(CoapType type)
		{
			this.type = type;
			return this;
		}

		public Builder code(CoapCode code)
		{
			this.code = code;
			return this;
		}

		public Builder messageID(Integer messageID)
		{
			this.messageID = messageID;
			return this;
		}

		public Builder token(byte[] token)
		{
			this.token = token;
			return this;
		}

		public Builder option(CoapOption option)
		{
			this.options.add(option);
			return this;
		}

		public Builder option(CoapOptionType type, Object value)
		{
			this.options.add(OptionParser.encode(type, value));
			return this;
		}

		public Builder payload(byte[] payload)
		{
			this.payload = payload;
			return this;
		}
	}

	public class Options
	{
		public Integer fetchAccept()
		{
			Short value = fetchSingleValue(CoapOptionType.ACCEPT, Short.class);
			return value != null ? value.intValue() : null;
		}

		public Integer fetchUriPort()
		{
			Short value = fetchSingleValue(CoapOptionType.URI_PORT, Short.class);
			return value != null ? value.intValue() : null;
		}

		public Integer fetchContentFormat()
		{
			Short value = fetchSingleValue(CoapOptionType.CONTENT_FORMAT, Short.class);
			return value != null ? value.intValue() : null;
		}

		public Integer fetchMaxAge()
		{
			return fetchSingleValue(CoapOptionType.MAX_AGE, Integer.class);
		}

		public Integer fetchSize1()
		{
			return fetchSingleValue(CoapOptionType.SIZE1, Integer.class);
		}

		public Integer fetchObserve()
		{
			return fetchSingleValue(CoapOptionType.OBSERVE, Integer.class);
		}

		public boolean fetchIfNoneMatch()
		{
			return fetchSingleValue(CoapOptionType.IF_NONE_MATCH, byte[].class) != null;
		}

		public String fetchNodeID()
		{
			return fetchSingleValue(CoapOptionType.NODE_ID, String.class);
		}

		public String fetchIfMatch()
		{
			return fetchSingleValue(CoapOptionType.IF_MATCH, String.class);
		}

		public String fetchUriHost()
		{
			return fetchSingleValue(CoapOptionType.URI_HOST, String.class);
		}

		public String fetchEtag()
		{
			return fetchSingleValue(CoapOptionType.ETAG, String.class);
		}

		public String fetchUriPath()
		{
			return fetchSingleValue(CoapOptionType.URI_PATH, String.class);
		}

		public String fetchLocationPath()
		{
			return fetchSingleValue(CoapOptionType.LOCATION_PATH, String.class);
		}

		public String fetchUriQuery()
		{
			return fetchSingleValue(CoapOptionType.URI_QUERY, String.class);
		}

		public String fetchLocationQuery()
		{
			return fetchSingleValue(CoapOptionType.LOCATION_QUERY, String.class);
		}

		public String fetchProxyScheme()
		{
			return fetchSingleValue(CoapOptionType.PROXY_SCHEME, String.class);
		}

		public String fetchProxyUri()
		{
			return fetchSingleValue(CoapOptionType.PROXY_URI, String.class);
		}

		private <T> T fetchSingleValue(CoapOptionType targetType, Class<T> expectedClazz)
		{
			T value = null;
			if (options != null)
			{
				for (CoapOption option : options)
				{
					value = fetchOptionValue(targetType, option, expectedClazz);
					if (value != null)
						break;
				}
			}
			return value;
		}

		@SuppressWarnings("unchecked")
		private <T> T fetchOptionValue(CoapOptionType targetType, CoapOption option, Class<T> expectedClazz)
		{
			T value = null;
			CoapOptionType optionType = CoapOptionType.valueOf(option.getNumber());
			if (optionType == targetType)
				value = (T) OptionParser.decode(optionType, option.getValue());
			return value;
		}
	}
}
