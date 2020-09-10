package br.gov.sp.iamspe.mensageria.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.ToString;

/**
 * EnvioSmsDTO
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MaxxMobiRetornoDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;

    private String seuId;

    private Long lote;

    private String numero;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date dataEnvio;

    private String mensagem;

    private String status;

    private String resposta;

    @ToString.Exclude
    private Set<RespostaDTO> listaResposta = new HashSet<RespostaDTO>(); 

        
}