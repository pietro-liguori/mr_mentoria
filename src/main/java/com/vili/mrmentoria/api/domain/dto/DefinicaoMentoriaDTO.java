package com.vili.mrmentoria.api.domain.dto;

import com.vili.mrmentoria.api.domain.DefinicaoMentoria;
import com.vili.mrmentoria.api.domain.enums.TipoMentoria;
import com.vili.mrmentoria.engine.IEntity;

public class DefinicaoMentoriaDTO extends DataTransferObject {

	private String nome;
	private String descricao;
	private Integer numeroDeReunioes;
	private Integer duracaoDaReuniao;
	private Integer unidadeDuracao;
	private Integer maxMentorados;
	private Integer tipo;
	private Double valor;
	private boolean permitePresencial;
	private boolean acessoComunidade;
	private boolean encontroMentorados;

	public DefinicaoMentoriaDTO() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getNumeroDeReunioes() {
		return numeroDeReunioes;
	}

	public void setNumeroDeReunioes(Integer numeroDeReunioes) {
		this.numeroDeReunioes = numeroDeReunioes;
	}

	public Integer getDuracaoDaReuniao() {
		return duracaoDaReuniao;
	}

	public void setDuracaoDaReuniao(Integer duracaoDaReuniao) {
		this.duracaoDaReuniao = duracaoDaReuniao;
	}

	public Integer getUnidadeDuracao() {
		return unidadeDuracao;
	}

	public void setUnidadeDuracao(Integer unidadeDuracao) {
		this.unidadeDuracao = unidadeDuracao;
	}

	public Integer getMaxMentorados() {
		return maxMentorados;
	}

	public void setMaxMentorados(Integer maxMentorados) {
		this.maxMentorados = maxMentorados;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public boolean isPermitePresencial() {
		return permitePresencial;
	}

	public void setPermitePresencial(boolean permitePresencial) {
		this.permitePresencial = permitePresencial;
	}

	public boolean isAcessoComunidade() {
		return acessoComunidade;
	}

	public void setAcessoComunidade(boolean acessoComunidade) {
		this.acessoComunidade = acessoComunidade;
	}

	public boolean isEncontroMentorados() {
		return encontroMentorados;
	}

	public void setEncontroMentorados(boolean encontroMentorados) {
		this.encontroMentorados = encontroMentorados;
	}

	@Override
	public IEntity toEntity() {
		TipoMentoria tipo = TipoMentoria.toEnum(getTipo());
		DefinicaoMentoria definicao = new DefinicaoMentoria(getId(), getNome(), getDescricao(), getNumeroDeReunioes(), getDuracaoDaReuniao(), getUnidadeDuracao(), getMaxMentorados(), getValor(), tipo, isPermitePresencial(), isAcessoComunidade(), isEncontroMentorados());
		return definicao;
	}
}
