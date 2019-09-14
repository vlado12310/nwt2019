package com.gsp.ns.gradskiPrevoz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class UserAlreadyExists extends RuntimeException {

	/**
	 * 
	 */
	public UserAlreadyExists(){
		  super("Email allready in use");
	  }
	private static final long serialVersionUID = 1L;

}
