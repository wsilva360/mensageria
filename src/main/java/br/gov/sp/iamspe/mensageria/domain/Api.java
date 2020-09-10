package br.gov.sp.iamspe.mensageria.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Api
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "API", schema = "GUARDIAN")
public class Api {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue
    @Column(name = "ID_API")
    private Long idApi;

    @Column(name = "URI")
    private String uri;

    @ToString.Exclude
    @OneToMany(mappedBy = "api")
    private Set<Sms> sms = new HashSet<>();


    public Api(Long idApi) {
        this.idApi = idApi;
    }

    public Api(Long idApi, String uri) {
        this.idApi = idApi;
        this.uri = uri;
    }

}