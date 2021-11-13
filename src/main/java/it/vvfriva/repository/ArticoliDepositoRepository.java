package it.vvfriva.repository;

import org.springframework.data.repository.CrudRepository;

import it.vvfriva.entity.ArticoliDepositi;
/**
 * 
 * @author simone
 *
 */
public interface ArticoliDepositoRepository extends CrudRepository<ArticoliDepositi, Integer>  {
	
}
