package com.rhsummit.jbw13.vandelay.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Order implements BaseModel {

	private Integer id;
	private List<Product> products = new ArrayList<Product>();
	private Set<Discount> discounts = new HashSet<Discount>();
	private Set<Promo> promos = new HashSet<Promo>();
	private Set<ExtraCharge> extraCharges = new HashSet<ExtraCharge>();
	private boolean rushShipping;
	
	private Customer customer;
	
	private String firstName;
	private String lastName;
	private String streetAddress1;
	private String streetAddress2;
	private String city;
	private String state;
	private String zip;
	
	
	private BigDecimal subtotal = new BigDecimal(0);
	private BigDecimal shipping;
	private BigDecimal tax = new BigDecimal(0);
	private BigDecimal total = new BigDecimal(0);
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public Set<Discount> getDiscounts() {
		return discounts;
	}
	public void setDiscounts(Set<Discount> discounts) {
		this.discounts = discounts;
	}
	public Set<Promo> getPromos() {
		return promos;
	}
	public void setPromos(Set<Promo> promos) {
		this.promos = promos;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getStreetAddress1() {
		return streetAddress1;
	}
	public void setStreetAddress1(String streetAddress1) {
		this.streetAddress1 = streetAddress1;
	}
	public String getStreetAddress2() {
		return streetAddress2;
	}
	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public BigDecimal getShipping() {
		return shipping;
	}
	public void setShipping(BigDecimal shipping) {
		this.shipping = shipping;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public Set<ExtraCharge> getExtraCharges() {
		return extraCharges;
	}
	public void setExtraCharges(Set<ExtraCharge> extraCharges) {
		this.extraCharges = extraCharges;
	}
	
	public boolean isRushShipping() {
		return rushShipping;
	}
	public void setRushShipping(boolean rushShipping) {
		this.rushShipping = rushShipping;
	}



	
}
