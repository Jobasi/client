package com.cognizant.adapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.cognizant.domain.Customer;
import com.mce.contracts.CustomerServiceInterface;


public class CustomerServiceRestAdapter implements CustomerServiceInterface {
	private final String baseUrl = "http://localhost:8080/server/api/customer";
	

	public Customer findCustomerById(Long id) {
		Customer customer  = new Customer();
		BufferedReader br;
		
		try {
			URL url = new URL(String.format("%s/find", baseUrl));
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Accept", "application/xml");
			
			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ httpConnection.getResponseCode());			
			}
			br = new BufferedReader(new InputStreamReader(
					(httpConnection.getInputStream())));
			
			String xmlString = br.readLine();  
			
			JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			StringReader reader = new StringReader(xmlString);
			 customer = (Customer) unmarshaller.unmarshal(reader);		
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	
		return customer;
		
	}

	public List<Customer> fetchAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	public Customer saveCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long deleteCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	public Customer updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

}
