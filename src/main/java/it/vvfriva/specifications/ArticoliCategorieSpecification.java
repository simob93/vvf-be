package it.vvfriva.specifications;

import org.springframework.data.jpa.domain.Specification;

import it.vvfriva.entity.ArticoliCategorie;
import it.vvfriva.utils.Utils;

public class ArticoliCategorieSpecification {

	public static Specification<ArticoliCategorie> ConIdArticolo(Integer idArticolo) {
		return  (root, query, criteriaBuilder) -> {
			if (!Utils.isValidId(idArticolo)) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.equal(root.get("idArticolo"), idArticolo);
		};
	} 
	
	public static Specification<ArticoliCategorie> ConCategoriaId(Integer idCategoria) {
		return  (root, query, criteriaBuilder) -> {
			if (!Utils.isValidId(idCategoria)) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.equal(root.get("idCategoria"), idCategoria);
		};
	} 
	
	public static Specification<ArticoliCategorie> ConIdDiversoDa(Integer id) {
		return  (root, query, criteriaBuilder) -> {
			if (!Utils.isValidId(id)) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.notEqual(root.get("id"), id);
		};
	} 
}
