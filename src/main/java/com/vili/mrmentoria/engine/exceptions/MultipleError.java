package com.vili.mrmentoria.engine.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "message" })
public class MultipleError extends StandardError {
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();
	
	public MultipleError(Instant timestamp, Integer status, String error, String path) {
		super(timestamp, status, error, null, path);
	}

	public MultipleError(Instant timestamp, Integer status, String error, String path, List<FieldMessage> messages) {
		super(timestamp, status, error, null, path);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void add(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}

	public void add(FieldMessage message) {
		errors.add(message);
	}

}
