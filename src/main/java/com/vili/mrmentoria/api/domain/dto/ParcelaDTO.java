package com.vili.mrmentoria.api.domain.dto;

import java.util.Date;

import com.vili.mrmentoria.api.domain.PagamentoParcelado;
import com.vili.mrmentoria.api.domain.Parcela;
import com.vili.mrmentoria.api.domain.enums.StatusPagamento;
import com.vili.mrmentoria.engine.IEntity;

public class ParcelaDTO extends DataTransferObject {

	private Double valor;
	private Date data;
	private Integer status;
	private Long pagamentoId;

	public ParcelaDTO() {
		super();
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getPagamentoId() {
		return pagamentoId;
	}

	public void setPagamentoId(Long pagamentoId) {
		this.pagamentoId = pagamentoId;
	}

	@Override
	public IEntity toEntity() {
		StatusPagamento status = StatusPagamento.toEnum(getStatus());
		return new Parcela(getId(), new PagamentoParcelado(getPagamentoId()), getValor(), getData(), status);
	}
}
