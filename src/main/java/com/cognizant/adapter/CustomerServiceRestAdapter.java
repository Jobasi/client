package com.cognizant.adapter;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cognizant.entity.Customer;
import com.cognizant.helper.BaseResponse;
import com.cognizant.helper.CustomerBuilder;
import com.cognizant.helper.CustomerList;
import com.mce.contracts.CustomerServiceInterface;


public class CustomerServiceRestAdapter extends BaseClass implements CustomerServiceInterface {
	private final String baseUrl = "http://localhost:8080/server/api/customer";
	

	public BaseResponse findCustomerById(Long id) {
		Customer customer  = new Customer();
		BufferedReader br;
		
		try {
			URL url = new URL(String.format("%s/find/%d", baseUrl, id));
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Accept", "application/xml");
			for (StatusCode s : StatusCode.values())			
			if (httpConnection.getResponseCode() == s.getCode()) {
				switch(s){
				case OK:
					break;
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
		
		
		return new BaseResponse(customer, 200);
				
	}


	private HttpURLConnection makeGetRequest(String urlString){
		HttpURLConnection httpConnection = null;
		try {
			URL url = new URL(urlString);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Accept", "application/xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpConnection;
	}
	
	private CustomerList getAllCustomers(HttpURLConnection httpConnection){
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
	public BaseResponse fetchAllCustomers() {
		
		String url = String.format("%s/list", baseUrl);
		HttpURLConnection httpConnection = makeGetRequest(url);
		
		for (StatusCode s : StatusCode.values())	{		
			try {
				if (httpConnection.getResponseCode() == s.getCode()) {
					switch(s){
					case OK:
						return new BaseResponse(getAllCustomers(httpConnection), 200);
					case NotFound:
						return new BaseResponse(404);
					case InternalServerError:
						return new BaseResponse(500);
					default :
						break;
						}			
					}
			} catch (IOException e) {
			e.printStackTrace();
			}
			}
		return new BaseResponse(500);
	}

	public BaseResponse saveCustomer(JAXBElement<Customer> customer) {
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
		
		return new BaseResponse(cus, 200);
	}

	public BaseResponse deleteCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return new BaseResponse(200);
	}

	public BaseResponse updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return new BaseResponse(200);
	}

}
