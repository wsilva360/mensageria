package br.gov.sp.iamspe.mensageria.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.gov.sp.iamspe.mensageria.domain.Email;

public interface EmailService {

	/**
	 * Prepara a entidade para ser enviada por email simple
	 * @param email
	 */
	void sendEmailMessage(Email email);

	/**
	 * Envia mensagem sem template por email
	 * @param msg
	 */
	void sendEmail(SimpleMailMessage msg);

	/**
	 * Prepara a entidade para ser enviada por email HTML
	 * @param email Entidade para persistencia em bando e envio da mensagem
	 * @return entidade persistida e enviada
	 */
	Email sendHtmlEmailMessage(Email email);

	/**
	 * Envia a mensagem HTML por email
	 */
	void sendHtmlEmail(MimeMessage msg);

	/**
	 * Salva mensagem na base de dados
	 * @param email entidade para persistencia em banco
	 * @return entidade salva
	 */
	Email insert(Email email);
	
	/**
	 * Atualiza mensagem na base de dados
	 * @param email entidade para persistencia em banco
	 * @return entidade salva
	 */
	Email update(Email email);
}
