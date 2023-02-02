package com.vili.mrmentoria.engine.exceptions.custom;

public class CastingException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CastingException(String msg) {
		super(msg);
	}
}