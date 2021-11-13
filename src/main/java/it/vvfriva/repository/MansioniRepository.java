package it.vvfriva.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.vvfriva.entity.Mansioni;

@Repository
public interface MansioniRepository extends CrudRepository<Mansioni, Integer> {

}
