package com.vili.mrmentoria.engine.exceptions.custom;

public class EnumValueNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EnumValueNotFoundException(Object value, String field, Class<?> enumClass) {
		super("Enum value not found for " + field + " = " + value + " on " + enumClass.getSimpleName());
	}

	public EnumValueNotFoundException(String msg) {
		super(msg);
	}
}