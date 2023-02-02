package com.vili.mrmentoria.api.services;

import org.springframework.mail.SimpleMailMessage;

import com.vili.mrmentoria.api.domain.Mentoria;
import com.vili.mrmentoria.api.domain.Pessoa;

public interface EmailService {

	void sendEmail(SimpleMailMessage msg);
	
	void sendBoasVindas(Mentoria mentoria);
	
	void sendNewPasswordEmail(Pessoa pessoa, String newPass);
}
