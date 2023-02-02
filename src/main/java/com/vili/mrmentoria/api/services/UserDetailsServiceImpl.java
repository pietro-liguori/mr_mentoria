package com.vili.mrmentoria.api.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vili.mrmentoria.api.domain.Pessoa;
import com.vili.mrmentoria.api.domain.enums.PerfilPessoa;
import com.vili.mrmentoria.api.repositories.PessoaRepository;
import com.vili.mrmentoria.api.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	PessoaRepository pessoaRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Pessoa pessoa = new Pessoa();
		pessoa.setEmail(email);
		Optional<Pessoa> aux = pessoaRepo.findOne(Example.of(pessoa, ExampleMatcher.matching().withIgnoreCase()));
		
		if (aux.isEmpty()) {
			throw new UsernameNotFoundException(email);
		}
		
		pessoa = aux.get();
		Set<PerfilPessoa> perfis = pessoa.getPerfis().stream().map(perfil -> PerfilPessoa.toEnum(perfil)).collect(Collectors.toSet());
		return new UserSS(pessoa.getId(), pessoa.getEmail(), pessoa.getSenha(), perfis);
	}

}
