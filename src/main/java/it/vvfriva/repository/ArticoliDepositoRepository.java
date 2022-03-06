package it.vvfriva.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.vvfriva.entity.ArticoliDepositi;
/**
 * 
 * @author simone
 *
 */
@Repository
public interface ArticoliDepositoRepository extends CrudRepository<ArticoliDepositi, Integer>  {
	
}
