package com.vili.mrmentoria.api.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vili.mrmentoria.api.domain.enums.MetodoPagamento;
import com.vili.mrmentoria.api.domain.enums.StatusPagamento;
import com.vili.mrmentoria.api.domain.enums.TipoPagamento;

@Entity
@JsonPropertyOrder({ "id", "descricao", "valor", "data", "metodo", "tipo", "status", "observacao", "descontos", "mentorias" })
public class PagamentoAVista extends Pagamento {

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date data;

	public PagamentoAVista() {
		super();
	}

	public PagamentoAVista(Long id) {
		super(id);
	}

	public PagamentoAVista(Long id, String descricao, Double valor, Date data, String observacao, MetodoPagamento metodo, TipoPagamento tipo, StatusPagamento status, Mentoria... mentorias) {
		super(id, descricao, valor, observacao, metodo, tipo, status, mentorias);
		setData(data);
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
