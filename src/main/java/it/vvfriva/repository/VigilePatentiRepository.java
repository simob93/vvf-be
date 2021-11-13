package it.vvfriva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.vvfriva.entity.VigileCertificati;
import it.vvfriva.entity.VigilePatenti;
import it.vvfriva.models.ModelPortletVigilePatenti;

public interface VigilePatentiRepository extends CrudRepository<VigilePatenti, Integer> {
	/**
	 * 
	 * @param idVigile
	 * @return
	 */
	@Query("select new it.vvfriva.models.ModelPortletVigilePatenti(v.id, p.name, v.date) from VigilePatenti v join Person p on (p.id = v.idPerson) where v.idVigile =:idVigile ")
	List<ModelPortletVigilePatenti> listForPortlet(@Param("idVigile") Integer idVigile);
	/**
	 * 
	 * @param idPerson
	 * @param idVigile
	 * @return
	 */
	VigileCertificati findFirstByIdPersonAndIdVigile(Integer idPerson, Integer idVigile);
}
