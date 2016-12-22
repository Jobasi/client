package com.cognizant.facade;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Component;

import com.cognizant.adapter.CustomerServiceRestAdapter;
import com.cognizant.entity.Customer;
import com.cognizant.helper.BaseResponse;


@Component
@Path(value="/customer")
public class CustomerFacade {
	
	private CustomerServiceRestAdapter customerServiceRestAdapter;	
	
	public void setCustomerServiceRestAdapter(CustomerServiceRestAdapter customerServiceRestAdapter) {
		this.customerServiceRestAdapter = customerServiceRestAdapter;
	}

	@GET
	@Path("/find/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getCustomer(@PathParam(value = "id") Long id) {
		BaseResponse baseResponse = customerServiceRestAdapter.findCustomerById(id);
		return Response.status(baseResponse.getStatusCode())
				.entity(baseResponse.getCustomer())
				.build();
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response createCustomer(JAXBElement<Customer> customer) {
		BaseResponse baseResponse = customerServiceRestAdapter.saveCustomer(customer);
		return Response.status(baseResponse.getStatusCode())
				.entity(baseResponse.getCustomer())
				.build();
		
	}

	@GET
	@Path(value="/list")
	@Produces(MediaType.APPLICATION_XML)
	public Response getAllCustomers(){	
		BaseResponse baseResponse =  customerServiceRestAdapter.fetchAllCustomers();
		return Response.status(baseResponse.getStatusCode())
				.entity(baseResponse.getCustomerList())
				.build();
	}
	

}
