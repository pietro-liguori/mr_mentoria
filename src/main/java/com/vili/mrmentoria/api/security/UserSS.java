package com.vili.mrmentoria.api.security;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vili.mrmentoria.api.domain.enums.PerfilPessoa;
import com.vili.mrmentoria.engine.IEntity;

public class UserSS implements IEntity, UserDetails {

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

	private String email;

	private String senha;

	private Collection<? extends GrantedAuthority> authorities;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public UserSS() {
		init();
	}

	public UserSS(Long id) {
		init();
		setId(id);
	}

	public UserSS(Long id, String email, String senha, Set<PerfilPessoa> perfis) {
		init();
		setId(id);
		this.email = email;
		this.senha = senha;
		this.authorities = perfis.stream().map(perfil -> new SimpleGrantedAuthority(perfil.getLabel())).toList();
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

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean hasRole(PerfilPessoa perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getLabel()));
	}
}
