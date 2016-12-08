package com.mce.contracts;

import java.io.IOException;
import java.util.List;

import com.cognizant.domain.Customer;
import com.cognizant.exceptions.CustomerNotDeletedException;
import com.cognizant.exceptions.CustomerNotFetchedException;
import com.cognizant.exceptions.CustomerNotFoundException;
import com.cognizant.exceptions.CustomerNotSavedException;
import com.cognizant.exceptions.CustomerNotUpdatedException;

public interface CustomerServiceInterface {
	
	Customer findCustomerById(Long id) throws CustomerNotFoundException;
	List<Customer> fetchAllCustomers() throws CustomerNotFetchedException;
	Customer saveCustomer(Customer customer) throws CustomerNotSavedException;
	Long deleteCustomer(Customer customer) throws CustomerNotDeletedException;
	Customer updateCustomer(Customer customer) throws CustomerNotUpdatedException;

}
