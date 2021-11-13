package it.vvfriva.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.vvfriva.entity.RuoliPermessi;

@Repository
public interface RuoliPermessiRepository extends CrudRepository<RuoliPermessi, Integer> {

}
