package br.gov.sp.iamspe.mensageria.resources.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NonNull;

@Data
public class ValidationError implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	// Atributos
	@NonNull
	private Integer status;

	@NonNull
	private String erro;

	@JsonFormat(pattern = "dd/MM/yyyy H:mm:ss", locale = "pt-BR", timezone = "Brazil/East")
	private Date data = new Date();

	private List<FieldMessage> list = new ArrayList<>();

	public void putErro(String fieldName, String msg) {
		list.add(new FieldMessage(fieldName, msg));
	}
}