package com.cognizant.adapter;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.cognizant.domain.Customer;
import com.cognizant.helper.CustomerBuilder;
import com.cognizant.helper.CustomerList;
import com.mce.contracts.CustomerServiceInterface;


public class CustomerServiceRestAdapter implements CustomerServiceInterface {
	private final String baseUrl = "http://localhost:8080/server/api/customer";
	private final String USER_AGENT = "Client/1.0";
	

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

	public CustomerList fetchAllCustomers() {
		CustomerList customer = new CustomerList();
		BufferedReader br;
		try {
			URL url = new URL(String.format("%s/list", baseUrl));
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
			
			JAXBContext jaxbContext = JAXBContext.newInstance(CustomerList.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			StringReader reader = new StringReader(xmlString);
			 customer = (CustomerList) unmarshaller.unmarshal(reader);		
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return customer;
		
	}

	public Customer saveCustomer(JAXBElement<Customer> customer) {
		Customer c = customer.getValue();
		Customer cus = new Customer();
		try {
			cus = new CustomerBuilder(c.getFirstName(), c.getLastName())
								.withEmail(c.getEmail())
								.withPhone(c.getPhoneNumber())
								.build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return cus;
		}
		BufferedReader bufferedReader;

		
		try {
			URL url = new URL(String.format("%s/create", baseUrl));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    conn.setRequestMethod("POST");
		    conn.setRequestProperty("Content-Type", "application/xml");
		    conn.setDoInput(true);
		    conn.setDoOutput(true);
		    
		    StringWriter stringWriter = new StringWriter();
		    JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
		    Marshaller marshaller = jaxbContext.createMarshaller();
		    marshaller.marshal(cus, stringWriter);
		    String xmlString = stringWriter.toString();
		   
		    OutputStream output = new BufferedOutputStream(conn.getOutputStream());
		    output.write(xmlString.getBytes());
		    output.flush();
		    
		    bufferedReader = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			
			String xmlStringResponse = bufferedReader.readLine();  
			
			JAXBContext jaxbContextResponse = JAXBContext.newInstance(Customer.class);
			Unmarshaller unmarshaller = jaxbContextResponse.createUnmarshaller();

			StringReader reader = new StringReader(xmlStringResponse);
			 cus = (Customer) unmarshaller.unmarshal(reader);	
		    
		    
		    conn.disconnect();
		} catch (IOException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cus;
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
