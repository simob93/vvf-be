package it.vvfriva.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.vvfriva.entity.Servizio;

public interface ServizioRepository extends CrudRepository<Servizio, Integer> {
	/**
	 * 
	 * @param idVigile
	 * @return
	 */
	@Query("From Servizio where id_vigile = :idVigile ")
	List<Servizio> listByIdVigile(@Param("idVigile") Integer idVigile, Sort sort);

	/**
	 * 
	 * @param dal
	 * @param al
	 * @param idVigile
	 * @return
	 */
	@Query("From Servizio where ( date_end is null or date_end >= :dateStart) and date_start <= :dateEnd and id_vigile = :idVigile")
	List<Servizio> listByPeriod(@Param("dateStart") Date dal, @Param("dateEnd") Date al,
			@Param("idVigile") Integer idVigile, Sort sort);

	/**
	 * 
	 * @param dal
	 * @param al
	 * @return
	 */
	@Query("From Servizio where ( date_end is null or date_end >= :dateStart) and date_start <= :dateEnd")
	List<Servizio> listByPeriod(@Param("dateStart") Date dal, @Param("dateEnd") Date al, Sort sort);

	/**
	 * 
	 * @param dal
	 * @param al
	 * @param idVigile
	 * @param idExclude
	 * @param sort
	 * @return
	 */
	@Query("From Servizio where ( date_end is null or date_end >= :dateStart) and date_start <= :dateEnd and id_vigile = :idVigile and id <> :idExclude ")
	List<Servizio> listByPeriod(@Param("dateStart") Date dal, @Param("dateEnd") Date al,
			@Param("idVigile") Integer idVigile, @Param("idExclude") Integer idExclude, Sort sort);

	/**
	 * @param idVigile
	 * @param letter
	 * @return
	 */
	@Query("From Servizio where ( date_end is null or date_end >= :dateStart) and date_start <= :dateEnd and id_team = :idTeam and letter = :letter")
	List<Servizio> listPeriodByTeamAndLetter(@Param("letter") String letter, @Param("idTeam") Integer idTeam, @Param("dateStart") Date dateStart, @Param("dateEnd") Date dateEnd);

}
