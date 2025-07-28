package br.com.lutani.lutani_lib.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.lutani.lutani_lib.entities.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    Optional<Livro> findByIsbn(String isbn);

    @Query("SELECT COUNT(e) > 0 FROM Exemplar e WHERE e.livro.id = :livroId")
    boolean hasExemplares(@Param("livroId") UUID livro);
}
