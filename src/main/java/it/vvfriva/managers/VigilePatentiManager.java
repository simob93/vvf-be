package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.VigilePatenti;
import it.vvfriva.enums.DbOperation;
import it.vvfriva.models.KeyValueTipiScadenza;
import it.vvfriva.models.ModelPortletVigilePatenti;
import it.vvfriva.repository.VigilePatentiRepository;
import it.vvfriva.utils.CostantiVVF;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;

@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class VigilePatentiManager extends DbManagerStandard<VigilePatenti> {

	final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	VigilePatentiRepository repository;
	
	@Autowired
	EntityManager em;
	
	@Autowired
	ScadenzeManager scadenzeManager;

	@Override
	public CrudRepository<VigilePatenti, Integer> getRepository() {
		return repository;
	}
	/**
	 * ritorna la lista delle patenti filtrata per idVigile
	 * 
	 * @param idVigile
	 * @return
	 * @throws CustomException
	 */
	public List<VigilePatenti> list(Integer idVigile) throws CustomException {
		
		if (!Utils.isValidId(idVigile)) {
			log.error("Exception in class: " + this.getClass().getCanonicalName() + ".list invalid field idVigile");
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko"))
			.append(": ").append(Messages.getMessageFormatted("field.err.mandatory", new String[] {"idVigile"}));
			throw new CustomException(sb.toString());
		}
		List<VigilePatenti> data = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<VigilePatenti> cq = cb.createQuery(VigilePatenti.class);

			Root<VigilePatenti> vigilePat = cq.from(VigilePatenti.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (Utils.isValidId(idVigile)) {
				predicates.add(cb.equal(vigilePat.get("idVigile"), idVigile));	
			}
			//data inizio dal piu recente al meno
			cq.orderBy(cb.asc(vigilePat.get("date")));
			cq.where(predicates.toArray(new Predicate[0]));
			data = em.createQuery(cq).getResultList();
			
		} catch (Exception e) {
			log.error("Excpetion in method:" + this.getClass().getCanonicalName() + ".list", e);
			e.printStackTrace();
			throw new CustomException(e.getMessage());
		}
		return data;
	}
	
	/**
	 * 
	 * @param idVigile
	 * @return
	 * @throws Exception
	 */
	public List<KeyValueTipiScadenza> getCbox(Integer idVigile) throws Exception {
		if (!Utils.isValidId(idVigile)) {
			logger.error("Excepetion in function: 'getCbox', invalid filed idVigile");
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
					.append(Messages.getMessageFormatted("field.err.mandatory", new Object[] { "idVigile" }));
			throw new Exception(sb.toString());
		}
		List<KeyValueTipiScadenza> data = new ArrayList<KeyValueTipiScadenza>();
		try {

			List<VigilePatenti> listVigilePatenti = list(idVigile);
			if (!Utils.isEmptyList(listVigilePatenti)) {
				listVigilePatenti.forEach(patente -> {
					data.add(new KeyValueTipiScadenza(patente.getId(), patente.getDescrPerson(), null,
							patente.getIdVigile(), patente.getIdPerson()));
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in function: " + this.getClass().getCanonicalName() + ".getCbox ", e);
			throw new CustomException(Messages.getMessage("search.ko"));
		}
		return data;
	}

	/**
	 * 
	 * @param idVigile
	 * @return
	 * @throws CustomException
	 */
	public List<ModelPortletVigilePatenti> listForPortlet(Integer idVigile) throws CustomException {
		if (!Utils.isValidId(idVigile)) {
			log.error("Exception in function: " + this.getClass().getCanonicalName()
					+ ".listForPortlet invalid filed idVigile");
			StringBuilder sb = new StringBuilder(Messages.getMessage("search.ko")).append(": ")
					.append(Messages.getMessageFormatted("field.err.mandatory", new Object[] { "idVigile" }));
			throw new CustomException(sb.toString());
		}
		List<ModelPortletVigilePatenti> data = null;
		try {
			data = repository.listForPortlet(idVigile);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in function: " + this.getClass().getCanonicalName()
					+ ".listForPortlet invalid filed idVigile");
			throw new CustomException(Messages.getMessage("search.ko"));
		}
		return data;
	}

	/**
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private boolean hasRecordDoppi(VigilePatenti object) throws Exception {
		List<VigilePatenti> data = null;
		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<VigilePatenti> cq = cb.createQuery(VigilePatenti.class);

			Root<VigilePatenti> vigilePat = cq.from(VigilePatenti.class);
			List<Predicate> predicates = new ArrayList<Predicate>();

			predicates.add(cb.equal(vigilePat.get("idPerson"), object.getIdPerson()));
			predicates.add(cb.equal(vigilePat.get("idVigile"), object.getIdVigile()));

			if (Utils.isValidId(object.getId())) {
				predicates.add(cb.notEqual(vigilePat.get("id"), object.getId()));
			}

			cq.where(predicates.toArray(new Predicate[0]));
			data = em.createQuery(cq).setMaxResults(1).getResultList();
			if (!Utils.isEmptyList(data)) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in function " + this.getClass().getCanonicalName() + ".hasRecordDoppi", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return false;
	}

	@Override
	public boolean checkCampiObbligatori(VigilePatenti object) {
		/*
		 * sono neccessari al fine di un insermiento ok! i campi numero patente
		 * solitamente alfanumerico data di svolgimento patente e tipologia di patete
		 */
		if (Utils.isEmptyString(object.getNumber())) {
			log.warn("Can't persist record invalid field number");
			addMessage(Messages.getMessageFormatted("field.err.mandatory", new Object[] { "numero patente" }));
			return false;
		}
		if (!Utils.isValidDate(object.getDate())) {
			log.warn("Can't persist record invalid field date");
			addMessage(Messages.getMessageFormatted("field.err.mandatory", new Object[] { "Data di inizio" }));
			return false;
		}
		if (!Utils.isValidId(object.getIdPerson())) {
			log.warn("Can't persist record invalid field id person");
			addMessage(Messages.getMessageFormatted("field.err.mandatory", new Object[] { "Tipo patente" }));
			return false;
		}

		// controllo record doppi
		try {
			boolean exists = hasRecordDoppi(object);
			if (exists) {
				addMessage(Messages.getMessage("record.already.exists"));
				return false;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			addMessage(ex.getMessage());
			return false;
		}

		return true;
	}

	@Override
	public boolean checkObjectForInsert(VigilePatenti object) {
		return true;
	}

	@Override
	public boolean checkObjectForUpdate(VigilePatenti object) {
		return true;
	}

	@Override
	public boolean afterDbAction(DbOperation action, VigilePatenti object) {
		try {
			if (action == DbOperation.INSERT) {
				if (Utils.isValidDate(object.getDateExpiration())) {
					scadenzeManager.insertExp(object.getId(), object.getDate(), object.getDateExpiration(),
							CostantiVVF.AREA_PATENTI_SERVIZIO, object.getIdVigile());
				}
			}
		} catch (Exception ex) {
			addMessage(ex.getMessage());
			ex.printStackTrace();
			log.error("Exception in function:" + this.getClass().getCanonicalName() + ".afterDbAction during: "
					+ action.toString());
			return false;
		}
		return true;
	}
	
	@Override
	public boolean checkObjectForDelete(VigilePatenti object) {
		return true;
	}
}
