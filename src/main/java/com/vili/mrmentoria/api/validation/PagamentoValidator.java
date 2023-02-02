package com.vili.mrmentoria.api.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.vili.mrmentoria.api.domain.Pagamento;
import com.vili.mrmentoria.api.domain.dto.PagamentoDTO;
import com.vili.mrmentoria.api.domain.enums.MetodoPagamento;
import com.vili.mrmentoria.api.domain.enums.StatusPagamento;
import com.vili.mrmentoria.api.domain.enums.TipoPagamento;
import com.vili.mrmentoria.api.repositories.MentoriaRepository;
import com.vili.mrmentoria.api.repositories.PagamentoRepository;
import com.vili.mrmentoria.api.validation.constraints.ValidPagamento;
import com.vili.mrmentoria.engine.exceptions.FieldMessage;

public class PagamentoValidator extends Validator<Pagamento> implements ConstraintValidator<ValidPagamento, PagamentoDTO> {

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private MentoriaRepository mentoriaRepository;

	@Override
	public boolean isValid(PagamentoDTO dto, ConstraintValidatorContext context) {
		clear();
		setContext(context);
		addErrors(validate(dto));
		return buildConstraintViolations();
	}
	
	public static List<FieldMessage> validate(PagamentoDTO dto) {
		PagamentoValidator validator = new PagamentoValidator();
		validator.entityId("id", dto.getId(), validator.pagamentoRepository, dto.isUpdate());
		validator.positiveOrZero("valor", dto.getValor(), dto.isInsert());
		validator.notEmpty("descricao", dto.getDescricao(), dto.isInsert());
		validator.enumValue("metodo", dto.getMetodo(), MetodoPagamento.class, dto.isInsert());
		validator.enumValue("status", dto.getStatus(), StatusPagamento.class, dto.isInsert());
		validator.notEmpty("observacao", dto.getObservacao(), false);
		validator.entityIds("mentoriasIds", dto.getMentoriasIds(), validator.mentoriaRepository, dto.isInsert());

		if (validator.enumValue("tipo", dto.getTipo(), TipoPagamento.class, dto.isInsert())) {
			if (dto.getTipo().equals(TipoPagamento.A_VISTA.getCode())) {
				validator.notEmpty("data", dto.getData(), dto.isInsert());
			} else if (dto.getTipo().equals(TipoPagamento.PARCELADO.getCode())){
				validator.positive("numeroDeParcelas", dto.getNumeroDeParcelas(), dto.isInsert());
				validator.notEmpty("dataEntrada", dto.getDataEntrada(), false);
				validator.positive("entrada", dto.getEntrada(), false);
			}
		}
		
		return validator.getErrors();
	}
}
