package com.vili.mrmentoria.api.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vili.mrmentoria.api.domain.enums.DetalhePessoa;
import com.vili.mrmentoria.api.domain.enums.PerfilPessoa;
import com.vili.mrmentoria.api.domain.enums.StatusPessoa;
import com.vili.mrmentoria.api.domain.enums.TipoPessoa;
import com.vili.mrmentoria.engine.IEntity;
import com.vili.mrmentoria.engine.request.annotations.FilterSetting;

@Entity
@JsonPropertyOrder({ "id", "nome", "sobrenome", "email", "tipo", "perfil", "status", "detalhe", "telefones" })
public class Pessoa implements IEntity {

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
	private String sobrenome;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String email;
	
	@JsonIgnore
	private String senha;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer tipo;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer status;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer detalhe;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "perfil")
	@FilterSetting(alias = "perfil")
	private Set<Integer> perfis = new HashSet<>();

	@ElementCollection
	@CollectionTable(name = "telefone")
	@FilterSetting(alias = "telefone")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Set<String> telefones = new HashSet<>();

	public Pessoa() {
		init();
	}

	public Pessoa(Long id) {
		init();
		setId(id);
	}

	public Pessoa(Long id, String nome, String sobrenome, String email, String senha, TipoPessoa tipo, StatusPessoa status, DetalhePessoa detalhe, PerfilPessoa... perfis) {
		init();
		setId(id);
		setNome(nome);
		setSobrenome(sobrenome);
		setEmail(email);
		setSenha(senha);
		setTipo(tipo);
		setStatus(status);
		setDetalhe(detalhe);
		setPerfis(Stream.of(perfis).collect(Collectors.toSet()));
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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(TipoPessoa tipo) {
		this.tipo = tipo == null ? null : tipo.getCode();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(StatusPessoa status) {
		this.status = status == null ? null : status.getCode();
	}

	public Integer getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(DetalhePessoa detalhe) {
		this.detalhe = detalhe == null ? null : detalhe.getCode();
	}

	public List<Integer> getPerfis() {
		return perfis.stream().toList();
	}

	public void setPerfis(Set<PerfilPessoa> perfis) {
		Set<Integer> ret = new HashSet<>();
		
		for (PerfilPessoa perfil : perfis) {
			if (perfil != null)
				ret.add(perfil.getCode());
		}
		
		this.perfis = ret;
	}

	public void addPerfil(PerfilPessoa perfil) {
		if (perfil != null)
			this.perfis.add(perfil.getCode());
	}

	public List<String> getTelefones() {
		return telefones.stream().toList();
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	public void addTelefone(String telefone) {
		this.telefones.add(telefone);
	}
	
	@JsonIgnore
	public String getNomeCompleto() {
		return getNome() + " " + getSobrenome();
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
