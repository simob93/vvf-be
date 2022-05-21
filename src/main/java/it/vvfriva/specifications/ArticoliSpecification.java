package it.vvfriva.specifications;

import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.ControllerAdvice;

import it.vvfriva.entity.Articoli;
import it.vvfriva.utils.Utils;

public class ArticoliSpecification {
	/**
	 * 
	 * @param categoriaId
	 * @return
	 */
	public static Specification<Articoli> ConCategoriaId(Integer categoriaId) {
		return  (root, query, criteriaBuilder) -> {
			if (!Utils.isValidId(categoriaId)) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.equal(root.join("categorie", JoinType.INNER).get("idCategoria"), categoriaId);
		};
	} 
	
	public static Specification<Articoli> ConCodice(String codice) {
		return  (root, query, criteriaBuilder) -> {
			if (Utils.isEmptyString(codice)) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.equal(criteriaBuilder.lower(root.get("codice")), codice.trim().toLowerCase());
		};
	} 
	/**
	 * 
	 * @param depositoId
	 * @return
	 */
	public static Specification<Articoli> ConDepositoId(Integer depositoId) {
		return  (root, query, criteriaBuilder) -> {
			if (!Utils.isValidId(depositoId)) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.equal(root.join("depositi", JoinType.INNER).get("idDeposito"), depositoId);
		};
	}
	
	public static Specification<Articoli> ConDescrizione(String descrizione) {
		return  (root, query, criteriaBuilder) -> {
			if (Utils.isEmptyString(descrizione)) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("descrizione")), "%"+ descrizione.toLowerCase() +"%");
		};
	}
	
	public static Specification<Articoli> ConGestioneScadenza(boolean gestioneScadenza) {
		return (root, query, criteriaBuilder) -> {
			if (!gestioneScadenza) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.equal(root.get("abilitaScadenza"), true);
		};
	}
	
	public static Specification<Articoli> ConIdDiversoDa(Integer id) {
		return (root, query, criteriaBuilder) -> {
			if (!Utils.isValidId(id)) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.notEqual(root.get("id"), id);
		};
	}
	
}
