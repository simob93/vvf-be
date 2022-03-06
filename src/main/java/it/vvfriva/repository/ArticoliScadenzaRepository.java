package it.vvfriva.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.vvfriva.entity.ArticoliScadenza;

/**
 * 
 * @author simone
 *
 */
@Repository
public interface ArticoliScadenzaRepository extends CrudRepository<ArticoliScadenza, Integer>, JpaSpecificationExecutor<ArticoliScadenza>  {
	
}
