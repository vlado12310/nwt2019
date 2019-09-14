package com.gsp.ns.gradskiPrevoz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class NoFundsException extends RuntimeException{

	/**
	 * 
	 */
	public NoFundsException(){
		super("Nemate doboljno sredstava za kupovinu ove karte!");
	}
	private static final long serialVersionUID = 1L;

}
