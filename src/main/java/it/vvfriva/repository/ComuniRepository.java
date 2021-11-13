package it.vvfriva.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.vvfriva.entity.Comuni;

public interface ComuniRepository  extends CrudRepository<Comuni, Integer> {

	List<Comuni> findByIstatProvicina(Integer istatProvicina);
	
}
