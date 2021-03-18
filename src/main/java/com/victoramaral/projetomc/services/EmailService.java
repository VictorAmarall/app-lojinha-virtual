package com.victoramaral.projetomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.victoramaral.projetomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg) ;

}
