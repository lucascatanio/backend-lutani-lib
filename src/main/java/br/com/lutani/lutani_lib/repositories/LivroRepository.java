package br.com.lutani.lutani_lib.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lutani.lutani_lib.entities.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {
  
}
