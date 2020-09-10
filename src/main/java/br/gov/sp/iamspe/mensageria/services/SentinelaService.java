package br.gov.sp.iamspe.mensageria.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;

import br.gov.sp.iamspe.mensageria.resources.exception.StandardError;

public interface SentinelaService {
    
    /**
     * Envia erro para API Sentinela para monitoria
     * @param StandardError entidade com detalhes do erro
     * @return status da resposta
     */
    @PostMapping(value = "/sentinela")
	public HttpStatus saveLog (StandardError StandardError);
}