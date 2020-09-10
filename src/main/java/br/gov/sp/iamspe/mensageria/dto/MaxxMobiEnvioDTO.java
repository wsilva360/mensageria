package br.gov.sp.iamspe.mensageria.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.ToString;

/**
 * MaxxMobiDTO
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MaxxMobiEnvioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;

    private String senha;

    @JsonFormat(pattern = "dd-MM-yyyyy H:mm:ss", locale = "pt-BR", timezone = "Brazil/East")
    private Date agendamento;

    private String id;

    @JsonFormat(pattern = "dd-MM-yyyyy H:mm:ss", locale = "pt-BR", timezone = "Brazil/East")
    private Date datainicio;

    @JsonFormat(pattern = "dd-MM-yyyyy H:mm:ss", locale = "pt-BR", timezone = "Brazil/East")
    private Date dataFim;

    @ToString.Exclude
    private List<RequisicaoDTO> requisicao = new ArrayList<>();

}