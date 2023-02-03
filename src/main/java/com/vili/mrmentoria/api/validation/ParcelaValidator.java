package com.vili.mrmentoria.api.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.vili.mrmentoria.api.domain.Parcela;
import com.vili.mrmentoria.api.domain.dto.ParcelaDTO;
import com.vili.mrmentoria.api.domain.enums.StatusPagamento;
import com.vili.mrmentoria.api.repositories.PagamentoParceladoRepository;
import com.vili.mrmentoria.api.validation.constraints.ValidParcela;
import com.vili.mrmentoria.engine.exceptions.FieldMessage;
import com.vili.mrmentoria.engine.validation.IValidator;

public class ParcelaValidator extends Validator<Parcela> implements ConstraintValidator<ValidParcela, ParcelaDTO> {

	@Autowired
	private PagamentoParceladoRepository pagamentoParceladoRepository;

	@Override
	public boolean isValid(ParcelaDTO dto, ConstraintValidatorContext context) {
		clear();
		setContext(context);
		addErrors(validate(dto));
		return buildConstraintViolations();
	}
	
	public static List<FieldMessage> validate(ParcelaDTO dto) {
		ParcelaValidator validator = new ParcelaValidator();
		validator.entityId("id", dto.getId(), IValidator.getRepository(Parcela.class), dto.isUpdate());
		validator.notEmpty("valor", dto.getValor(), dto.isInsert());
		validator.notEmpty("data", dto.getData(), dto.isInsert());
		validator.enumValue("status", dto.getStatus(), StatusPagamento.class, dto.isInsert());
		validator.entityId("pagamentoId", dto.getPagamentoId(), validator.pagamentoParceladoRepository, dto.isInsert());
		return validator.getErrors();
	}
}
