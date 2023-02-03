package com.vili.mrmentoria.api.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.vili.mrmentoria.api.domain.Custo;
import com.vili.mrmentoria.api.domain.Mentoria;
import com.vili.mrmentoria.api.domain.dto.CustoDTO;
import com.vili.mrmentoria.api.domain.enums.StatusPagamento;
import com.vili.mrmentoria.api.domain.enums.TipoCusto;
import com.vili.mrmentoria.api.validation.constraints.ValidCusto;
import com.vili.mrmentoria.engine.exceptions.FieldMessage;
import com.vili.mrmentoria.engine.validation.IValidator;

public class CustoValidator extends Validator<Custo> implements ConstraintValidator<ValidCusto, CustoDTO> {

	@Override
	public boolean isValid(CustoDTO dto, ConstraintValidatorContext context) {
		clear();
		setContext(context);
		addErrors(validate(dto));
		return buildConstraintViolations();
	}
	
	public static List<FieldMessage> validate(CustoDTO dto) {
		CustoValidator validator = new CustoValidator();
		validator.entityId("id", dto.getId(), IValidator.getRepository(Custo.class), dto.isUpdate());
		validator.notEmpty("descricao", dto.getDescricao(), false);
		validator.notEmpty("data", dto.getData(), dto.isInsert());
		validator.positiveOrZero("valor", dto.getValor(), dto.isInsert());
		validator.notEmpty("observacao", dto.getObservacao(), false);
		validator.enumValue("tipo", dto.getTipo(), TipoCusto.class, dto.isInsert());
		validator.enumValue("status", dto.getStatus(), StatusPagamento.class, dto.isInsert());
		validator.entityIds("mentoriasIds", dto.getMentoriasIds(), IValidator.getRepository(Mentoria.class), dto.isInsert());
		return validator.getErrors();
	}
}
