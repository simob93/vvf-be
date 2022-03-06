package it.vvfriva.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.vvfriva.entity.ArticoliCategorie;
/**
 * 
 * @author simone
 *
 */
@Repository
public interface ArticoliCategorieRepository extends CrudRepository<ArticoliCategorie, Integer>  {
	
}
