package com.hotel.mvc.exceptions;

public class NegocioException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NegocioException(String mesagem) {
		super(mesagem);
	}

}
