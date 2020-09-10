package br.gov.sp.iamspe.mensageria.resources.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

import br.gov.sp.iamspe.mensageria.config.PropertiesConfig;
import br.gov.sp.iamspe.mensageria.services.SentinelaService;
import br.gov.sp.iamspe.mensageria.services.exception.ObjectNotFoundException;
import br.gov.sp.iamspe.mensageria.services.exception.SentinelaException;
import br.gov.sp.iamspe.mensageria.services.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ResourceExceptionHandler {

	// Atributos
	@Autowired
	private SentinelaService sentinelaService;

	@Autowired
	private PropertiesConfig properties;

	// Métodos
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError(e.getMessage(), e, e.getStackTrace()[0].getClassName());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(),
				"Erro de validação. Total de erros: " + e.getBindingResult().getFieldErrors().size());

		for (FieldError erro : e.getBindingResult().getFieldErrors()) {
			err.putErro(erro.getField(), erro.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ValidationError> validation(HttpMessageNotReadableException e, HttpServletRequest request) {
		log.info("[INFO][RESOURCE EXCEPTION HANDLER] - [HttpMessageNotReadableException]: {}", e);

		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação");

		err.putErro(e.getMessage().substring(e.getMessage().lastIndexOf("[") + 2, e.getMessage().lastIndexOf("\"")),
				"Tipo de dado incorreto.");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(HttpMessageNotWritableException.class)
	public ResponseEntity<StandardError> serverError(HttpMessageNotWritableException e, HttpServletRequest request) {
		log.info("[INFO][RESOURCE EXCEPTION HANDLER] - [HttpMessageNotWritableException]: {}");

		StandardError err = new StandardError(e.getMessage(), e, e.getRootCause().getClass().getName());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
	}

	@ExceptionHandler(SentinelaException.class)
	public ResponseEntity<StandardError> serverError(SentinelaException err, HttpServletRequest request) {
		log.debug("[RESOURCE_EXCEPTION_HANDLER] - [serverError]");

		try {

			StandardError se = err.getStandardError();
			Map<String, Long> api = new HashMap<>();
			api.put("idApi", this.properties.getIdApi());

			se.setDataLog(new Date());
			se.setApi(api);
			se.setStatus(HttpStatus.BAD_REQUEST.value());
			se.setTipoLog(1);
			se.setTipoObjeto("Classe");

			log.debug("[RESOURCE_EXCEPTION_HANDLER] - [serverError] Response:"
					+ this.sentinelaService.saveLog(se).value());

		} catch (RestClientException ex) {
			log.debug("[RESOURCE_EXCEPTION_HANDLER][EXCEPTION]:");

		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getStandardError());

	}

	@ExceptionHandler(ServiceException.class)
	public void serverError(ServiceException err) {
		log.debug("[RESOURCE_EXCEPTION_HANDLER] - [serverError]");

		try {

			StandardError se = err.getStandardError();
			Map<String, Long> api = new HashMap<>();
			api.put("idApi", this.properties.getIdApi());

			se.setDataLog(new Date());
			se.setApi(api);
			se.setStatus(HttpStatus.BAD_REQUEST.value());
			se.setTipoLog(1);
			se.setTipoObjeto("Classe");

			log.debug("[RESOURCE_EXCEPTION_HANDLER] - [serverError] Response:"
					+ this.sentinelaService.saveLog(se).value());

		} catch (RestClientException ex) {
			log.debug("[RESOURCE_EXCEPTION_HANDLER][EXCEPTION]:");
			ex.printStackTrace();
		}
	}

}