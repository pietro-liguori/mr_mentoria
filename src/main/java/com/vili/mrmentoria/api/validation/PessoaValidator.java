package com.vili.mrmentoria.api.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import com.vili.mrmentoria.api.domain.Pessoa;
import com.vili.mrmentoria.api.domain.dto.PessoaDTO;
import com.vili.mrmentoria.api.domain.enums.DetalhePessoa;
import com.vili.mrmentoria.api.domain.enums.PerfilPessoa;
import com.vili.mrmentoria.api.domain.enums.StatusPessoa;
import com.vili.mrmentoria.api.domain.enums.TipoPessoa;
import com.vili.mrmentoria.api.repositories.PessoaRepository;
import com.vili.mrmentoria.api.validation.constraints.ValidPessoa;
import com.vili.mrmentoria.engine.exceptions.FieldMessage;

public class PessoaValidator extends Validator<Pessoa> implements ConstraintValidator<ValidPessoa, PessoaDTO> {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Override
	public boolean isValid(PessoaDTO dto, ConstraintValidatorContext context) {
		clear();
		setContext(context);
		addErrors(validate(dto));
		return buildConstraintViolations();
	}
	
	public static List<FieldMessage> validate(PessoaDTO dto) {
		PessoaValidator validator = new PessoaValidator();
		validator.entityId("id", dto.getId(), validator.pessoaRepository, dto.isUpdate());
		validator.length("nome", dto.getNome(), 3, 50, dto.isInsert());
		validator.length("sobrenome", dto.getSobrenome(), 3, 120, dto.isInsert());
		validator.length("senha", dto.getSobrenome(), 6, 20, dto.isInsert());
		validator.enumValue("tipo", dto.getTipo(), TipoPessoa.class, dto.isInsert());
		validator.enumValues("perfis", dto.getPerfis(), PerfilPessoa.class, dto.isInsert());
		validator.enumValue("status", dto.getStatus(), StatusPessoa.class, dto.isInsert());
		validator.enumValue("detalhe", dto.getDetalhe(), DetalhePessoa.class, dto.isInsert());
		validator.notEmpty("telefones", dto.getTelefones(), false);
		
		if (validator.email("email", dto.getEmail(), dto.isInsert())) {
			if (dto.isInsert()) {
				Pessoa probe = new Pessoa();
				probe.setEmail(dto.getEmail());
				validator.unique("email", Example.of(probe, ExampleMatcher.matching().withIgnoreCase()), validator.pessoaRepository, true);
			} else {
				Pessoa probe = new Pessoa();
				probe.setEmail(dto.getEmail());
				List<Pessoa> pessoas = validator.pessoaRepository.findAll(Example.of(probe, ExampleMatcher.matching().withIgnoreCase()));
				
				if (pessoas.size() > 1) {
					validator.addError(new FieldMessage("email", "Must be unique"));
				} else if (pessoas.size() == 1) {
					if (!pessoas.get(0).getId().equals(dto.getId()))
						validator.addError(new FieldMessage("email", "Must be unique"));
				}
			}
		}
		
		return validator.getErrors();
	}
}
