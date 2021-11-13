package it.vvfriva.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.vvfriva.entity.Area;

public interface AreaRepository extends CrudRepository<Area, Integer>  {
	/**
	 * 
	 * @param manageExpiry
	 * @return
	 */
	List<Area> findByManageExpiry(Integer manageExpiry);
	/**
	 * 
	 * @return
	 */
	default List<Area> findByManageExpiry() {
		return findByManageExpiry(1);
	};
}
