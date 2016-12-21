package com.cognizant.facade;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Component;

import com.cognizant.adapter.CustomerServiceRestAdapter;
import com.cognizant.domain.Customer;
import com.cognizant.helper.CustomerList;


@Component
@Path(value="/customer")
public class CustomerFacade {
	
	private static final int BAD_REQUEST = 400;
	private CustomerServiceRestAdapter customerServiceRestAdapter;	
	
	public void setCustomerServiceRestAdapter(CustomerServiceRestAdapter customerServiceRestAdapter) {
		this.customerServiceRestAdapter = customerServiceRestAdapter;
	}

	@GET
	@Path("/find")
	@Produces(MediaType.APPLICATION_XML)
	public Response getCustomer() {
		return customerServiceRestAdapter.findCustomerById(1l);
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response createCustomer(JAXBElement<Customer> customer) {
		return customerServiceRestAdapter.saveCustomer(customer);
	}

	@GET
	@Path(value="/list")
	@Produces(MediaType.APPLICATION_XML)
	public Response getAllCustomers(){	
		return customerServiceRestAdapter.fetchAllCustomers();
	}
	

}
