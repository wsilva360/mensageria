package br.gov.sp.iamspe.mensageria.services.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.gov.sp.iamspe.mensageria.config.PropertiesConfig;
import br.gov.sp.iamspe.mensageria.domain.Api;
import br.gov.sp.iamspe.mensageria.domain.ItSms;
import br.gov.sp.iamspe.mensageria.domain.Sms;
import br.gov.sp.iamspe.mensageria.dto.MaxxMobiEnvioDTO;
import br.gov.sp.iamspe.mensageria.dto.MaxxMobiRetornoDTO;
import br.gov.sp.iamspe.mensageria.dto.RequisicaoDTO;
import br.gov.sp.iamspe.mensageria.dto.SmsDTO;
import br.gov.sp.iamspe.mensageria.resources.exception.StandardError;
import br.gov.sp.iamspe.mensageria.services.ApiService;
import br.gov.sp.iamspe.mensageria.services.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SendSmsServiceImpl extends AbstractSmsService {

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    ApiService as;

    @Autowired
    private RestTemplate restTemplate;

    // Metodos
    @Override
    public MaxxMobiRetornoDTO sendSmsMessage(SmsDTO dto) {

        log.debug("[SendServiceImpl][sendMessage]" + dto);

        try {

            Sms resp = insert(this.preparaInsert(dto));

            MaxxMobiEnvioDTO envio = this.preparaEnvio(resp);

            MaxxMobiRetornoDTO retorno = this.envia(envio);

            resp.setResposta(retorno.getResposta());
            resp.setLote(retorno.getLote());
            resp.setStatus("T");
            resp.setDataIntegracao(new Date());

            update(resp);

            return retorno;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(new StandardError(e.getMessage(), e, e.getClass().getCanonicalName()));
        }

    }

    private Sms preparaInsert(SmsDTO dto) {
        log.debug("[SendServiceImpl][preparaInsert]");

        Api api = this.as.getApiById(dto.getIdApi());

        Sms sms = new Sms();

        sms.setApi(api);
        sms.setAcao(dto.getAcao().toUpperCase());
        sms.setAgendamento(dto.getAgendamento());

        dto.getRequisicao().forEach(item -> {
            ItSms itSms = new ItSms();
            itSms.setIdMensagem(api.getIdApi() + "" +item.getIdCliente());
            itSms.setStatusRequest("S");
            itSms.setMensagem(item.getMensagem());
            itSms.setNumero(item.getNumero());
            itSms.setStatus("P");

            itSms.setSms(sms);

            sms.getRequisicao().add(itSms);
        });

        return sms;
    }

    private MaxxMobiEnvioDTO preparaEnvio(Sms sms) {
        log.debug("[SendServiceImpl][preparaEnvio]");

        MaxxMobiEnvioDTO env = new MaxxMobiEnvioDTO();
        env.setUsuario(propertiesConfig.getSmsUsuario());
        env.setSenha(propertiesConfig.getSmsSenha());
        env.setAgendamento(sms.getAgendamento());

        sms.getRequisicao().forEach(e -> {
            RequisicaoDTO it = new RequisicaoDTO();
            it.setIdCliente(StringUtils.isNotBlank(e.getIdMensagem()) ? e.getIdMensagem()
                    : String.valueOf(e.getIdItMaxxMobi()));
            it.setNumero(e.getNumero());
            it.setMensagem(e.getMensagem());
            env.getRequisicao().add(it);
        });
        return env;
    }

    private MaxxMobiRetornoDTO envia(MaxxMobiEnvioDTO envio) throws RestClientException {
        log.debug("[SendServiceImpl][envia]");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> httpEntity = new HttpEntity<Object>(envio, requestHeaders);

        return restTemplate.exchange(this.propertiesConfig.getUrlMaxxMobbiEnvio(), HttpMethod.POST, httpEntity,
                MaxxMobiRetornoDTO.class).getBody();
    }
}