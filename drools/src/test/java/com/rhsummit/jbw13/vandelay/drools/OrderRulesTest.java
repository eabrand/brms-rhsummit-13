package com.rhsummit.jbw13.vandelay.drools;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.drools.builder.ResourceType;
import org.junit.Before;
import org.junit.Test;

import com.rhsummit.jbw13.vandelay.model.Customer;
import com.rhsummit.jbw13.vandelay.model.Order;
import com.rhsummit.jbw13.vandelay.model.Product;
import com.rhsummit.jbw13.vandelay.model.Promo;

public class OrderRulesTest {
	
	private GenericDroolsCommand<Order> orderCommand;
	
	
	@Before
	public void setup()
	{
		Map<String,ResourceType> resourceMap = new HashMap<String,ResourceType>();
		resourceMap.put("OrderRules.drl", ResourceType.DRL);

		orderCommand = new GenericDroolsCommand<Order>(resourceMap);
	}
	
	
	
	@Test
	public void testSimpleOrder()
	{
		Order order = new Order();
		
		Product product1 = new Product();
		product1.setId(1);
		product1.setPrice(new BigDecimal(10));
		
		order.getProducts().add(product1);
		
		executeOrderRulesAndBasicRules(order, "10", "0", "5", "15");
		
		assertEquals(0,order.getDiscounts().size());
		assertEquals(0, order.getPromos().size());
		assertEquals(0,order.getPromos().size());
	}
	

	@Test
	public void testStateTaxRules()
	{
		Order order = new Order();
		order.setState("NY");
		Product product1 = new Product();
		product1.setId(1);
		product1.setPrice(new BigDecimal(10));
		
		order.getProducts().add(product1);
		
		
		// Because of Decimals in Results, we will handle tests here
		
		orderCommand.execute(order);
		

		assertEquals(new BigDecimal(10),order.getSubtotal());
		assertEquals(new BigDecimal(0.75),order.getTax().setScale(2, RoundingMode.HALF_UP));
		assertEquals(new BigDecimal(5),order.getShipping());
		assertEquals(new BigDecimal(15.75),order.getTotal().setScale(2, RoundingMode.HALF_UP));

		assertEquals(0,order.getDiscounts().size());
		assertEquals(0, order.getPromos().size());
		assertEquals(0,order.getPromos().size());
		
	}
	
