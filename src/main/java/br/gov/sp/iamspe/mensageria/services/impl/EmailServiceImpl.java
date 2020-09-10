package br.gov.sp.iamspe.mensageria.services.impl;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.gov.sp.iamspe.mensageria.domain.Email;
import br.gov.sp.iamspe.mensageria.repositories.AnexoRepository;
import br.gov.sp.iamspe.mensageria.repositories.EmailRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailServiceImpl extends AbstractEmailService {

	@Autowired
	MailSender mailSender;// Ao ser instanciada, pega as configurações de email do application.properties

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private EmailRepository repoEmail;

	@Autowired
	private AnexoRepository repoAnexo;

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		log.info("[SmtpEmailService][sendEmail]", msg);
		mailSender.send(msg);
		log.debug("Email enviado.");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) throws MailAuthenticationException {
		log.info("[SmtpEmailService][sendHtmlEmail]", msg);
		javaMailSender.send(msg);
		log.debug("Email enviado.");
	}

	@Override
	@Transactional
	public Email insert(Email email) {
		log.debug("[SmtpEmailService][save]");
		email.setIdEmail(null);
		email = repoEmail.save(email);
		
		this.repoAnexo.saveAll(email.getAnexoList());
		
		return email;
	}

	@Override
	@Transactional
	public Email update(Email email) {
		log.debug("[SmtpEmailService][update]");
		return repoEmail.save(email);
	}
}
