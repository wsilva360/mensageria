package br.gov.sp.iamspe.mensageria.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.sp.iamspe.mensageria.domain.Api;
import br.gov.sp.iamspe.mensageria.repositories.ApiRepository;
import br.gov.sp.iamspe.mensageria.services.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiService {
    
    @Autowired
    private ApiRepository ar;

    public Api getApiById(Long id){
        log.debug("[ApiRepository][getApiById]");
        return this.ar.findById(id).orElseThrow(()-> new ObjectNotFoundException("API " + id + " não encontrada."));
    }
}