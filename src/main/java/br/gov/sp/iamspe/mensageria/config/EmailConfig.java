package br.gov.sp.iamspe.mensageria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.gov.sp.iamspe.mensageria.services.EmailService;
import br.gov.sp.iamspe.mensageria.services.impl.EmailServiceImpl;

@Configuration
public class EmailConfig {
    
    @Bean
    public EmailService emailService() {
        return new EmailServiceImpl();
    }
}