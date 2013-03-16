package com.rhsummit.jbw13.vandelay.drools;

public interface Command<T> {
	
	public T execute(T object);

}
