package com.vili.mrmentoria.engine.exceptions.custom;

import com.vili.mrmentoria.engine.request.QueryOperator;

public class OperationNotSupportedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public OperationNotSupportedException(QueryOperator op) {
		super("Operation not supported yet: " + op);
	}
}