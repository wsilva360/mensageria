package br.gov.sp.iamspe.mensageria.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.iamspe.mensageria.dto.SmsDTO;
import br.gov.sp.iamspe.mensageria.resources.exception.StandardError;
import br.gov.sp.iamspe.mensageria.services.exception.SentinelaException;
import br.gov.sp.iamspe.mensageria.services.exception.ServiceException;
import br.gov.sp.iamspe.mensageria.services.impl.CancelSmsServiceImpl;
import br.gov.sp.iamspe.mensageria.services.impl.SendSmsServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * MensagemResource
 */
@Slf4j
@RestController
public class SmsResource {

    @Autowired
    private SendSmsServiceImpl sendSmsServiceImpl;

    @Autowired
    private CancelSmsServiceImpl cancelSmsServiceImpl;

    // Métodos
    @PostMapping(value = "/sms")
    public ResponseEntity<?> sms(@Valid @RequestBody SmsDTO dto) {

        log.debug("[SmsResource][sms]");

        try {

            // AcaoEnumImpl acao = Enum.valueOf(AcaoEnumImpl.class,
            // dto.getAcao().toUpperCase());
            // SmsService service = acao.getSms();
            // return ResponseEntity.ok().body(service.sendSmsMessage(dto));

            if (dto.getAcao().toUpperCase().equalsIgnoreCase("SEND")) {
                return ResponseEntity.ok().body(sendSmsServiceImpl.sendSmsMessage(dto));
            } else {
                return ResponseEntity.ok().body(cancelSmsServiceImpl.sendSmsMessage(dto));
            }

        } catch (ServiceException e) {
            throw new SentinelaException(e.getStandardError());
        } catch (Exception e) {
            throw new SentinelaException(new StandardError(e.getMessage(), e, e.getClass().getCanonicalName()));
        }

    }

    @GetMapping(value = "/sms")
    public ResponseEntity<?> getBodySms() {
        return ResponseEntity.ok().body(new SmsDTO());
    }

}