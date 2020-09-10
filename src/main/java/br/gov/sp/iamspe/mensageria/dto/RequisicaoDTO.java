package br.gov.sp.iamspe.mensageria.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Requisicao
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequisicaoDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private Long id_itMsg;

    private String idCliente;

    private String numero;

    private String mensagem;
    
    private String data;

}