package com.vili.mrmentoria.api.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.vili.mrmentoria.api.domain.Desconto;
import com.vili.mrmentoria.api.domain.dto.DescontoDTO;
import com.vili.mrmentoria.api.domain.enums.TipoDesconto;
import com.vili.mrmentoria.api.validation.constraints.ValidDesconto;
import com.vili.mrmentoria.engine.exceptions.FieldMessage;
import com.vili.mrmentoria.engine.validation.IValidator;

public class DescontoValidator extends Validator<Desconto> implements ConstraintValidator<ValidDesconto, DescontoDTO> {

	@Override
	public boolean isValid(DescontoDTO dto, ConstraintValidatorContext context) {
		clear();
		setContext(context);
		addErrors(validate(dto));
		return buildConstraintViolations();
	}
	
	public static List<FieldMessage> validate(DescontoDTO dto) {
		DescontoValidator validator = new DescontoValidator();
		validator.entityId("id", dto.getId(), IValidator.getRepository(Desconto.class), dto.isUpdate());
		validator.length("descricao", dto.getDescricao(), 1, 144, dto.isInsert());
		validator.notEmpty("valor", dto.getValor(), dto.isInsert());
		validator.enumValue("tipo", dto.getTipo(), TipoDesconto.class, dto.isInsert());
		validator.notNull("absoluto", dto.isAbsoluto(), dto.isInsert());
		return validator.getErrors();
	}
}
