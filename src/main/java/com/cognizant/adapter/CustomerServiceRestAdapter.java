package com.cognizant.adapter;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cognizant.domain.Customer;
import com.cognizant.helper.CustomerBuilder;
import com.cognizant.helper.CustomerList;
import com.mce.contracts.CustomerServiceInterface;


public class CustomerServiceRestAdapter extends BaseClass implements CustomerServiceInterface {
	private final String baseUrl = "http://localhost:8080/server/api/customer";
	private final String USER_AGENT = "Client/1.0";
	

	public Response findCustomerById(Long id) {
		Customer customer  = new Customer();
		BufferedReader br;
		
		try {
			URL url = new URL(String.format("%s/find", baseUrl));
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Accept", "application/xml");
			
			for (StatusCode s : StatusCode.values())			
			if (httpConnection.getResponseCode() == s.getCode()) {
				switch(s){
				case OK:
					throw new RuntimeException("Failed : HTTP error code : "
							+ httpConnection.getResponseCode() + " " + s);	
				case Created:
					throw new RuntimeException("Failed : HTTP error code : "
							+ httpConnection.getResponseCode() + " " + s);
				case BadRequest:
					throw new RuntimeException("Failed : HTTP error code : "
							+ httpConnection.getResponseCode() + " " + s);
				case NotFound:
					throw new RuntimeException("Failed : HTTP error code : "
							+ httpConnection.getResponseCode() + " " + s);
				case InternalServerError:
					throw new RuntimeException("Failed : HTTP error code : "
							+ httpConnection.getResponseCode() + " " + s);
				default :
					throw new RuntimeException("Failed : HTTP error code : "
							+ httpConnection.getResponseCode() + " " + s);	
				}
			
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
		
	
		return Response.status(200).entity(customer).build();
		
	}

	public Response fetchAllCustomers() {
		CustomerList customer = new CustomerList();
		BufferedReader br;
		try {
			URL url = new URL(String.format("%s/list", baseUrl));
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Accept", "application/xml");
			
			for (StatusCode s : StatusCode.values())			
				if (httpConnection.getResponseCode() == s.getCode()) {
					switch(s){
					case OK:
						break;
					case NotFound:
						return Response.status(Status.NOT_FOUND).build();
					case InternalServerError:
						return Response.status(500).build();
					default :
						break;
					}			
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
		return Response.status(200).entity(customer).build();
		
	}

	public Response saveCustomer(JAXBElement<Customer> customer) {
		Customer c = customer.getValue();
		Customer cus = new Customer();
		cus = new CustomerBuilder(c.getFirstName(), c.getLastName())
				.withEmail(c.getEmail())
				.withPhone(c.getPhoneNumber())
				.build();
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
		
		return Response.status(200).entity(cus).build();
	}

	public Response deleteCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return Response.status(200).build();
	}

	public Response updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return Response.status(200).build();
	}

}
