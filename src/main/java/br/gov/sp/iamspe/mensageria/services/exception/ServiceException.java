package br.gov.sp.iamspe.mensageria.services.exception;

import br.gov.sp.iamspe.mensageria.resources.exception.StandardError;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	
	// Atributos
	private StandardError standardError;
	
	
	// Construtores
	public ServiceException(String message) {
		super(message);

	}
	public ServiceException(StandardError standardError) {
		this.standardError = standardError;
	}

	
	// Getters e Setters
	public StandardError getStandardError() {
		return standardError;
	}
	
}