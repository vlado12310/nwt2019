package com.gsp.ns.gradskiPrevoz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException{

	/**
	 * 
	 */
	public ConflictException(){
		
	}
	public ConflictException(String msg){
		super(msg);
	}
	private static final long serialVersionUID = 1L;

}
