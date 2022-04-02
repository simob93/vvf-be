package it.vvfriva.specifications;

import org.springframework.data.jpa.domain.Specification;

import it.vvfriva.entity.ArticoliScadenza;
import it.vvfriva.enums.EnumTrueFalse;
import it.vvfriva.utils.Utils;

public class ArticoliScadenzaSpecification  {
	/**
	 * 	
	 * @param idArticolo
	 * @return
	 */
	public static Specification<ArticoliScadenza> ConIdArticolo(Integer idArticolo) {
		return  (root, query, criteriaBuilder) -> {
			if (!Utils.isValidId(idArticolo)) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.equal(root.get("idArticolo"), idArticolo);
		};
	} 
	/**
	 * 	
	 * @param effettuato
	 * @return
	 */
	public static Specification<ArticoliScadenza> ConScadenzaAttiva() {
		return  (root, query, criteriaBuilder) -> {
			return criteriaBuilder.or(
					criteriaBuilder.equal(root.get("effettuato"), EnumTrueFalse.F), 
					criteriaBuilder.isNull(root.get("effettuato")));
		};
	} 
	
	public static Specification<ArticoliScadenza> ConStorico() {
		return  (root, query, criteriaBuilder) -> {
			return criteriaBuilder.equal(root.get("effettuato"), EnumTrueFalse.T); 
		};
	} 
}