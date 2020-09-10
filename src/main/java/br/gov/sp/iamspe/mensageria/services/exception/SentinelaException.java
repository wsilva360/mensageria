package br.gov.sp.iamspe.mensageria.services.exception;

import br.gov.sp.iamspe.mensageria.resources.exception.StandardError;

public class SentinelaException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	
	// Atributos
	private StandardError standardError;
	
	
	// Construtores
	public SentinelaException(String message) {
		super(message);

	}
	public SentinelaException(StandardError standardError) {
		this.standardError = standardError;
	}

	
	// Getters e Setters
	public StandardError getStandardError() {
		return standardError;
	}
	
}