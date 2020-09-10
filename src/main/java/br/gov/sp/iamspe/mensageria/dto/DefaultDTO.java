package br.gov.sp.iamspe.mensageria.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DefaultDTO implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NonNull
    private Integer status;

    @NonNull
    private String message;

    @JsonFormat(pattern = "dd/MM/yyyy H:mm:ss", locale = "pt-BR", timezone = "Brazil/East")
    private Date data = new Date();
}