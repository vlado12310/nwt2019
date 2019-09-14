package com.gsp.ns.gradskiPrevoz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class InvalidParametersException extends RuntimeException{

	
	  public InvalidParametersException(String msg){
		  super(msg);
	  }
	  public InvalidParametersException(){
		  
	  }
	private static final long serialVersionUID = 1L;

}
