package com.rhsummit.jbw13.vandelay.model;

import java.util.Date;

public class Customer implements BaseModel {

	private Integer id;
	private Date subscriptionExpiration;
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
		
	}

	public Date getSubscriptionExpiration() {
		return subscriptionExpiration;
	}

	public void setSubscriptionExpiration(Date subscriptionExpiration) {
		this.subscriptionExpiration = subscriptionExpiration;
	}

}
