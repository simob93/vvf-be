package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Menu;
import it.vvfriva.entity.Ruoli;
import it.vvfriva.entity.RuoliPermessi;
import it.vvfriva.entity.UtentiRuoli;
import it.vvfriva.enums.DbOperation;
import it.vvfriva.repository.RuoliRepository;
import it.vvfriva.utils.CostantiVVF;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;
/**
 * 
 * @author simone
 *
 */
@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RuoliManager extends DbManagerStandard<Ruoli> {
	
	final Logger logger = LoggerFactory.getLogger(RuoliManager.class);

	@Autowired private RuoliRepository repository;
	@Autowired private UtentiRuoliManager utentiRuoliManager;
	@Autowired private RuoliPermessiManager ruoliPermessiManager;
	@Autowired private MenuManager menuManager;
	
	@PersistenceContext EntityManager em;
	
	@Override
	public CrudRepository<Ruoli, Integer> getRepository() {
		return repository;
	}

	@Override
	public boolean checkCampiObbligatori(Ruoli object) {
		
		if (Utils.isEmptyString(object.getDescrizione())) {
			addMessage(Messages.getMessageFormatted("field.err.mandatory", new String[] {Messages.getMessage("field.descrizione")}));
			return false;
		}
		
		return true;
	}
	

	@Override
	public boolean checkObjectForInsert(Ruoli object) {
		return true;
	}

	@Override
	public boolean checkObjectForUpdate(Ruoli object) {
		return true;
	}
	/**
	 * 
	 * @return elenco di tutti i ruoli  creati 
	 * @throws Exception
	 */
	public List<Ruoli> list() throws Exception {
		List<Ruoli> data = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Ruoli> cq = cb.createQuery(Ruoli.class);
		 
		    Root<Ruoli> ruolo = cq.from(Ruoli.class);
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    cq.where(predicates.toArray(new Predicate[0]))
		    	.orderBy(cb.asc(ruolo.get("descrizione")));
		    data = em.createQuery(cq)
		    		.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(Utils.errorInMethod(e.getMessage()));
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
	
	@Override
	public boolean afterDbAction(DbOperation action, Ruoli object) {
		try {
			if (action.compareTo(DbOperation.INSERT) == 0 || 
					action.compareTo(DbOperation.UPDATE) == 0) {
				
				// inserimento o modifica di un ruolo
				List<RuoliPermessi> permessiRuolo = object.getRuoliPermessi();
				if (Utils.isEmptyList(object.getRuoliPermessi())) {
					permessiRuolo = this.ruoliPermessiManager.getPermessiByIdRuolo(object.getId());
				}
				
				if (Utils.isEmptyList(permessiRuolo)) {
					// creo il ruolo con voci menu e permesso = 'N'
					List<Menu> listaVociMenu = this.menuManager.listMenu(true);
					for(Menu menu: listaVociMenu) {
						if (!Utils.isEmptyList(menu.getSubMenu())) {
							// non inserisce nodi padre.
							continue;
						}
						RuoliPermessi ruoloPermesso = new RuoliPermessi();
						ruoloPermesso.setIdMenu(menu.getId());
						ruoloPermesso.setIdRuolo(object.getId());
						ruoloPermesso.setPermesso(CostantiVVF.NESSUN_PERMESSO);
						this.ruoliPermessiManager.dbManager(DbOperation.INSERT, ruoloPermesso);
					}
				} else {
					
					for(RuoliPermessi ruoloPermesso: permessiRuolo) {
						if (Utils.isValidId(ruoloPermesso.getId())) {
							this.ruoliPermessiManager.dbManager(DbOperation.UPDATE, ruoloPermesso);
						} else {
							this.ruoliPermessiManager.dbManager(DbOperation.INSERT, ruoloPermesso);
						}
					}
				}
				
			} 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Excpetion in method: " + this.getClass().getCanonicalName() + "afterDbAction during action" + action.toString());
			return false;
		}
		return true;
	}

	@Override
	public boolean checkObjectForDelete(Ruoli object) {
		// verifico che il ruolo non sia associato a ciascun account
		if (!Utils.isValidId(object.getId())) {
			addMessage(Messages.getMessageFormatted("field.err.mandatory",
					new String[] { Messages.getMessage("field.id") }));
			return false;
		}
		List<UtentiRuoli> utenti = utentiRuoliManager.getUtentiByIdRuolo(object.getId());
		if (!Utils.isEmptyList(utenti)) {
			// profoilo movimentato.
			addMessage(Messages.getMessage("profilo.delete.in.use"));
			return false;
		}
		return true;
	}

}
