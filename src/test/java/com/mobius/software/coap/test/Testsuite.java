package com.mobius.software.coap.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
{ PositiveTests.class, NegativeTests.class })
public class Testsuite
{
	public static final int REPEAT_COUNT = 10;
}
