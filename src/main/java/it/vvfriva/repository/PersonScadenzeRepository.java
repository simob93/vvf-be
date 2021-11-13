package it.vvfriva.repository;

import org.springframework.data.repository.CrudRepository;

import it.vvfriva.entity.SettingScadenze;

public interface PersonScadenzeRepository extends CrudRepository<SettingScadenze, Integer> {
	SettingScadenze findFirstByIdPerson(Integer idPerson);
}
