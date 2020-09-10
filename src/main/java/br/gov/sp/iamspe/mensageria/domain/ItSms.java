package br.gov.sp.iamspe.mensageria.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Requisicao
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "itsms")
@Table(name = "IT_MENSAGERIA_MAXXMOBI")
public class ItSms {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ")
    @SequenceGenerator(sequenceName = "seq_id_mensagem", allocationSize = 1, name = "CUST_SEQ")
    @Column(name = "ID_IT_MENSAGERIA_MAXXMOBI", updatable = false)
    private Long idItMaxxMobi;

    @Column(name = "ID_MENSAGEM", updatable = false)
    private String idMensagem;

    @Column(name = "NR_TELEFONE", updatable = false)
    private String numero;

    @Column(name = "MENSAGEM", updatable = false)
    private String mensagem;

    @Column(name = "STATUS", insertable = false)
    private String status;

    @Column(name = "DT_ENVIO", columnDefinition = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEvio;

    @Column(name = "ID_RESPOSTA", updatable = false)
    private Long idResposta;

    @Column(name = "DT_RESPOSTA", columnDefinition = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataResposta;

    @Column(name = "STATUS_REQUEST", updatable = false)
    private String statusRequest;

    @ManyToOne
    @JoinColumn(name = "ID_IMENSAGERIA_MAXXMOBI", updatable = false)
    private Sms sms;

}
