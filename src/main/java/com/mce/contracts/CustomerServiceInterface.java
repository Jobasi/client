package com.mce.contracts;

import javax.xml.bind.JAXBElement;

import com.cognizant.entity.Customer;
import com.cognizant.exceptions.CustomerNotDeletedException;
import com.cognizant.exceptions.CustomerNotFetchedException;
import com.cognizant.exceptions.CustomerNotFoundException;
import com.cognizant.exceptions.CustomerNotSavedException;
import com.cognizant.exceptions.CustomerNotUpdatedException;
import com.cognizant.helper.BaseResponse;

public interface CustomerServiceInterface {
	
	BaseResponse findCustomerById(Long id) throws CustomerNotFoundException;
	BaseResponse fetchAllCustomers() throws CustomerNotFetchedException;
	BaseResponse saveCustomer(JAXBElement<Customer> customer) throws CustomerNotSavedException;
	BaseResponse deleteCustomer(Customer customer) throws CustomerNotDeletedException;
	BaseResponse updateCustomer(Customer customer) throws CustomerNotUpdatedException;

}
