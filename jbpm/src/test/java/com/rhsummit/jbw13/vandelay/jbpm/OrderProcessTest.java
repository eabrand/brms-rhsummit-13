package com.rhsummit.jbw13.vandelay.jbpm;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.junit.Test;

import com.rhsummit.jbw13.vandelay.model.Order;
import com.rhsummit.jbw13.vandelay.model.Product;

public class OrderProcessTest {

	
	@Test
	public void testOrderProcess()
	{
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("OrderProcess.bpmn"), ResourceType.BPMN2);
		kbuilder.add(ResourceFactory.newClassPathResource("OrderRules.drl"), ResourceType.DRL);
		KnowledgeBase kbase = kbuilder.newKnowledgeBase();
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		
		Order order = new Order();
		
		Product product1 = new Product();
		product1.setId(1);
		product1.setPrice(new BigDecimal(10));
		
		order.getProducts().add(product1);

		ksession.insert(order);		
		ProcessInstance processInstance = ksession.startProcess("com.rhsummit.jbw13.vandelay.jbpm.OrderProcess");
		ksession.fireAllRules();
		
		assertTrue(processInstance.getState() == ProcessInstance.STATE_COMPLETED);
		assertNotNull(order.getTotal());
		assertTrue(order.getTotal().compareTo(new BigDecimal(0)) != 0);
		
	}
}
