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
		Customer customer = customerServiceRestAdapter.findCustomerById(1l);
		return Response.status(200).entity(customer).build();
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response createCustomer(JAXBElement<Customer> customer) {
		Customer customer2 = customerServiceRestAdapter.saveCustomer(customer);
		if (customer2.getFirstName() == null 
				||
				customer2.getFirstName().trim().isEmpty() 
				||  
				customer2.getEmail().trim().isEmpty() 
				||
				customer2.getEmail() == null ){
			return Response.status(BAD_REQUEST).build();
		}
		return Response.status(201).entity(customer).build();
	}

	@GET
	@Path(value="/list")
	@Produces(MediaType.APPLICATION_XML)
	public CustomerList getAllCustomers(){	
		return customerServiceRestAdapter.fetchAllCustomers();
	}
	

}
