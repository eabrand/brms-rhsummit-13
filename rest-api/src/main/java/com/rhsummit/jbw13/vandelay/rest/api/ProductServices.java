package com.rhsummit.jbw13.vandelay.rest.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.rhsummit.jbw13.vandelay.model.Product;

@Path("product")
public interface ProductServices {
	
	@GET
	@Path("test")
	@Produces(MediaType.APPLICATION_JSON)
	public Product test();

	
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> list();

}
