package it.vvfriva.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.vvfriva.entity.VigileCertificati;
import it.vvfriva.models.ModelPortletVigileCertificati;
import it.vvfriva.models.ModelPrntAutorizzazioni;

public interface VigileCertificatiRepository  extends CrudRepository<VigileCertificati, Integer> {
	/**
	 * 
	 * @param idVigile
	 * @return
	 */
	@Query("select new it.vvfriva.models.ModelPortletVigileCertificati(c.id, p.name, c.date) from VigileCertificati c join Person p on (p.id = c.idPerson) where c.idVigile =:idVigile ")
	List<ModelPortletVigileCertificati> listForPortlet(@Param("idVigile") Integer idVigile);
	/**
	 * 
	 * @param sort
	 * @return
	 */
	@Query("select new it.vvfriva.models.ModelPrntAutorizzazioni(c.idVigile, v.firstName ||' '|| v.lastName, c.descrPerson) From VigileCertificati c right join Vigile v on (v.id = c.idVigile)")
	List<ModelPrntAutorizzazioni> listForReport(Sort sort);
	
	default List<ModelPrntAutorizzazioni> listForReport() {
		return listForReport(Sort.unsorted());
	};
}
