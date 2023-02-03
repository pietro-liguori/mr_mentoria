package com.vili.mrmentoria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.vili.mrmentoria.api.services.EmailService;
import com.vili.mrmentoria.api.services.MockEmailService;

@Configuration
@Profile("prod")
public class ProdConfig {

	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
