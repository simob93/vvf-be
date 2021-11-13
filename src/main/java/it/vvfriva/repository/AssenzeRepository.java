package it.vvfriva.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.vvfriva.entity.Assenze;

@Repository
public interface AssenzeRepository extends CrudRepository<Assenze, Integer> {

}
