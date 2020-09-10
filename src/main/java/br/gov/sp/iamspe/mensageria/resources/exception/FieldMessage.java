package br.gov.sp.iamspe.mensageria.resources.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Atributos
	private String nome;

	private String mensagem;
}