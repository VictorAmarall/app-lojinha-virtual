package com.victoramaral.projetomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.victoramaral.projetomc.domain.Cliente;
import com.victoramaral.projetomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg) ;
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void  sendHtmlEmail (MimeMessage  msg);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);

}
