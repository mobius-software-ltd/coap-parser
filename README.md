# COAP-parser

COAP parser is a library designed for encoding and decoding of AMQP packets. It is written in Java. 
COAP parser is developed by [Mobius Software](https://www.mobius-software.com/).


## Getting Started

First you should clone COAP parser. Then you should add the following lines within the <project> element of pom.xml file of 
your project:

```
<dependency>
         <groupId>com.mobius-software.coap</groupId>
	 <artifactId>coap-parser</artifactId>
         <version>1.0.1-SNAPSHOT</version>
</dependency>
```
Now you are able to start using COAP parser.

# Examples

## Create, encode, decode message

```
try
   {
    
      String name = "hanna";
      String clientId = "hanna007";
      String contentString = "hellow wo
      int version = 1;
      int messageID = 1;
      byte[] content = contentString.getBytes();
      byte[] nameBytes = name.getBytes();
      byte[] nodeIdBytes = clientId.getBytes();
      byte[] qosValue = new byte[2];
      qosValue[1] = 0x01;
      
      CoapMessage coapMessage = CoapMessage.builder().version(version).type(CoapType.CONFIRMABLE).code(CoapCode.PUT).messageID(messageID).payload(content)
      .option(new CoapOption(CoapOptionType.URI_PATH.getValue(), nameBytes.length, nameBytes))
      .option(new CoapOption((int) CoapOptionType.NODE_ID.getValue(), nodeIdBytes.length, nodeIdBytes)).option(new CoapOption(CoapOptionType.ACCEPT.getValue(), 2, qosValue)).build();
      // Encode message
      ByteBuf encoded = CoapParser.encode(open);
      // process encoded value...

      // Decode message
      CoapMessage decoded = COAPParser.decode(encoded);
      // process decoded value...https://github.com/mobius-software-ltd/coap-parser/blob/master/README.md
		}
		  catch (Exception e)
		{
			 e.printStackTrace();
			 fail();
		}

```
