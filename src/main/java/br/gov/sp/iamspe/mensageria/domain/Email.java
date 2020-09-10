package br.gov.sp.iamspe.mensageria.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "MENSAGERIA_EMAIL")
public class Email {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ")
    @SequenceGenerator(sequenceName = "seq_mensageria_email", allocationSize = 1, name = "CUST_SEQ")
    @Column(name = "ID_EMAIL")
    private Long idEmail;

    private String para;

    private String cc;

    private String cco;

    private String dataAgendamento;

    private String assunto;

    private String mensagem;

    private String template;

    @Column(insertable = false)
    private char status;

    private Long idApi;

    private String conteudo;

    @ToString.Exclude
    @OneToMany(mappedBy = "email", cascade = CascadeType.ALL)
    private List<Anexo> anexoList = new ArrayList<>();

}
