package com.victoramaral.projetomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.victoramaral.projetomc.services.DBService;
import com.victoramaral.projetomc.services.EmailService;
import com.victoramaral.projetomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dBService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dBService.instantiateTestDatabase();
		return true;
	}
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
