package br.com.lutani.lutani_lib.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lutani.lutani_lib.entities.Exemplar;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar, UUID> {

}
