package it.vvfriva.repository;

import org.springframework.data.repository.CrudRepository;

import it.vvfriva.entity.Articoli;
/**
 * 
 * @author simone
 *
 */
public interface ArticoliRepository extends CrudRepository<Articoli, Integer>  {
	
}
