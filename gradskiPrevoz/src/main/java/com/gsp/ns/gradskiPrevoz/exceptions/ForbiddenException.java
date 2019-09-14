package com.gsp.ns.gradskiPrevoz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException{

	/**
	 * 
	 */
	public ForbiddenException(){
		
	}
	public ForbiddenException(String msg){
		super(msg);
	}
	private static final long serialVersionUID = 1L;

}