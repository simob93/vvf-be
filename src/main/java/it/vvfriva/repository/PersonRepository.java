package it.vvfriva.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.vvfriva.entity.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
		
	@Query("from Person p where p.idArea in (:idArea)")
	List<Person> list(@Param("idArea") List<Integer> area, Sort sort);
	
	default List<Person> list(List<Integer> area) {
		return list(area, Sort.by("name").ascending());
	};
}
