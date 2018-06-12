package com.mobius.software.coap.parser.message.options;

import java.util.Comparator;

public class CoapOptionsComparator implements Comparator<CoapOption>
{
	@Override
	public int compare(CoapOption o1, CoapOption o2) 
	{
		if(o1.getNumber()>o2.getNumber())
			return 1;
		else if(o1.getNumber()==o2.getNumber())
			return 0;
		
		return -1;
	}
}
