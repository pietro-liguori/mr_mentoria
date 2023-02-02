package com.vili.mrmentoria.api.validation;

import java.util.Calendar;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.vili.mrmentoria.api.domain.DefinicaoMentoria;
import com.vili.mrmentoria.api.domain.dto.DefinicaoMentoriaDTO;
import com.vili.mrmentoria.api.domain.enums.TipoMentoria;
import com.vili.mrmentoria.api.repositories.DefinicaoMentoriaRepository;
import com.vili.mrmentoria.api.validation.constraints.ValidDefinicaoMentoria;
import com.vili.mrmentoria.engine.exceptions.FieldMessage;

public class DefinicaoMentoriaValidator extends Validator<DefinicaoMentoria> implements ConstraintValidator<ValidDefinicaoMentoria, DefinicaoMentoriaDTO> {

	@Autowired
	private DefinicaoMentoriaRepository definicaoMentoriaRepository;
	
	@Override
	public boolean isValid(DefinicaoMentoriaDTO dto, ConstraintValidatorContext context) {
		clear();
		setContext(context);
		addErrors(validate(dto));
		return buildConstraintViolations();
	}
	
	public static List<FieldMessage> validate(DefinicaoMentoriaDTO dto) {
		DefinicaoMentoriaValidator validator = new DefinicaoMentoriaValidator();
		validator.entityId("id", dto.getId(), validator.definicaoMentoriaRepository, dto.isUpdate());
		validator.length("descricao", dto.getDescricao(), 1, 144, dto.isInsert());
		validator.length("nome", dto.getNome(), 1, 144, dto.isInsert());
		validator.notEmpty("numeroDeReunioes", dto.getNumeroDeReunioes(), dto.isInsert());
		validator.notEmpty("duracaoDaReuniao", dto.getDuracaoDaReuniao(), dto.isInsert());
		validator.in("unidadeDuracao", dto.getUnidadeDuracao(), List.of(Calendar.HOUR, Calendar.MINUTE), dto.isInsert());
		validator.min("maxMentorados", dto.getMaxMentorados(), 1, dto.isInsert());
		validator.notEmpty("valor", dto.getValor(), dto.isInsert());
		validator.enumValue("tipo", dto.getTipo(), TipoMentoria.class, dto.isInsert());
		validator.notNull("permitePresencial", dto.isPermitePresencial(), dto.isInsert());
		validator.notNull("acessoComunidade", dto.isAcessoComunidade(), dto.isInsert());
		validator.notNull("encontroMentorados", dto.isEncontroMentorados(), dto.isInsert());
		return validator.getErrors();
	}
}
