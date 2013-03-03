package com.rhsummit.jbw13.vandelay.errai.client;

import org.jboss.errai.enterprise.client.jaxrs.api.RestClient;
import org.jboss.errai.enterprise.client.jaxrs.test.AbstractErraiJaxrsTest;
import org.junit.Test;

public class VandelayRHSummitTest extends AbstractErraiJaxrsTest {

	@Override
	public String getModuleName() {
		return "com.rhsummit.jbw13.vandelay.errai.VandelayRHSummit";
	}
	
	@Override
	protected void gwtSetUp() throws Exception
	{
		RestClient.setApplicationRoot("/vandelay-rest/");
		super.gwtSetUp();
	}
	
	@Test
	public void testRetrieveData()
	{
		final VandelayRHSummit mainApp = ErraiTestHelper.instance.client;
		
		assertEquals(1,mainApp.rows.size());
		delayTestFinish(20000);
	}
}
