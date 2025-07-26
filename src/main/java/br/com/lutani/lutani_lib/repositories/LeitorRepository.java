package br.com.lutani.lutani_lib.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lutani.lutani_lib.entities.Leitor;

@Repository
public interface LeitorRepository extends JpaRepository<Leitor, UUID> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    Optional<Leitor> findByEmail(String email);
}
