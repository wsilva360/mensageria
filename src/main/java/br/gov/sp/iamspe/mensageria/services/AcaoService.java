package br.gov.sp.iamspe.mensageria.services;

import org.springframework.stereotype.Service;

import br.gov.sp.iamspe.mensageria.dto.MaxxMobiRetornoDTO;
import br.gov.sp.iamspe.mensageria.dto.SmsDTO;

@Service
public interface AcaoService {
    public MaxxMobiRetornoDTO sendSmsMessage(SmsDTO sms);
}