package com.cognizant.dao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.cognizant.adapter.CustomerServiceRestAdapter;
import com.cognizant.domain.Customer;


@Component
@Path(value="/customer")
public class CustomerDAO {
	
	private CustomerServiceRestAdapter customerServiceRestAdapter;	
	
	public void setCustomerServiceRestAdapter(CustomerServiceRestAdapter customerServiceRestAdapter) {
		this.customerServiceRestAdapter = customerServiceRestAdapter;
	}

	@GET
	@Path("/find")
	@Produces(MediaType.APPLICATION_XML)
	public Customer getCustomer() {
		return customerServiceRestAdapter.findCustomerById(1l);
	}


}
