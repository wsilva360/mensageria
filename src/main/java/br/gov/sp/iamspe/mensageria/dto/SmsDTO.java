package br.gov.sp.iamspe.mensageria.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.stereotype.Component;

import br.gov.sp.iamspe.mensageria.services.validation.SmsValidation;
import lombok.Data;

/**
 * EnvioSmsDTO
 */
@Data
@Component
@SmsValidation
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "acao não pode ser nulo.")
    private String acao;

    @NotNull(message = "idApi não pode ser nulo.")
    private Long idApi;

    @JsonFormat(pattern = "dd/MM/yyyy H:mm:ss", locale = "pt-BR", timezone = "Brazil/East")
    private Date agendamento;

    private String status;

    private String resposta;

    private Long lote;

    private Set<RequisicaoDTO> requisicao = new HashSet<RequisicaoDTO>();
    
}