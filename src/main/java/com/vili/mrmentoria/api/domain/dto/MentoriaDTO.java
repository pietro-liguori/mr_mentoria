package com.vili.mrmentoria.api.domain.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vili.mrmentoria.api.domain.DefinicaoMentoria;
import com.vili.mrmentoria.api.domain.Desconto;
import com.vili.mrmentoria.api.domain.Mentoria;
import com.vili.mrmentoria.api.domain.Pessoa;
import com.vili.mrmentoria.api.domain.enums.StatusMentoria;
import com.vili.mrmentoria.api.validation.constraints.ValidMentoria;
import com.vili.mrmentoria.engine.IEntity;

@ValidMentoria
public class MentoriaDTO extends DataTransferObject {

	private String descricao;
	private Integer status;
	private Long definicaoId;
	private Long mentorId;
	private Set<PessoaDTO> mentorados = new HashSet<>();
	private Set<DescontoDTO> descontos = new HashSet<>();

	public MentoriaDTO() {
		super();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getMentorId() {
		return mentorId;
	}

	public void setMentorId(Long mentorId) {
		this.mentorId = mentorId;
	}

	public List<PessoaDTO> getMentorados() {
		return mentorados.stream().toList();
	}

	public void setMentorados(Set<PessoaDTO> mentorados) {
		this.mentorados = mentorados;
	}

	public Long getDefinicaoId() {
		return definicaoId;
	}

	public void setDefinicao(Long definicaoId) {
		this.definicaoId = definicaoId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<DescontoDTO> getDescontos() {
		return descontos.stream().toList();
	}

	public void setDescontos(Set<DescontoDTO> descontos) {
		this.descontos = descontos;
	}

	@Override
	public IEntity toEntity() {
		StatusMentoria status = StatusMentoria.toEnum(getStatus());
		Mentoria mentoria = new Mentoria(getId(), new Pessoa(getMentorId()), getDescricao(), new DefinicaoMentoria(getDefinicaoId()), status);
		this.mentorados.forEach(x -> mentoria.addMentorado((Pessoa) x.toEntity()));
		this.descontos.forEach(x -> mentoria.addDesconto((Desconto) x.toEntity()));
		return mentoria;
	}
}
