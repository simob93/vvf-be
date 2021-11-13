package it.vvfriva.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.vvfriva.entity.Gradi;

@Repository
public interface GradiRepository extends CrudRepository<Gradi, Integer> {

}
