package br.gov.sp.iamspe.mensageria.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.gov.sp.iamspe.mensageria.config.PropertiesConfig;
import br.gov.sp.iamspe.mensageria.domain.Api;
import br.gov.sp.iamspe.mensageria.domain.Sms;
import br.gov.sp.iamspe.mensageria.dto.MaxxMobiEnvioDTO;
import br.gov.sp.iamspe.mensageria.dto.MaxxMobiRetornoDTO;
import br.gov.sp.iamspe.mensageria.dto.SmsDTO;
import br.gov.sp.iamspe.mensageria.resources.exception.StandardError;
import br.gov.sp.iamspe.mensageria.services.ApiService;
import br.gov.sp.iamspe.mensageria.services.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CancelSmsServiceImpl extends AbstractSmsService {

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    ApiService as;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public MaxxMobiRetornoDTO sendSmsMessage(SmsDTO sms) {
        log.debug("[CancelSmsServiceImpl][sendMessage]" + sms);

        Sms resp = new Sms();
        try {

            resp = insert(this.preparaInsert(sms));

            MaxxMobiEnvioDTO envio = this.preparaEnvio(resp);

            MaxxMobiRetornoDTO retorno = this.envia(envio);

            resp.setResposta(retorno.getResposta());
            resp.setStatus("T");
            resp.setDataIntegracao(new Date());

            update(resp);

            return retorno;

        } catch (Exception e) {
            if (resp.getIdMsgMaxxMobi() != null) {
                resp.setResposta("Erro ao cancelar");
                resp.setStatus("E");
                update(resp);
            }
            throw new ServiceException(new StandardError(e.getMessage(), e, e.getClass().getCanonicalName()));
        }
    }

    private Sms preparaInsert(SmsDTO dto) {
        log.debug("[CancelSmsServiceImpl][preparaInsert]");

        Api api = this.as.getApiById(dto.getIdApi());

        Sms sms = new Sms();

        sms.setApi(api);
        sms.setLote(dto.getLote());
        sms.setAcao(dto.getAcao().toUpperCase());
        return sms;
    }

    private MaxxMobiEnvioDTO preparaEnvio(Sms sms) {
        log.debug("[CancelSmsServiceImpl][preparaEnvio]");

        MaxxMobiEnvioDTO env = new MaxxMobiEnvioDTO();
        env.setUsuario(propertiesConfig.getSmsUsuario());
        env.setSenha(propertiesConfig.getSmsSenha());
        env.setId(String.valueOf(sms.getLote()));
        return env;
    }

    private MaxxMobiRetornoDTO envia(MaxxMobiEnvioDTO envio) {
        log.debug("[CancelSmsServiceImpl][envia]");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> httpEntity = new HttpEntity<Object>(envio, requestHeaders);

        return restTemplate.exchange(this.propertiesConfig.getUrlMaxxMobbiCancelamento(), HttpMethod.POST, httpEntity,
                MaxxMobiRetornoDTO.class).getBody();
    }
}