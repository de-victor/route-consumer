package br.com.greenmile.desafio.exception;

public class CoordinateDeliveryNotFoundException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	
	public CoordinateDeliveryNotFoundException(String msg) {
		super(msg);
	}

}
