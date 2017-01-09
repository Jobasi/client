package com.cognizant.helper;

import com.cognizant.entity.Customer;

public class BaseResponse {
	private CustomerList customerList;
	private Customer customer;
	private int statusCode;
	
	public BaseResponse() {
		// TODO Auto-generated constructor stub
	}
	
	
	public BaseResponse( Customer customer, int statusCode) {
		super();
		this.customer = customer;
		this.statusCode = statusCode;
	}
	public BaseResponse(CustomerList customerList, int statusCode) {
		super();
		this.customerList = customerList;
		this.statusCode = statusCode;
	}
	public BaseResponse(int statusCode) {
		super();
		this.statusCode = statusCode;
	}
	public CustomerList getCustomerList() {
		return customerList;
	}
	public void setCustomerList(CustomerList customerList) {
		this.customerList = customerList;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
