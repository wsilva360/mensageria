package br.gov.sp.iamspe.mensageria.jobs;

import java.util.Calendar;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.gov.sp.iamspe.mensageria.config.PropertiesConfig;
import br.gov.sp.iamspe.mensageria.domain.ItSms;
import br.gov.sp.iamspe.mensageria.domain.enums.MaxxMobiEnum;
import br.gov.sp.iamspe.mensageria.dto.MaxxMobiEnvioDTO;
import br.gov.sp.iamspe.mensageria.dto.MaxxMobiRetornoDTO;
import br.gov.sp.iamspe.mensageria.dto.SmsDTO;
import br.gov.sp.iamspe.mensageria.repositories.ItSmsRepository;
import br.gov.sp.iamspe.mensageria.resources.exception.StandardError;
import br.gov.sp.iamspe.mensageria.services.exception.ServiceException;
import br.gov.sp.iamspe.mensageria.services.impl.AbstractSmsService;
import lombok.extern.slf4j.Slf4j;

/**
 * SmsJobs - Responsavel por atualizar status das mensagem enviadas para
 * MaxxMobi, verificando se ja foram entregues aos usuarios e devolver respostas
 * enviadas pelos usuarios para as APIs de origem.
 */
@Slf4j
@Component
public class SmsJobs extends AbstractSmsService {

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    private ItSmsRepository itSmsRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RestTemplate restTemplate;

    /**
     * Verifica status das mensagens enviadas para MaxxMobi. Alterar tempo do job no
     * aquivo application.properties parametro "jobTimeStatus"
     */
    @Scheduled(fixedRateString = "${conf.jobTimeStatus}")
    protected void consultaStatus() {

        log.debug("[SmsJobs][consultaStatus]");
        try {

            List<ItSms> list = this.itSmsRepository.getByStatusP();

            log.debug("[SmsJobs][consultaStatus] Total de mensagens: " + list.size());

            list.forEach(itSms -> {

                log.debug("[SmsJobs][consultaStatus] Consultando status de: " + itSms.getIdMensagem());

                MaxxMobiEnvioDTO envio = new MaxxMobiEnvioDTO();
                envio.setUsuario(propertiesConfig.getSmsUsuario());
                envio.setSenha(propertiesConfig.getSmsSenha());
                envio.setId(itSms.getIdMensagem());

                MaxxMobiRetornoDTO resp = this.envia(this.propertiesConfig.getUrlMaxxMobbiStatus(), envio);

                if (resp.getId() != null) {
                    log.debug("[SmsJobs][consultaStatus] Atualizando base com informacoes");
                    itSms.setDataEvio(resp.getDataEnvio());
                    itSms.setStatus(MaxxMobiEnum.valueOf(resp.getStatus()).getStatus());
                    update(itSms);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(new StandardError(e.getMessage(), e, this.getClass().getName()));
        }

    }

    /**
     * Verifica se existe resposta para mensagens enviadas. Alterar tempo do job no
     * aquivo application.properties parametro "jobTimeRetorno"
     */

    @Scheduled(fixedRateString = "${conf.jobTimeRetorno}")
    protected void consultaRetorno() {

        log.debug("[SmsJobs][consultaRetorno]");

        MaxxMobiEnvioDTO envio = new MaxxMobiEnvioDTO();
        envio.setUsuario(propertiesConfig.getSmsUsuario());
        envio.setSenha(propertiesConfig.getSmsSenha());

        Calendar calendar = Calendar.getInstance();

        envio.setDataFim(calendar.getTime());

        calendar.add(Calendar.HOUR_OF_DAY, -1);

        envio.setDatainicio(calendar.getTime());

        try {
            MaxxMobiRetornoDTO mbRetorno = this.envia(propertiesConfig.getUrlMaxxMobbiRetorno(), envio);

            log.debug("[SmsJobs][consultaRetorno] Total respostas.: " + mbRetorno.getListaResposta().size());
            mbRetorno.getListaResposta().forEach(resp -> {
                if (!this.itSmsRepository.existsByIdResposta(resp.getId())) {
                    ItSms itSms = itSmsRepository.findByIdMensagem(resp.getSeuId());
                    itSms.setDataEvio(null);
                    itSms.setDataResposta(resp.getData());
                    itSms.setIdResposta(resp.getId());
                    itSms.setMensagem(resp.getMensagem());
                    itSms.setNumero(resp.getNumero());
                    itSms.setStatusRequest("E");
                    insert(itSms);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(new StandardError(e.getMessage(), e, this.getClass().getName()));
        }

    }

    private MaxxMobiRetornoDTO envia(String url, MaxxMobiEnvioDTO envio) throws RestClientException {
        log.debug("[SmsJobs][envia]");

        System.setProperty("proxyHost", "10.1.6.100");
        System.setProperty("proxyPort", "80");

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        // requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<?> httpEntity = new HttpEntity<Object>(envio, requestHeaders);

        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, MaxxMobiRetornoDTO.class).getBody();
    }

    @Override
    public MaxxMobiRetornoDTO sendSmsMessage(SmsDTO sms) {
        return null;
    }
}