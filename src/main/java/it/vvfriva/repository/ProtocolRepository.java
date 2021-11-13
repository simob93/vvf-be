package it.vvfriva.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.vvfriva.entity.Protocol;
public interface ProtocolRepository extends CrudRepository<Protocol, Integer>  {
	@Query("From Protocol ")
	List<Protocol> findAllPaged(Pageable paged);
}
