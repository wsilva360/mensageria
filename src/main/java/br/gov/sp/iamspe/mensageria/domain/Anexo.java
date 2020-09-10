package br.gov.sp.iamspe.mensageria.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "MENSAGERIA_EMAIL_ANEXO")
public class Anexo {
    
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ")
	@SequenceGenerator(sequenceName = "seq_mensageria_email_anexo", allocationSize = 1, name = "CUST_SEQ")
    private Long idAnexo;

    private String nome;

    private byte[] arquivo;

    @ManyToOne
    @JoinColumn(name = "ID_EMAIL")
    private Email email;
    
}
