package com.cognizant.facade;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Component;

import com.cognizant.adapter.CustomerServiceRestAdapter;
import com.cognizant.entity.Customer;
import com.cognizant.exceptions.CustomerNotDeletedException;
import com.cognizant.exceptions.CustomerNotFoundException;
import com.cognizant.exceptions.CustomerNotSavedException;
import com.cognizant.exceptions.CustomerNotUpdatedException;
import com.cognizant.helper.CustomerList;
import com.cognizant.helper.StatusCode;
import com.cognizant.responses.CustomerResponse;


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
	public CustomerResponse findById(@PathParam(value = "id") Long id) {
		Customer customer = new Customer();
	
		try {
			customer = customerServiceRestAdapter.findById(id);
		} catch (CustomerNotFoundException e) {
			return new CustomerResponse(StatusCode.NOT_FOUND);		}
		return new CustomerResponse(customer, StatusCode.OK  );
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public CustomerResponse createCustomer(JAXBElement<Customer> customer) {
		Customer customerOne = new Customer();

		try {
			customerOne = customerServiceRestAdapter.createCustomer(customer.getValue());
		} catch (CustomerNotSavedException e) {
			return new CustomerResponse(StatusCode.BAD_REQUEST);
		}
		return new CustomerResponse(customerOne, StatusCode.OK);
		
	}
	
	@GET
	@Path(value="/list")
	@Produces(MediaType.APPLICATION_XML)
	public CustomerResponse getAllCustomers() throws IOException{	
		CustomerList customerList =  customerServiceRestAdapter.fetchAllCustomers();
		return new CustomerResponse(customerList, StatusCode.OK);
	}
	
	@PUT
	@Path(value="/update")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public CustomerResponse updateCustomer(JAXBElement<Customer> customer) throws IOException, MalformedURLException{
		try {
			customerServiceRestAdapter.updateCustomer(customer.getValue());
		} catch (CustomerNotUpdatedException e) {
			return new CustomerResponse(StatusCode.BAD_REQUEST);
		} catch (Exception e) {
			return new CustomerResponse(StatusCode.INTERNAL_SERVER_ERROR);
		}
		return new CustomerResponse(customer.getValue(), StatusCode.OK);
	}
	
	@DELETE
	@Path(value="/delete/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public CustomerResponse deleteCustomer(@PathParam(value = "id") Long id) throws IOException, MalformedURLException{
		Customer customer = new Customer();
		try {
			customerServiceRestAdapter.deleteCustomer(id);
		} catch (CustomerNotDeletedException e) {
			return new CustomerResponse(StatusCode.BAD_REQUEST);
		} catch (Exception e) {
			return new CustomerResponse(StatusCode.INTERNAL_SERVER_ERROR);
		}
		return new CustomerResponse(customer, StatusCode.OK);
	}
	
	@GET
	@Path("/email/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public CustomerResponse findByEmail(@PathParam(value = "email") String email) {
		Customer customer = new Customer();
	
		try {
			customer = customerServiceRestAdapter.findByEmail(email);
		} catch (CustomerNotFoundException e) {
			return new CustomerResponse(StatusCode.NOT_FOUND);		}
		return new CustomerResponse(customer, StatusCode.OK  );
	}
	
	
	

}
