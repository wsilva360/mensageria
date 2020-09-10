package br.gov.sp.iamspe.mensageria.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.iamspe.mensageria.domain.ItSms;
import br.gov.sp.iamspe.mensageria.domain.Sms;
import br.gov.sp.iamspe.mensageria.repositories.ItSmsRepository;
import br.gov.sp.iamspe.mensageria.repositories.SmsRepository;
import br.gov.sp.iamspe.mensageria.services.SmsService;
import lombok.extern.slf4j.Slf4j;

/**
 * SmsServiceImpl
 */
@Slf4j
@Service
public abstract class AbstractSmsService implements SmsService {

    @Autowired
    private SmsRepository smsRepository;

    @Autowired
    private ItSmsRepository itSmsRepository;

    @Transactional
    @Override
    public Sms insert(Sms sms) {
        log.debug("[SmsServiceImpl][insert]");
        sms.setIdMsgMaxxMobi(null);
        return this.smsRepository.save(sms);
    }

    @Override
    public Sms update(Sms obj) {
        log.debug("[SmsServiceImpl][update]");
        return this.smsRepository.save(obj);
    }

    @Transactional
    @Override
    public ItSms insert(ItSms itSms) {
        log.debug("[SmsServiceImpl][insert]");
        itSms.setIdItMaxxMobi(null);
        return this.itSmsRepository.save(itSms);
    }

    @Override
    public ItSms update(ItSms itSms) {
        log.debug("[SmsServiceImpl][update]");
        return this.itSmsRepository.save(itSms);
    }
}