package br.gov.sp.iamspe.mensageria.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.gov.sp.iamspe.mensageria.services.validation.EmailValidation;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EmailValidation
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos
    @EqualsAndHashCode.Include
    private Long idEmail;
    
    @NotEmpty(message = "Para não pode ser nulo ou vazio.")
    private List<String> para = new ArrayList<>();

    private List<String> cc = new ArrayList<>();

    private List<String> cco = new ArrayList<>();

    //private Boolean snIndividual;
    @JsonFormat(pattern = "dd/MM/yyyy H:mm:ss", locale = "pt-BR", timezone = "Brazil/East")
    private Date dataAgendamento;

    @NotBlank(message = "Assunto não pode ser nulo ou vazio.")
    private String assunto;

    private String mensagem;

    private String template;
    
    @NotNull(message = "IdApi não pode ser nulo.")
    private Long idApi;
    
    private char status;
    
    private Map<String, Object> conteudo = new HashMap<>();

    private List<AnexoDto> anexoList = new ArrayList<>();
}
