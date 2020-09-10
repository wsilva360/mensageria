package br.gov.sp.iamspe.mensageria.services.impl;

import br.gov.sp.iamspe.mensageria.services.SmsService;

public enum AcaoEnumImpl {
    SEND (new SendSmsServiceImpl());
    
    private SmsService sms;

    private AcaoEnumImpl(SmsService sms) {
        this.sms = sms;
    }

    public SmsService getSms() {
        return sms;
    }

    public void setSms(SmsService sms) {
        this.sms = sms;
    }
    
}