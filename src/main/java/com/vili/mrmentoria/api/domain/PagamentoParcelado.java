package com.vili.mrmentoria.api.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vili.mrmentoria.api.domain.enums.MetodoPagamento;
import com.vili.mrmentoria.api.domain.enums.StatusPagamento;
import com.vili.mrmentoria.api.domain.enums.TipoPagamento;
import com.vili.mrmentoria.engine.request.annotations.NoFilter;

@Entity
@JsonPropertyOrder({ "id", "descricao", "valor", "entrada", "dataEntrada", "numeroDeParcelas", "parcelas", "metodo", "tipo", "status", "observacao", "descontos", "mentorias" })
public class PagamentoParcelado extends Pagamento {

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Double entrada;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Date dataEntrada;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Integer numeroDeParcelas;
	
	@OneToMany(mappedBy = "pagamento")
	@NoFilter
	@JsonIgnoreProperties({ "pagamento" })
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Set<Parcela> parcelas = new HashSet<>();

	public PagamentoParcelado() {
		super();
	}

	public PagamentoParcelado(Long id) {
		super(id);
	}

	public PagamentoParcelado(Long id, String descricao, Double valor, Integer numeroDeParcelas, Double entrada, Date dataEntrada, String observacao, MetodoPagamento metodo, TipoPagamento tipo, StatusPagamento status, Mentoria... mentorias) {
		super(id, descricao, valor, observacao, metodo, tipo, status, mentorias);
		setNumeroDeParcelas(numeroDeParcelas);
		setEntrada(entrada);
		setDataEntrada(dataEntrada);
	}

	public Double getEntrada() {
		return entrada;
	}

	public void setEntrada(Double entrada) {
		this.entrada = entrada;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public List<Parcela> getParcelas() {
		return parcelas.stream().toList();
	}

	public void setParcelas(Set<Parcela> parcelas) {
		this.parcelas = parcelas;
	}
	
	public void addParcela(Parcela parcela) {
		this.parcelas.add(parcela);
	}
}
