package it.vvfriva.repository;

import org.springframework.data.repository.CrudRepository;

public interface DbManagerStandardInt<T> {
	CrudRepository<T, Integer> getRepository();
}
