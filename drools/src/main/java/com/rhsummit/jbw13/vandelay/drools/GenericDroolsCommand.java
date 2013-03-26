package com.rhsummit.jbw13.vandelay.drools;

import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.common.DefaultAgenda;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.impl.AgendaImpl;


public class GenericDroolsCommand<T> implements Command<T> {
	
	private StatefulKnowledgeSession kSession;
	private KnowledgeBuilder kBuilder;
	
	public GenericDroolsCommand(Map<String,ResourceType> resources)
	{
		kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		
		for(String key : resources.keySet())
		{
			kBuilder.add(ResourceFactory.newClassPathResource(key, getClass()), resources.get(key));
		}
		
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kBuilder.getKnowledgePackages());	
		kSession = kbase.newStatefulKnowledgeSession();

	}
	
	public T execute(T object)
	{
		AgendaImpl agenda = (AgendaImpl) kSession.getAgenda();
		agenda.activateRuleFlowGroup("something");
		System.out.println(agenda.getAgenda().getRuleFlowGroup("something").isActive());
		kSession.insert(object);
//		kSession.execute(object);
		kSession.fireAllRules();
		
		if(kBuilder.hasErrors())
		{
		    System.out.println( kBuilder.getErrors().toString());
		}
		
		kSession.dispose();
		
		
		return object;
	}

}
