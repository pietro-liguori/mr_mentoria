package com.vili.mrmentoria.api.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.vili.mrmentoria.api.domain.Reuniao;
import com.vili.mrmentoria.api.domain.dto.ReuniaoDTO;
import com.vili.mrmentoria.api.domain.enums.StatusReuniao;
import com.vili.mrmentoria.api.domain.enums.TipoReuniao;
import com.vili.mrmentoria.api.repositories.MentoriaRepository;
import com.vili.mrmentoria.api.repositories.ReuniaoRepository;
import com.vili.mrmentoria.api.validation.constraints.ValidReuniao;
import com.vili.mrmentoria.engine.exceptions.FieldMessage;

public class ReuniaoValidator extends Validator<Reuniao> implements ConstraintValidator<ValidReuniao, ReuniaoDTO> {

	@Autowired
	private MentoriaRepository mentoriaRepository;

	@Autowired
	private ReuniaoRepository reuniaoRepository;

	@Override
	public boolean isValid(ReuniaoDTO dto, ConstraintValidatorContext context) {
		clear();
		setContext(context);
		addErrors(validate(dto));
		return buildConstraintViolations();
	}
	
	public static List<FieldMessage> validate(ReuniaoDTO dto) {
		ReuniaoValidator validator = new ReuniaoValidator();
		validator.entityId("id", dto.getId(), validator.reuniaoRepository, dto.isUpdate());
		validator.length("descricao", dto.getDescricao(), 1, 144, dto.isInsert());
		validator.length("local", dto.getLocal(), 1, 144, dto.isInsert());
		validator.notEmpty("data", dto.getData(), dto.isInsert());
		validator.enumValue("tipo", dto.getTipo(), TipoReuniao.class, dto.isInsert());
		validator.enumValue("status", dto.getStatus(), StatusReuniao.class, dto.isInsert());
		validator.entityId("mentoriaId", dto.getMentoriaId(), validator.mentoriaRepository, dto.isInsert());
		return validator.getErrors();
	}
}
