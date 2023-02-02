package com.vili.mrmentoria.api.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidatorContext;

import com.vili.mrmentoria.engine.IEntity;
import com.vili.mrmentoria.engine.exceptions.FieldMessage;
import com.vili.mrmentoria.engine.validation.IValidator;

public abstract class Validator<E extends IEntity> implements IValidator<E> {

	private ConstraintValidatorContext context;
	private List<FieldMessage> errors = new ArrayList<>();

	@Override
	public ConstraintValidatorContext getContext() {
		return context;
	}

	@Override
	public void setContext(ConstraintValidatorContext context) {
		this.context = context;
	}

	@Override
	public List<FieldMessage> getErrors() {
		return errors;
	}
	
	public void clear() {
		errors = new ArrayList<>();
	}
}