	@Test
	public void testNonActiveMember() throws ParseException
	{
		Order order = new Order();
		Product product1 = new Product();
		product1.setId(1);
		product1.setPrice(new BigDecimal(10));
		
		order.getProducts().add(product1);
		
		Customer customer = new Customer();
		customer.setSubscriptionExpiration(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2010"));
		
		order.setCustomer(customer);
		
		executeOrderRulesAndBasicRules(order, "10", "0", "5", "15");	
		
		assertEquals(0,order.getDiscounts().size());
		assertEquals(0, order.getPromos().size());
		assertEquals(0,order.getPromos().size());		
		
	}
	
	@Test
	public void testActiveMember() throws ParseException
	{
		Order order = new Order();
		Product product1 = new Product();
		product1.setId(1);
		product1.setPrice(new BigDecimal(10));
		
		order.getProducts().add(product1);
		
		Customer customer = new Customer();
		customer.setSubscriptionExpiration(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2020"));
		
		order.setCustomer(customer);
		
		executeOrderRulesAndBasicRules(order, "10", "0", "0", "10");
		
		assertEquals(0,order.getDiscounts().size());
		assertEquals(0, order.getPromos().size());
		assertEquals(0,order.getPromos().size());
		
		
	}


	@Test
	public void testFreeShipping50Rule() throws ParseException
	{
		Order order = new Order();
		Product product1 = new Product();
		product1.setId(1);
		product1.setPrice(new BigDecimal(15.50));
		
		
		Product product2 = new Product();
		product2.setId(2);
		product2.setPrice(new BigDecimal(45.75));

		
		order.getProducts().add(product1);
		order.getProducts().add(product2);
		
		executeOrderRulesAndBasicRules(order, "61.25", "0", "0", "61.25");
		
		assertEquals(0,order.getDiscounts().size());
		assertEquals(0, order.getPromos().size());
		assertEquals(0,order.getPromos().size());
		
		
	}
	
	@Test
	public void testOversizeItems()
	{
		Order order = new Order();
		
		Product product1 = new Product();
		product1.setId(1);
		product1.setPrice(new BigDecimal(100));
		product1.setWeightLbs(55);
		product1.setName("Large Sofa");
		
		Product product2 = new Product();
		product2.setId(2);
		product2.setPrice(new BigDecimal(10));
		product2.setWeightLbs(15);
		product2.setName("Table");
		
		Product product3 = new Product();
		product3.setId(3);
		product3.setPrice(new BigDecimal(350));
		product3.setWeightLbs(100);
		product3.setName("Extra Large Sofa");
		
		order.getProducts().add(product1);
		order.getProducts().add(product2);
		order.getProducts().add(product3);
		
		executeOrderRulesAndBasicRules(order, "460", "0", "0", "480");
		
		
		assertEquals(2, order.getExtraCharges().size());
		
	}
	
	@Test
	public void testRushShippingCharge()
	{
		Order order = new Order();
		order.setRushShipping(true);
		
		Product product1 = new Product();
		product1.setId(1);
		product1.setPrice(new BigDecimal(25));
		
		order.getProducts().add(product1);
		
		executeOrderRulesAndBasicRules(order, "25", "0", "5", "40");
		
		assertEquals(1, order.getExtraCharges().size());
		assertEquals(0,order.getDiscounts().size());
		assertEquals(0, order.getPromos().size());
		assertEquals(0,order.getPromos().size());
		
	}
	
	@Test
	public void testRushShippingChargePaidCustomer() throws ParseException
	{
		Order order = new Order();
		order.setRushShipping(true);
		
		Product product1 = new Product();
		product1.setId(1);
		product1.setPrice(new BigDecimal(25));
		
		order.getProducts().add(product1);
		
		Customer customer = new Customer();
		customer.setSubscriptionExpiration(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2020"));
		
		order.setCustomer(customer);

		
		orderCommand.execute(order);
		
		assertEquals(0,order.getDiscounts().size());
		assertEquals(0, order.getPromos().size());
		assertEquals(new BigDecimal(25),order.getSubtotal());
		assertEquals(0,order.getPromos().size());
		assertEquals(new BigDecimal(0),order.getTax());
		assertEquals(new BigDecimal(0),order.getShipping());
		assertEquals(new BigDecimal(25),order.getTotal());
		
	}
	
	@Test
	public void testPromoCodeBRMS() throws ParseException
	{
		Order order = new Order();
		
		Product product1 = new Product();
		product1.setId(1);
		product1.setPrice(new BigDecimal(25));
		
		order.getProducts().add(product1);
		order.getPromos().add(new Promo("BRMS"));
		
		
		orderCommand.execute(order);
		
		assertEquals(1,order.getDiscounts().size());
		assertEquals(1, order.getPromos().size());
		assertEquals(new BigDecimal(25),order.getSubtotal());
		assertEquals(new BigDecimal(0),order.getTax());
		assertEquals(new BigDecimal(5),order.getShipping());
		assertEquals(new BigDecimal(24.00).setScale(2),order.getTotal().setScale(2));
		
	}
	
	@Test
	public void testPromoCodeRHSUMMIT() throws ParseException
	{
		Order order = new Order();
		
		Product product1 = new Product();
		product1.setId(1);
		product1.setPrice(new BigDecimal(25));
		
		order.getProducts().add(product1);
		order.getPromos().add(new Promo("RHSUMMIT"));
		
		
		orderCommand.execute(order);
		
		assertEquals(1,order.getDiscounts().size());
		assertEquals(1, order.getPromos().size());
		assertEquals(new BigDecimal(25),order.getSubtotal());
		assertEquals(new BigDecimal(0),order.getTax());
		assertEquals(new BigDecimal(0),order.getShipping());
		assertEquals(new BigDecimal(22.50).setScale(2),order.getTotal().setScale(2));
		
	}
	
	private void executeOrderRulesAndBasicRules(Order order, String subtotal, String tax, String shipping, String total)
	{
		orderCommand.execute(order);
		
		assertEquals(new BigDecimal(subtotal),order.getSubtotal());
		assertEquals(new BigDecimal(tax),order.getTax());
		assertEquals(new BigDecimal(shipping),order.getShipping());
		assertEquals(new BigDecimal(total),order.getTotal());
	}
	

}
