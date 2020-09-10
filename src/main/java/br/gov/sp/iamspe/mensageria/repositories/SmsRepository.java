package br.gov.sp.iamspe.mensageria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.sp.iamspe.mensageria.domain.Sms;

/** * SmsRepository */
@Repository
public interface SmsRepository extends JpaRepository<Sms, Long> { 

    @Query("select a, b, c from Sms a join fetch a.requisicao b join fetch a.api c where b.status = 'R'")
    public List<Sms> findByStatusR(); 

    @Query("select distinct a from Sms a join fetch a.requisicao b where b.idMensagem = ?1")
    public Sms getIdIMensageriaByidMensagem(String idMensagem);
}