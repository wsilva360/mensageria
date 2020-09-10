package br.gov.sp.iamspe.mensageria.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * ConfigProperties
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "conf")
@Getter
@Setter
public class PropertiesConfig {

    private Long idApi;

    private String urlMaxxMobbiEnvio;

    private String urlMaxxMobbiRetorno;

    private String urlMaxxMobbiStatus;

    private String urlMaxxMobbiCancelamento;

    private String urlGuardian;

    private String urlGuardianToken;

    private String smsUsuario;

    private String smsSenha;
    
    private String smsToken;

    private String urlSentinela;

    private String jobTimeRetorno;

    private String jobTimeStatus;
}