package br.com.lutani.lutani_lib.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lutani.lutani_lib.entities.Exemplar;
import br.com.lutani.lutani_lib.enums.StatusExemplar;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar, UUID> {

    boolean existsByCodigoExemplar(String codigoExemplar);

    Optional<Exemplar> findByCodigoExemplar(String codigoExemplar);

    long countByLivroIdAndStatus(UUID livroId, StatusExemplar status);
}
