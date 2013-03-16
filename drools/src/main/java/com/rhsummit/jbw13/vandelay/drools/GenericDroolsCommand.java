package com.rhsummit.jbw13.vandelay.drools;

import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;


public class GenericDroolsCommand<T> implements Command<T> {
	
	private StatelessKnowledgeSession kSession;
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
		
		kSession = kbase.newStatelessKnowledgeSession();

	}
	
	public T execute(T object)
	{
		
		kSession.execute(object);
		
		if(kBuilder.hasErrors())
		{
		    System.out.println( kBuilder.getErrors().toString());
		}
		
		
		return object;
	}

}
