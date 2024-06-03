package com.tay.usermanager.exception;

public class ExistedUserException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExistedUserException(String message) {
		super(message);
	}

}
