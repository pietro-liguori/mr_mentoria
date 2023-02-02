package com.vili.mrmentoria.api.domain.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vili.mrmentoria.api.domain.Pessoa;
import com.vili.mrmentoria.api.domain.enums.DetalhePessoa;
import com.vili.mrmentoria.api.domain.enums.PerfilPessoa;
import com.vili.mrmentoria.api.domain.enums.StatusPessoa;
import com.vili.mrmentoria.api.domain.enums.TipoPessoa;
import com.vili.mrmentoria.api.validation.constraints.ValidPessoa;
import com.vili.mrmentoria.engine.IEntity;

@ValidPessoa
public class PessoaDTO extends DataTransferObject {

	@Autowired
	BCryptPasswordEncoder be;
	
	private String nome;
	private String sobrenome;
	private String email;
	private String senha;
	private Integer tipo;
	private Integer status;
	private Integer detalhe;
	private Set<Integer> perfis = new HashSet<>();
	private Set<String> telefones = new HashSet<>();

	public PessoaDTO() {
		super();
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

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(Integer detalhe) {
		this.detalhe = detalhe;
	}

	public List<Integer> getPerfis() {
		return perfis.stream().toList();
	}

	public void setPerfis(Set<Integer> perfis) {
		this.perfis = perfis;
	}

	public List<String> getTelefones() {
		return telefones.stream().toList();
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	@Override
	public IEntity toEntity() {
		TipoPessoa tipo = TipoPessoa.toEnum(getTipo());
		StatusPessoa status = StatusPessoa.toEnum(getStatus());
		DetalhePessoa detalhe = DetalhePessoa.toEnum(getDetalhe());
		Pessoa person = new Pessoa(getId(), getNome(), getSobrenome(), getEmail(), getSenha() == null ? null : be.encode(getSenha()), tipo, status, detalhe);
		this.telefones.forEach(x -> person.addTelefone(x));
		this.perfis.forEach(perfil -> person.addPerfil(PerfilPessoa.toEnum(perfil)));
		return person;
	}
}
