package com.cognizant.adapter;

public class BaseClass {
	
	enum StatusCode {
		   OK(200),Created(201),BadRequest(400),NotFound(404),InternalServerError(500);
		   private int code;
		   StatusCode(int p) {
		      code = p;
		   }
		   int getCode() {
		      return code;
		   } 
		}
	
}
