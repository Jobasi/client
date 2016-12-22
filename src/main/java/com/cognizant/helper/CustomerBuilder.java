package com.cognizant.helper;

import com.cognizant.entity.Customer;

public class CustomerBuilder {
	private final String firstName;
    private final String lastName;
    private String email;
    private long phoneNumber;


    public CustomerBuilder(String firstName, String lastName) {
    	this.firstName = firstName;
        this.lastName = lastName;
        
    }

    public CustomerBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public CustomerBuilder withPhone(long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Customer build() {
        return new Customer(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

}
