package com.vili.mrmentoria.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.vili.mrmentoria.api.services.DBService;
import com.vili.mrmentoria.api.services.EmailService;
import com.vili.mrmentoria.api.services.MockEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instanciateDatabase() throws ParseException {
		if (!strategy.startsWith("create"))
			return false;
					
		dbService.instanciateDatabase();
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
