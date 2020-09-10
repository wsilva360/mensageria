package br.gov.sp.iamspe.mensageria.services;

import br.gov.sp.iamspe.mensageria.domain.ItSms;
import br.gov.sp.iamspe.mensageria.domain.Sms;

/**
 * EnvioSmsService
 */
public interface SmsService extends AcaoService {

    public Sms insert(Sms sms);

    public Sms update(Sms sms);

    public ItSms insert(ItSms itSms);

    public ItSms update(ItSms itSms);

}