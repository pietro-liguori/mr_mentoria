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
import com.vili.mrmentoria.api.domain.enums.TipoDesconto;
import com.vili.mrmentoria.engine.IEntity;
import com.vili.mrmentoria.engine.request.annotations.FilterSetting;

@Entity
@JsonPropertyOrder({ "id", "descricao", "valor", "absoluto", "tipo", "mentoria" })
public class Desconto implements IEntity {

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
	private String descricao;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Double valor;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean absoluto;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer tipo;

	@ManyToOne
	@JoinColumn(name = "mentoria_id")
	@FilterSetting(nesting = { "id" }, alias = "mentoria")
	@JsonIgnoreProperties({ "reunioes", "pagamentos", "descontos", "definicao", "custos" })
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Mentoria mentoria;

	public Desconto() {
		init();
	}

	public Desconto(Long id) {
		init();
		setId(id);
	}

	public Desconto(Long id, Mentoria mentoria, String descricao, Double valor, Boolean absoluto, TipoDesconto tipo) {
		init();
		setId(id);
		setMentoria(mentoria);
		setDescricao(descricao);
		setValor(valor);
		setAbsoluto(absoluto);
		setTipo(tipo);
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Boolean isAbsoluto() {
		return absoluto;
	}

	public void setAbsoluto(Boolean absoluto) {
		this.absoluto = absoluto;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(TipoDesconto tipo) {
		this.tipo = tipo == null ? null : tipo.getCode();
	}

	public Mentoria getMentoria() {
		return mentoria;
	}

	public void setMentoria(Mentoria mentoria) {
		this.mentoria = mentoria;
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
