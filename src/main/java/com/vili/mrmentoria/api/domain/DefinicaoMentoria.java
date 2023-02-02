package com.vili.mrmentoria.api.domain;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vili.mrmentoria.api.domain.enums.TipoMentoria;
import com.vili.mrmentoria.engine.IEntity;

@Entity
@JsonPropertyOrder({ "id", "nome", "descricao", "tipo", "valor", "numeroDeReunioes", "duracaoDaReuniao", "maxMentorados", "permitePresencial", "acessoComunidade", "encontroMentorados" })
public class DefinicaoMentoria implements IEntity {

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
	private String nome;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String descricao;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer numeroDeReunioes;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer duracaoDaReuniao;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer unidadeDuracao;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer maxMentorados;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Double valor;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer tipo;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean permitePresencial;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean acessoComunidade;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean encontroMentorados;

	public DefinicaoMentoria() {
		init();
	}

	public DefinicaoMentoria(Long id) {
		init();
		setId(id);
	}

	public DefinicaoMentoria(Long id, String nome, String descricao, Integer numeroDeReunioes, Integer duracaDaReuniao, Integer unidadeDuracao, Integer maxMentorados, Double valor, TipoMentoria tipo, Boolean permitePresencial, Boolean acessoComunidade, Boolean encontroMentorados) {
		init();
		setId(id);
		setNome(nome);
		setDescricao(descricao);
		setNumeroDeReunioes(numeroDeReunioes);
		setDuracaoDaReuniao(duracaDaReuniao);
		setUnidadeDuracao(unidadeDuracao);
		setMaxMentorados(maxMentorados);
		setValor(valor);
		setTipo(tipo);
		setPermitePresencial(permitePresencial);
		setAcessoComunidade(acessoComunidade);
		setEncontroMentorados(encontroMentorados);
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(TipoMentoria tipo) {
		this.tipo = tipo == null ? null : tipo.getCode();
	}

	public Boolean isPermitePresencial() {
		return permitePresencial;
	}

	public void setPermitePresencial(Boolean permitePresencial) {
		this.permitePresencial = permitePresencial;
	}

	public Boolean isAcessoComunidade() {
		return acessoComunidade;
	}

	public void setAcessoComunidade(Boolean acessoComunidade) {
		this.acessoComunidade = acessoComunidade;
	}

	public Boolean isEncontroMentorados() {
		return encontroMentorados;
	}

	public void setEncontroMentorados(Boolean encontroMentorados) {
		this.encontroMentorados = encontroMentorados;
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
