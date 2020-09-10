package br.gov.sp.iamspe.mensageria.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.gov.sp.iamspe.mensageria.config.PropertiesConfig;
import br.gov.sp.iamspe.mensageria.resources.exception.StandardError;
import br.gov.sp.iamspe.mensageria.services.SentinelaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SentinelaServiceImpl implements SentinelaService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PropertiesConfig properties;

    public HttpStatus saveLog(StandardError standardError) {
    log.debug("[SentinelaServiceImpl]-[saveLog]");

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(2000);
        factory.setReadTimeout(2000);
        this.restTemplate.setRequestFactory(factory);

        HttpEntity<StandardError> entity = new HttpEntity<>(standardError);
        ResponseEntity<String> response = this.restTemplate.exchange(this.properties.getUrlSentinela(), HttpMethod.POST,
                entity, String.class);
        return response.getStatusCode();
    }

}