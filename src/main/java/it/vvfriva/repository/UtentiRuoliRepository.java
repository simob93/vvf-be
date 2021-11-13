package it.vvfriva.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.vvfriva.entity.UtentiRuoli;

@Repository
public interface UtentiRuoliRepository extends CrudRepository<UtentiRuoli, Integer> {

}
