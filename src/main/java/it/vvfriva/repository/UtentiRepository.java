package it.vvfriva.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.vvfriva.entity.Utenti;

@Repository
public interface UtentiRepository extends CrudRepository<Utenti, Integer> {

}
