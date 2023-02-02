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
import com.vili.mrmentoria.api.domain.enums.StatusReuniao;
import com.vili.mrmentoria.api.domain.enums.TipoReuniao;
import com.vili.mrmentoria.engine.IEntity;
import com.vili.mrmentoria.engine.request.annotations.FilterSetting;

@Entity
@JsonPropertyOrder({ "id", "descricao", "data", "local", "tipo", "status", "mentoria" })
public class Reuniao implements IEntity {

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
	private Date data;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String local;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer tipo;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer status;
	
	@ManyToOne
	@JoinColumn(name = "mentoria_id")
	@FilterSetting(nesting = { "id" })
	@JsonIgnoreProperties({ "reunioes", "pagamentos", "descontos", "definicao", "custos" })
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Mentoria mentoria;

	public Reuniao() {
		init();
	}

	public Reuniao(Long id) {
		init();
		setId(id);
	}

	public Reuniao(Long id, Mentoria mentoria, String descricao, Date data, String local, TipoReuniao tipo, StatusReuniao status) {
		init();
		setId(id);
		setMentoria(mentoria);
		setDescricao(descricao);
		setData(data);
		setLocal(local);
		setTipo(tipo);
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(TipoReuniao tipo) {
		this.tipo = tipo == null ? null : tipo.getCode();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(StatusReuniao status) {
		this.status = status == null ? null : status.getCode();
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
