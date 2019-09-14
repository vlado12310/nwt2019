package com.gsp.ns.gradskiPrevoz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	public ResourceNotFoundException(String msg){
		super(msg);
	}
	public ResourceNotFoundException(){
		super("404 not found.");
	}
	private static final long serialVersionUID = 1L;

}
