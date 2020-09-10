package br.gov.sp.iamspe.mensageria.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Sms
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "IMENSAGERIA_MAXXMOBI")
public class Sms {
    
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ")
    @SequenceGenerator(sequenceName = "seq_id_lote", allocationSize = 1, name = "CUST_SEQ")
    @Column(name = "ID_IMENSAGERIA_MAXXMOBI", updatable = false)
    private Long idMsgMaxxMobi;

    @Column(name = "ACAO")
    private String acao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_AGENDAMENTO")
    private Date agendamento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INTEGRACAO")
    private Date dataIntegracao;

    @Column(name = "LOTE")
    private Long lote;

    @Column(name = "RESPOSTA")
    private String resposta;

    @Column(name = "STATUS", insertable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "ID_API")
    private Api api;

    @ToString.Exclude
    @OneToMany(mappedBy = "sms", cascade = CascadeType.ALL)
    private List<ItSms> requisicao = new ArrayList<>();

}