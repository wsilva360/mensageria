package br.gov.sp.iamspe.mensageria.services.impl;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.gov.sp.iamspe.mensageria.domain.Anexo;
import br.gov.sp.iamspe.mensageria.domain.Email;
import br.gov.sp.iamspe.mensageria.services.EmailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractEmailService implements EmailService {

	@Autowired
	TemplateEngine templateEngine;

	@Autowired
	JavaMailSender javaMailSender;

	@Override
	public void sendEmailMessage(Email emsg) {
		log.debug("[AbstractEmailService][sendEmailMessage]");
		SimpleMailMessage sm = prepareSimpleMailMessage(emsg);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessage(Email email) {
		log.debug("[AbstractEmailService][prepareSimpleMailMessage]");
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(email.getPara().split(","));
		sm.setFrom("noreply@iamspe.sp.gov.br");
		sm.setSubject(email.getAssunto());
		sm.setSentDate(new Date(System.currentTimeMillis()));// data do email
		sm.setText(email.getMensagem());
		return sm;
	}

	/**
	 * Envia email e atualiza o staus da mensagem na base de dados
	 */
	public Email sendHtmlEmailMessage(Email email) {
		log.debug("[AbstractEmailService][sendHtmlEmailMessage]");
		Email em = email;
		email.getAnexoList().forEach(e -> {
			e.setEmail(em);
		});
		try {

			email = insert(email);

			MimeMessage mm = prepareMimeMessage(email);
			sendHtmlEmail(mm);
			email.setStatus('C');
			update(email);
			return email;
		} catch (Exception e) {
			email.setStatus('E');
			update(email);
			throw new RuntimeException(e.getMessage());
		}
	}

	protected MimeMessage prepareMimeMessage(Email email) throws MessagingException {
		log.debug("[AbstractEmailService][prepareMimeMessage]");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(email.getPara().split(","));

		if (!email.getCc().isEmpty()) {
			mmh.setCc(email.getCc().split(","));
		}

		if (!email.getCco().isEmpty()) {
			mmh.setBcc(email.getCco().split(","));
		}

		mmh.setFrom("noreply@iamspe.sp.gov.br");
		mmh.setSubject(email.getAssunto());
		mmh.setSentDate(new Date());// data do email

		if (StringUtils.isNotBlank(email.getTemplate())) {
			mmh.setText(htmlFromTemplate(email.getTemplate(), email.getConteudo()), true);
		} else {
			String conteudo = "{\"conteudo\":" + email.getMensagem() + "}";
			mmh.setText(htmlFromTemplate("default", conteudo), true);
		}

		if (email.getAnexoList().size() > 0) {
			for (Anexo anexo : email.getAnexoList()) {
				log.debug("Adicionando anexo: " + anexo.getNome());
				mmh.addAttachment(anexo.getNome(), new ByteArrayResource(anexo.getArquivo()));// adicona anexo
			}
		}

		return mimeMessage;
	}

	protected String htmlFromTemplate(String template, String obj) {
		log.debug("[AbstractEmailService][htmlFromTemplate] " + template + " data " + obj);
		Context context = new Context();

		context.setVariable("param", new JSONObject(obj.toString()).toMap());

		return templateEngine.process("email/" + template, context);
	}

}
