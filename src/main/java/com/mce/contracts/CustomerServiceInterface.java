package com.mce.contracts;

import javax.xml.bind.JAXBElement;

import com.cognizant.domain.Customer;
import com.cognizant.exceptions.CustomerNotDeletedException;
import com.cognizant.exceptions.CustomerNotFetchedException;
import com.cognizant.exceptions.CustomerNotFoundException;
import com.cognizant.exceptions.CustomerNotSavedException;
import com.cognizant.exceptions.CustomerNotUpdatedException;
import com.cognizant.helper.CustomerList;

public interface CustomerServiceInterface {
	
	Customer findCustomerById(Long id) throws CustomerNotFoundException;
	CustomerList fetchAllCustomers() throws CustomerNotFetchedException;
	Customer saveCustomer(JAXBElement<Customer> customer) throws CustomerNotSavedException;
	Long deleteCustomer(Customer customer) throws CustomerNotDeletedException;
	Customer updateCustomer(Customer customer) throws CustomerNotUpdatedException;

}
