package com.cognizant.responses;

import javax.xml.bind.annotation.XmlRootElement;

import com.cognizant.entity.Customer;
import com.cognizant.helper.BaseResponse;
import com.cognizant.helper.CustomerList;
import com.cognizant.helper.StatusCode;

@XmlRootElement(name="customer-response")
public class CustomerResponse extends BaseResponse {
	
	public CustomerResponse() {
		super();
	}
	
	public CustomerResponse(CustomerList customerList, int statusCode) {
		super(customerList, statusCode);
	}

	public CustomerResponse(Customer customer, int statusCode) {
		super(customer, statusCode);
		// TODO Auto-generated constructor stub
	}
	
	public CustomerResponse(int statusCode) {
		super(statusCode);
		// TODO Auto-generated constructor stub
	}

}
