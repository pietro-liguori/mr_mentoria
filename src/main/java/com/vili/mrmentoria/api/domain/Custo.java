package com.vili.mrmentoria.api.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vili.mrmentoria.api.domain.enums.StatusPagamento;
import com.vili.mrmentoria.api.domain.enums.TipoCusto;
import com.vili.mrmentoria.engine.IEntity;
import com.vili.mrmentoria.engine.request.annotations.FilterSetting;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonPropertyOrder({ "id", "descricao", "valor", "data", "tipo", "status", "observacao", "mentorias" })
public class Custo implements IEntity {

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
	private Date data;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String observacao;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer tipo;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer status;

	@ManyToMany
	@JoinTable(name = "custos_mentorias", joinColumns = @JoinColumn(name = "custo_id"), inverseJoinColumns = @JoinColumn(name = "mentoria_id"))
	@FilterSetting(nesting = { "id" }, alias = "mentoria")
	@JsonIgnoreProperties({ "reunioes", "pagamentos", "descontos", "definicao", "custos" })
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Set<Mentoria> mentorias = new HashSet<>();

	public Custo() {
		init();
	}

	public <T extends IEntity> Custo(Long id) {
		init();
		setId(id);
	}

	public <T extends IEntity> Custo(Long id, String descricao, Double valor, Date data, String observacao, TipoCusto tipo, StatusPagamento status, Mentoria... mentorias) {
		init();
		setId(id);
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
		this.observacao = observacao;
		this.tipo = tipo == null ? null : tipo.getCode();
		this.status = status == null ? null : status.getCode();
		
		for (Mentoria mentoria : mentorias) {
			addMentoria(mentoria);
		}
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

	public Date getData() {
		return data;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(TipoCusto tipo) {
		this.tipo = tipo == null ? null : tipo.getCode();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(StatusPagamento status) {
		this.status = status == null ? null : status.getCode();
	}

	public List<Mentoria> getMentorias() {
		return mentorias.stream().toList();
	}

	public void setMentorias(Set<Mentoria> mentorias) {
		this.mentorias = mentorias;
	}

	public void addMentoria(Mentoria mentoria) {
		this.mentorias.add(mentoria);
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
