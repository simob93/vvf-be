package it.vvfriva.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.vvfriva.entity.Ruoli;
/**
 * 
 * @author simone
 *
 */
@Repository
public interface RuoliRepository extends CrudRepository<Ruoli, Integer> {

}
