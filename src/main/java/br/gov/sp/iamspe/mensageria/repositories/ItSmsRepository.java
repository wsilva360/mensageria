package br.gov.sp.iamspe.mensageria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.sp.iamspe.mensageria.domain.ItSms;

/** * SmsRepository */
@Repository
public interface ItSmsRepository extends JpaRepository<ItSms, Long> {

    @Query("select a from itsms a where a.idMensagem = ?1")
    public ItSms findByIdMensagem(String idMensagem);

    @Query("select distinct a from itsms a join fetch a.sms b where a.status in ('P','EN', 'ER') and a.statusRequest = 'S' and (trunc(b.agendamento) = trunc(SYSDATE) OR b.agendamento IS NULL)")
    public List<ItSms> getByStatusP();

    public boolean existsByIdResposta(Long idMensagem);
}