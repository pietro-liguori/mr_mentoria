package com.vili.mrmentoria.api.domain.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vili.mrmentoria.api.domain.Custo;
import com.vili.mrmentoria.api.domain.Mentoria;
import com.vili.mrmentoria.api.domain.enums.StatusPagamento;
import com.vili.mrmentoria.api.domain.enums.TipoCusto;
import com.vili.mrmentoria.engine.IEntity;

public class CustoDTO extends DataTransferObject {

	private String descricao;
	private Double valor;
	private Date data;
	private String observacao;
	private Integer tipo;
	private Integer status;
	private Set<Long> mentoriasIds = new HashSet<>();

	public CustoDTO() {
		super();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Long> getMentoriasIds() {
		return mentoriasIds.stream().toList();
	}

	public void setMentoriasIds(Set<Long> mentoriasIds) {
		this.mentoriasIds = mentoriasIds;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public IEntity toEntity() {
		StatusPagamento status = StatusPagamento.toEnum(getStatus());
		TipoCusto tipo = TipoCusto.toEnum(getTipo());
		Custo custo = new Custo(getId(), getDescricao(), getValor(), getData(), getObservacao(), tipo, status);
		getMentoriasIds().forEach(x -> custo.addMentoria(new Mentoria(x)));
		return custo;
	}
}
