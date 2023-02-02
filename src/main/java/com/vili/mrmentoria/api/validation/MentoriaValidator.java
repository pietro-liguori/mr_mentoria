package com.vili.mrmentoria.api.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.vili.mrmentoria.api.domain.Mentoria;
import com.vili.mrmentoria.api.domain.Pessoa;
import com.vili.mrmentoria.api.domain.dto.MentoriaDTO;
import com.vili.mrmentoria.api.domain.dto.PessoaDTO;
import com.vili.mrmentoria.api.domain.enums.PerfilPessoa;
import com.vili.mrmentoria.api.domain.enums.StatusMentoria;
import com.vili.mrmentoria.api.repositories.DefinicaoMentoriaRepository;
import com.vili.mrmentoria.api.repositories.MentoriaRepository;
import com.vili.mrmentoria.api.repositories.PessoaRepository;
import com.vili.mrmentoria.api.validation.constraints.ValidMentoria;
import com.vili.mrmentoria.engine.exceptions.FieldMessage;
import com.vili.mrmentoria.engine.validation.IValidator;

public class MentoriaValidator extends Validator<Mentoria> implements ConstraintValidator<ValidMentoria, MentoriaDTO> {

	@Autowired
	private MentoriaRepository mentoriaRepository;

	@Autowired
	private DefinicaoMentoriaRepository definicaoMentoriaRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public boolean isValid(MentoriaDTO dto, ConstraintValidatorContext context) {
		clear();
		setContext(context);
		addErrors(validate(dto));
		return buildConstraintViolations();
	}
	
	public static List<FieldMessage> validate(MentoriaDTO dto) {
		MentoriaValidator validator = new MentoriaValidator();
		validator.entityId("id", dto.getId(), validator.mentoriaRepository, dto.isUpdate());
		validator.length("descricao", dto.getDescricao(), 1, 144, dto.isInsert());
		validator.enumValue("status", dto.getStatus(), StatusMentoria.class, dto.isInsert());
		validator.entityId("definicaoId", dto.getDefinicaoId(), validator.definicaoMentoriaRepository,  dto.isInsert());
		Pessoa mentor = (Pessoa) validator.entityId("mentorId", dto.getDefinicaoId(), validator.pessoaRepository,  dto.isInsert());
		
		if (mentor != null) {
			if (!mentor.getPerfis().contains(PerfilPessoa.MENTOR.getCode()))
				validator.addError(new FieldMessage("mentor.perfis[]", "Must have a Perfil '" + PerfilPessoa.MENTOR.getLabel() + "'"));
		}
		
		if (validator.min("mentorados", dto.getMentorados(), 1, dto.isInsert())) {
			for (int i = 0; i < dto.getMentorados().size(); i++) {
				PessoaDTO mentoradoDTO = dto.getMentorados().get(i);
				List<FieldMessage> mentoradoErrors = IValidator.handleErrors(PessoaValidator.validate(mentoradoDTO), "mentorados[" + i + "]");
				validator.addErrors(mentoradoErrors);
				
				if (mentoradoErrors.size() > 0)
					continue;
					
				if (mentoradoDTO.isInsert()) {
					if (!mentoradoDTO.getPerfis().contains(PerfilPessoa.MENTORADO.getCode()))
							validator.addError(new FieldMessage("mentorados[" + i + "].perfis[]", "Must have a Perfil '" + PerfilPessoa.MENTORADO.getLabel() + "'"));
				} else {
					Pessoa mentorado = (Pessoa) validator.entityId("mentorados[" + i + "].perfis[]", mentoradoDTO.getId(), validator.pessoaRepository, true);
					
					if (!mentorado.getPerfis().contains(PerfilPessoa.MENTORADO.getCode()))
						validator.addError(new FieldMessage("mentorados[" + i + "].perfis[]", "Must have a Perfil '" + PerfilPessoa.MENTORADO.getLabel() + "'"));
				}
			}
		}

		return validator.getErrors();
	}
}
