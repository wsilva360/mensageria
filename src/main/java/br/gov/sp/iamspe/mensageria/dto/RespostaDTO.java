package br.gov.sp.iamspe.mensageria.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Requisicao
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespostaDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String mensagem;
    
    private String seuId;

    @JsonFormat(pattern = "yyyy-MM-dd H:mm:ss", locale = "pt-BR", timezone = "Brazil/East")
    private Date data;

    private String numero;
}