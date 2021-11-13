package it.vvfriva.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.vvfriva.entity.Vigile;
import it.vvfriva.utils.Paged;
import it.vvfriva.utils.Utils;

public interface VigileRepository extends CrudRepository<Vigile, Integer> {
	
	@Query("from Vigile v")
	List<Vigile> findAllPaged( String search, Paged pageRequest);
	
	List<Vigile> findByFirstNameContainingOrLastNameContaining( String search, Paged pageRequest);

	default List<Vigile> findAllPaged(Integer start, Integer limit, String search) {
		if (!Utils.isEmptyString(search)) {
			return findByFirstNameContainingOrLastNameContaining(search, new Paged(start, limit, Sort.Direction.ASC, new String[] {"lastName"}));
		}
		return findAllPaged(search, new Paged(start, limit, Sort.Direction.ASC, new String[] {"lastName"}));
	}

}
