package br.gov.sp.iamspe.mensageria.resources.exception;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.http.HttpStatus;

import br.gov.sp.iamspe.mensageria.utils.Utils;
import lombok.Data;
import lombok.NonNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;

	// Atributos
	private Integer status = HttpStatus.BAD_REQUEST.value();

	@NonNull
	private String message;

	@NonNull
	private String trace;

	private Integer tipoLog;

	private String tipoObjeto;
	
	@NonNull
	private String nomeObjeto;

	private Map<String, Long> api = new HashMap<>();

	@JsonFormat(pattern = "dd/MM/yyyy H:mm:ss", locale = "pt-BR", timezone = "Brazil/East")
	private Date dataLog;

	public StandardError(String message, Exception erro, String nomeObjeto) {
		this.message = message;
		this.trace = Utils.getStrackTrace(erro);
		this.nomeObjeto = nomeObjeto;
	}
}