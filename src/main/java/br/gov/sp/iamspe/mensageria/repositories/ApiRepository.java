package br.gov.sp.iamspe.mensageria.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.sp.iamspe.mensageria.domain.Api;

@Repository
public interface ApiRepository extends JpaRepository<Api, Long> {

    @Query("select DISTINCT a,b,c from Api a join fetch a.sms b join fetch b.requisicao c where c.status = 'R'")
    public Set<Api> getMsgParaRemetente();

    @Query("select a from Api a where a.idApi = ?1")
    public Optional<Api> findByIdApi(Long id);

    @Query("select a from Api a where a.idApi = ?1")
    public Boolean existsByIdApi(Long id);
}