package br.gov.sp.iamspe.mensageria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.sp.iamspe.mensageria.domain.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    
}