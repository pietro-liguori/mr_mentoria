package com.vili.mrmentoria.engine.exceptions.custom;

public class AuthorizationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public AuthorizationException(String msg) {
		super(msg);
	}

}
