package com.vili.mrmentoria.api.domain;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vili.mrmentoria.api.domain.enums.StatusPagamento;
import com.vili.mrmentoria.engine.IEntity;
import com.vili.mrmentoria.engine.request.annotations.FilterSetting;

@Entity
@JsonPropertyOrder({ "id", "valor", "data", "pagamento" })
public class Parcela implements IEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	private Class<?> entityType;
	
	@JsonIgnore
	private Date createdAt;
	
	@JsonIgnore
	private Date updatedAt;
	
	@JsonIgnore
	private boolean active;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Double valor;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date data;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer status;

	@ManyToOne
	@JoinColumn(name = "pagamento_id")
	@FilterSetting(nesting = { "id" })
	@JsonIgnoreProperties({ "parcelas" })
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private PagamentoParcelado pagamento;

	public Parcela() {
		init();
	}

	public Parcela(Long id) {
		init();
		setId(id);
	}

	public Parcela(Long id, PagamentoParcelado pagamento, Double valor, Date data, StatusPagamento status) {
		init();
		setId(id);
		setPagamento(pagamento);
		setValor(valor);
		setData(data);
		setStatus(status);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Class<?> getEntityType() {
		return entityType;
	}

	@Override
	public void setEntityType(Class<?> entityType) {
		this.entityType = entityType;
	}

	@Override
	public Date getCreatedAt() {
		return createdAt;
	}

	@Override
	public void setCreatedAt(Date date) {
		this.createdAt = date;
	}
	
	@Override
	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public void setUpdatedAt(Date date) {
		this.updatedAt = date;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
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

	public void setStatus(StatusPagamento status) {
		this.status = status == null ? null : status.getCode();
	}

	public PagamentoParcelado getPagamento() {
		return pagamento;
	}

	public void setPagamento(PagamentoParcelado pagamento) {
		this.pagamento = pagamento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IEntity other = (IEntity) obj;
		return Objects.equals(getId(), other.getId());
	}
}
