package com.vili.mrmentoria.api.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.vili.mrmentoria.api.domain.Custo;
import com.vili.mrmentoria.api.domain.dto.CustoDTO;
import com.vili.mrmentoria.api.domain.enums.StatusPagamento;
import com.vili.mrmentoria.api.domain.enums.TipoCusto;
import com.vili.mrmentoria.api.repositories.CustoRepository;
import com.vili.mrmentoria.api.repositories.MentoriaRepository;
import com.vili.mrmentoria.api.validation.constraints.ValidCusto;
import com.vili.mrmentoria.engine.exceptions.FieldMessage;

public class CustoValidator extends Validator<Custo> implements ConstraintValidator<ValidCusto, CustoDTO> {

	@Autowired
	private MentoriaRepository mentoriaRepository;
	
	@Autowired
	private CustoRepository custoRepository;

	@Override
	public boolean isValid(CustoDTO dto, ConstraintValidatorContext context) {
		clear();
		setContext(context);
		addErrors(validate(dto));
		return buildConstraintViolations();
	}
	
	public static List<FieldMessage> validate(CustoDTO dto) {
		CustoValidator validator = new CustoValidator();
		validator.entityId("id", dto.getId(), validator.custoRepository, dto.isUpdate());
		validator.notEmpty("descricao", dto.getDescricao(), false);
		validator.notEmpty("data", dto.getData(), dto.isInsert());
		validator.positiveOrZero("valor", dto.getValor(), dto.isInsert());
		validator.notEmpty("observacao", dto.getObservacao(), false);
		validator.enumValue("tipo", dto.getTipo(), TipoCusto.class, dto.isInsert());
		validator.enumValue("status", dto.getStatus(), StatusPagamento.class, dto.isInsert());
		validator.entityIds("mentoriasIds", dto.getMentoriasIds(), validator.mentoriaRepository, dto.isInsert());
		return validator.getErrors();
	}
}
