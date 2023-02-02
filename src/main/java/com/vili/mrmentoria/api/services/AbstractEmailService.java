package com.vili.mrmentoria.api.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.vili.mrmentoria.api.domain.Mentoria;
import com.vili.mrmentoria.api.domain.Pessoa;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendBoasVindas(Mentoria mentoria) {
		List<Pessoa> mentorados = mentoria.getMentorados();
		mentorados.forEach(mentorado -> {
			SimpleMailMessage sm = new SimpleMailMessage();
			sm.setTo(mentorado.getEmail());
			sm.setFrom(sender);
			sm.setSubject("MR Mentoria - Boas vindas!");
			sm.setSentDate(new Date(System.currentTimeMillis()));
			sm.setText("Olá, " + mentorado.getNome() + "!\nSeja muito bem vindo à mentoria!");
			sendEmail(sm);
		});
	}
	
	@Override
	public void sendNewPasswordEmail(Pessoa pessoa, String newPass) {
		SimpleMailMessage sm =  new SimpleMailMessage();
		sm.setTo(pessoa.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setText("Nova senha: " + newPass);
		sendEmail(sm);
	}
}
