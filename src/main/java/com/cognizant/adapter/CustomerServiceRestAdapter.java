package com.cognizant.adapter;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cognizant.entity.Customer;
import com.cognizant.exceptions.CustomerNotFoundException;
import com.cognizant.exceptions.CustomerNotSavedException;
import com.cognizant.helper.BaseResponse;
import com.cognizant.helper.CustomerBuilder;
import com.cognizant.helper.CustomerList;
import com.mce.contracts.CustomerServiceInterface;


public class CustomerServiceRestAdapter {
	private final String baseUrl = "http://localhost:8080/server/api/customer";
	
	private HttpURLConnection makeRestCall(String path, String method, Long id) throws IOException  {
		URL url = new URL(String.format("%s/%s/%d", baseUrl, path, id));
		HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
		httpConnection.setRequestMethod(method);
		httpConnection.setRequestProperty("Accept", "application/xml");
		return httpConnection;
	}
	

	private HttpURLConnection makeRestCall(String path, String method) throws IOException  {
		URL url = new URL(String.format("%s/%s/", baseUrl, path));
		HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
		httpConnection.setRequestMethod(method);
		httpConnection.setRequestProperty("Content-Type", "application/xml");
		httpConnection.setRequestProperty("Accept", "application/xml");
		httpConnection.setDoInput(true);
		httpConnection.setDoOutput(true);
		return httpConnection;
	}
	

	public Customer findById(Long customerId) throws CustomerNotFoundException{
		Customer customer = new Customer();
		BufferedReader bufferedReader = null;
		try{
			HttpURLConnection httpConnection = makeRestCall("find", "GET", customerId);
			if(httpConnection.getResponseCode() == 404){
				throw new CustomerNotFoundException();
			}
			bufferedReader = new BufferedReader(new InputStreamReader(
					(httpConnection.getInputStream())));
			
			String xmlString = bufferedReader.readLine();  
			
			JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			StringReader reader = new StringReader(xmlString);
			customer = (Customer) unmarshaller.unmarshal(reader);
			
			if (customer == null){
				throw new CustomerNotFoundException();
			}
			
		} catch (RuntimeException | IOException | JAXBException e){
			
		}
		return customer;
	}
	
	
	public Customer createCustomer(Customer customer) throws CustomerNotSavedException {
		//Customer customerOne = new Customer();
		BufferedReader bufferedReader = null;
		try {
			HttpURLConnection conn = makeRestCall("create", "POST");
			
		    StringWriter stringWriter = new StringWriter();
		    JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
		    Marshaller marshaller = jaxbContext.createMarshaller();
		    marshaller.marshal(customer, stringWriter);
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
			 customer = (Customer) unmarshaller.unmarshal(reader);	
		    
		    
		    conn.disconnect();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return customer;
	}
	
	
	public CustomerList fetchAllCustomers() throws IOException {
		HttpURLConnection httpConnection = makeRestCall("list", "GET");
		CustomerList customer = new CustomerList();
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					(httpConnection.getInputStream())));
			String xmlString = bufferedReader.readLine(); 
			JAXBContext jaxbContext = JAXBContext.newInstance(CustomerList.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			StringReader reader = new StringReader(xmlString);
			customer = (CustomerList) unmarshaller.unmarshal(reader);
		} catch (IOException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return customer;
	}


	public Customer deleteCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return new Customer();
	}

	public Customer updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return new Customer();
		}
	}
