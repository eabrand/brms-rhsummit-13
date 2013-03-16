package com.rhsummit.jbw13.vandelay.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product implements Serializable {
	
	private Integer id;
	private String name;
	private BigDecimal price;
	private double weightLbs;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public double getWeightLbs() {
		return weightLbs;
	}
	public void setWeightLbs(double weightLbs) {
		this.weightLbs = weightLbs;
	}

}
