package com.mce.contracts;

import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

import com.cognizant.domain.Customer;
import com.cognizant.exceptions.CustomerNotDeletedException;
import com.cognizant.exceptions.CustomerNotFetchedException;
import com.cognizant.exceptions.CustomerNotFoundException;
import com.cognizant.exceptions.CustomerNotSavedException;
import com.cognizant.exceptions.CustomerNotUpdatedException;
import com.cognizant.helper.CustomerList;

public interface CustomerServiceInterface {
	
	Response findCustomerById(Long id) throws CustomerNotFoundException;
	Response fetchAllCustomers() throws CustomerNotFetchedException;
	Response saveCustomer(JAXBElement<Customer> customer) throws CustomerNotSavedException;
	Response deleteCustomer(Customer customer) throws CustomerNotDeletedException;
	Response updateCustomer(Customer customer) throws CustomerNotUpdatedException;

}
