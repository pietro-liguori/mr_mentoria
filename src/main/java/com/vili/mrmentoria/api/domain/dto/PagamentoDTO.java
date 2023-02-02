package com.vili.mrmentoria.api.domain.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vili.mrmentoria.api.domain.Mentoria;
import com.vili.mrmentoria.api.domain.PagamentoAVista;
import com.vili.mrmentoria.api.domain.PagamentoParcelado;
import com.vili.mrmentoria.api.domain.enums.MetodoPagamento;
import com.vili.mrmentoria.api.domain.enums.StatusPagamento;
import com.vili.mrmentoria.api.domain.enums.TipoPagamento;
import com.vili.mrmentoria.engine.IEntity;

public class PagamentoDTO extends DataTransferObject {

	private Double valor;
	private String descricao;
	private Integer metodo;
	private Integer status;
	private Integer tipo;
	private String observacao;
	private Date data;
	private Double entrada;
	private Date dataEntrada;
	private Integer numeroDeParcelas;
	private Set<Long> mentoriasIds = new HashSet<>();

	public PagamentoDTO() {
		super();
	}

	public Integer getMetodo() {
		return metodo;
	}

	public void setMetodo(Integer metodo) {
		this.metodo = metodo;
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

	public String getObservacao() {
		return observacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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

	public Double getEntrada() {
		return entrada;
	}

	public void setEntrada(Double entrada) {
		this.entrada = entrada;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	@Override
	public IEntity toEntity() {
		StatusPagamento status = StatusPagamento.toEnum(getStatus());
		TipoPagamento tipo = TipoPagamento.toEnum(getTipo());
		MetodoPagamento metodo = MetodoPagamento.toEnum(getMetodo());
		
		if (tipo.equals(TipoPagamento.A_VISTA)) {
			PagamentoAVista pagamento = new PagamentoAVista(getId(), getDescricao(), getValor(), getData(), getObservacao(), metodo, tipo, status);
			getMentoriasIds().forEach(x -> pagamento.addMentoria(new Mentoria(x)));
			return pagamento;
		} else if (tipo.equals(TipoPagamento.PARCELADO)) {
			PagamentoParcelado pagamento = new PagamentoParcelado(getId(), getDescricao(), getValor(), getNumeroDeParcelas(), getEntrada(), getDataEntrada(), getObservacao(), metodo, tipo, status);
			getMentoriasIds().forEach(x -> pagamento.addMentoria(new Mentoria(x)));
			return pagamento;
		}

		return null;
	}
}
