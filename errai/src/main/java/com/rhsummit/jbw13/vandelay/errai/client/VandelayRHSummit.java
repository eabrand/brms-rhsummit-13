package com.rhsummit.jbw13.vandelay.errai.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.enterprise.client.jaxrs.api.ResponseException;
import org.jboss.errai.enterprise.client.jaxrs.api.RestClient;
import org.jboss.errai.ioc.client.api.Caller;
import org.jboss.errai.ioc.client.api.EntryPoint;

import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.rhsummit.jbw13.vandelay.model.Product;
import com.rhsummit.jbw13.vandelay.rest.api.ProductServices;


@EntryPoint
public class VandelayRHSummit {

	@Inject
	private Caller<ProductServices> productServices;
		
	  final private FlexTable customersTable = new FlexTable();
	  
	  private final Label idLabel = new Label();
	  private final Label nameLabel = new Label();
	  private final Label priceLabel = new Label();

	  final Map<Integer, Integer> rows = new HashMap<Integer, Integer>();
	  
	  
	  @PostConstruct
	  public void init()
	  {
		  	
		    FlexTable newCustomerTable = new FlexTable();
		    newCustomerTable.setWidget(0, 1, idLabel);
		    newCustomerTable.setWidget(0, 2, nameLabel);
		    newCustomerTable.setWidget(0, 3, priceLabel);
		    newCustomerTable.setStyleName("new-customer-table");

		    VerticalPanel vPanel = new VerticalPanel();
		    vPanel.add(customersTable);
		    vPanel.add(new HTML("<hr>"));
		    vPanel.add(newCustomerTable);
		    vPanel.addStyleName("whole-customer-table");
		    RootPanel.get().add(vPanel);
		    
		    listProducts();
  
	  }
	  
	  private void listProducts()
	  {
		  
		    customersTable.setText(0, 0, "ID");
		    customersTable.setText(0, 1, "Name");
		    customersTable.setText(0, 2, "Price");

		  final RemoteCallback<List<Product>> listProductCallback = new RemoteCallback<List<Product>>(){
			@Override
			public void callback(List<Product> response) {

				for(Product product : response)
				{
					addToProductTable(product, customersTable.getRowCount()+1);
				}
				
			}
		  };
		  
		  final ErrorCallback errorCallback = new ErrorCallback() {
			
			@Override
			public boolean error(Message message, Throwable throwable) {
				
				try {
				      throw throwable;
				    }
				    catch (ResponseException e) {
				      Response response = e.getResponse();
				      // process unexpected response
				      response.getStatusCode();
				    }
				    catch (Throwable t) {
				      // process unexpected error (e.g. a network problem)
				    }
				    return false;
				  }
			
		};
		  
		  productServices.call(listProductCallback, errorCallback).list();
	  }
	  
	  private void addToProductTable(Product product, int row)
	  {
		  final Label idLabel = new Label();
		  idLabel.setText(String.valueOf(product.getId()));
		  
		  final Label nameLabel = new Label();
		  nameLabel.setText(product.getName());
		  
		  final Label priceLabel = new Label();
		  priceLabel.setText(NumberFormat.getCurrencyFormat().format(product.getPrice()));
		  
		  customersTable.setWidget(row, 0, idLabel);
		  customersTable.setWidget(row, 1, nameLabel);
		  customersTable.setWidget(row, 2, priceLabel);
		  rows.put(product.getId(), row);
	  }
}
