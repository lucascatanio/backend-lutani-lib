package br.com.lutani.lutani_lib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lutani.lutani_lib.entities.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer> {

}
