package br.gov.sp.iamspe.mensageria.resources;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.iamspe.mensageria.domain.Email;
import br.gov.sp.iamspe.mensageria.dto.DefaultDTO;
import br.gov.sp.iamspe.mensageria.dto.EmailDTO;
import br.gov.sp.iamspe.mensageria.resources.exception.StandardError;
import br.gov.sp.iamspe.mensageria.services.EmailService;
import br.gov.sp.iamspe.mensageria.services.exception.SentinelaException;
import lombok.extern.slf4j.Slf4j;

/**
 * MensagemResource
 */

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/email")
public class EmailResource {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper mapper;

    // Métodos
    @PostMapping
    public ResponseEntity<?> email(@Valid @RequestBody EmailDTO dto) {
        log.debug("[EMAILRESOURCE]-[POST]");
        try {
            Email e = this.emailService.sendHtmlEmailMessage(mapper.map(dto, Email.class));
            
            dto = mapper.map(e, EmailDTO.class);

            dto.getAnexoList().forEach(arquivo -> {
                arquivo.setArquivo(null);
            });
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            throw new SentinelaException(new StandardError(e.getMessage(), e, e.getClass().getCanonicalName()));
        }
    }

    @GetMapping
    public ResponseEntity<?> email() {
        log.debug("[EMAILRESOURCE]-[GET]");
        return ResponseEntity.ok(new DefaultDTO(0, "OK"));
    }
}