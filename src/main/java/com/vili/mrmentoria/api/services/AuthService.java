package com.vili.mrmentoria.api.services;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vili.mrmentoria.api.domain.Pessoa;
import com.vili.mrmentoria.engine.exceptions.custom.ResourceNotFoundException;

@Service
public class AuthService {

	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		List<Pessoa> lista = pessoaService.findAll().stream().map(ent -> (Pessoa) ent).filter(pessoa -> pessoa.getEmail().equals(email)).toList();
		
		if (lista.size() == 0) {
			throw new ResourceNotFoundException("Pessoa com email : " + email + ", não encontrada");
		}
		
		String newPass = newPassword();
		Pessoa pessoa = lista.get(0);
		pessoa.setSenha(encoder.encode(newPass));
		
		pessoaService.save(pessoa);
		emailService.sendNewPasswordEmail(pessoa, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		
		if (opt == 0)
			return (char) (rand.nextInt(10) + 48);
		
		if (opt == 1)
			return (char) (rand.nextInt(26) + 65);
		
		if (opt == 2)
			return (char) (rand.nextInt(26) + 97);

		return 0;
	}
}
