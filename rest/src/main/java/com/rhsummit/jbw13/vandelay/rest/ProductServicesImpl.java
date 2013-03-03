package com.rhsummit.jbw13.vandelay.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.rhsummit.jbw13.vandelay.model.Product;
import com.rhsummit.jbw13.vandelay.rest.api.ProductServices;

public class ProductServicesImpl implements ProductServices {
	
	private final List<Product> products = new ArrayList<Product>();

	@PostConstruct
	public void setup()
	{
		Product product1 = new Product();
		product1.setId(1);
		product1.setName("Latex Gloves");
		product1.setPrice(new BigDecimal(100));
		
		products.add(product1);
	}
	
	@Override
	public Product test()
	{
		return products.get(0);
	}

	@Override
	public List<Product> list() {
		return products;
	}

}
