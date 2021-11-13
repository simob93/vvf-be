package it.vvfriva.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.vvfriva.entity.Menu;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Integer> {

}
