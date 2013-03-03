package com.rhsummit.jbw13.vandelay.errai.client;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.api.EntryPoint;

import com.rhsummit.jbw13.vandelay.errai.client.VandelayRHSummit;


@EntryPoint
public class ErraiTestHelper {

	
	static ErraiTestHelper instance;
	
	@Inject VandelayRHSummit client;
	
	@PostConstruct
	public void init()
	{
		instance = this;
		System.out.println("Helper Class PostConstruct");
	}
}
