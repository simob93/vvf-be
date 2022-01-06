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
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Menu;
import it.vvfriva.entity.Ruoli;
import it.vvfriva.entity.RuoliPermessi;
import it.vvfriva.entity.UtentiRuoli;
import it.vvfriva.utils.CostantiVVF;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;
/**
 * 
 * @author simone
 *
 */
@Service
public class RuoliManager extends DbManagerStandard<Ruoli> {
	
	final Logger logger = LoggerFactory.getLogger(RuoliManager.class);

	@Autowired private UtentiRuoliManager utentiRuoliManager;
	@Autowired private RuoliPermessiManager ruoliPermessiManager;
	@Autowired private MenuManager menuManager;
	
	@PersistenceContext EntityManager em;

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
	/**
	 * 
	 * @param object
	 * @throws Exception 
	 */
	public void gestisciRuoliPermessi(Ruoli object) throws Exception {
		List<RuoliPermessi> permessiRuolo = object.getRuoliPermessi();
		if (Utils.isEmptyList(object.getRuoliPermessi())) {
			permessiRuolo = this.ruoliPermessiManager.getPermessiByIdRuolo(object.getId());
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
				this.ruoliPermessiManager.save(ruoloPermesso);
			}
		} else {
			for(RuoliPermessi ruoloPermesso: permessiRuolo) {
				if (Utils.isValidId(ruoloPermesso.getId())) {
					this.ruoliPermessiManager.update(ruoloPermesso);
				} else {
					this.ruoliPermessiManager.save(ruoloPermesso);
				}
			}
		}
	}
	
	@Override
	public boolean controllaCampiObbligatori(Ruoli object, List<ResponseMessage> msg)
			throws CustomException, Exception {
		if (Utils.isEmptyString(object.getDescrizione())) {
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new String[] {Messages.getMessage("field.descrizione")})));
			return false;
		}
		return true;
	}
	@Override
	public void operazioneDopoInserimento(Ruoli object) throws Exception, CustomException {
		this.gestisciRuoliPermessi(object);
	}
	@Override
	public void operazioneDopoModifica(Ruoli object) throws Exception, CustomException {
		this.gestisciRuoliPermessi(object);
	}
	@Override
	public void operazionePrimaDiCancellare(Ruoli object) throws Exception, CustomException {
		List<UtentiRuoli> utenti = utentiRuoliManager.getUtentiByIdRuolo(object.getId());
		if (!Utils.isEmptyList(utenti)) {
			// profoilo movimentato.
			throw new Exception(Messages.getMessage("profilo.delete.in.use"));
		}
	}

}
