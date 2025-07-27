package br.com.lutani.lutani_lib.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lutani.lutani_lib.entities.Emprestimo;
import br.com.lutani.lutani_lib.enums.StatusEmprestimo;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, UUID> {

    boolean existsByLeitorIdAndStatus(UUID leitorId, StatusEmprestimo status);

    long countByLeitorIdAndStatus(UUID leitorId, StatusEmprestimo status);
}
