package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.RuoliPermessi;
import it.vvfriva.models.TreeNodeMenu;
import it.vvfriva.repository.RuoliPermessiRepository;
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
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RuoliPermessiManager extends DbManagerStandard<RuoliPermessi> {

	private final Logger logger = LoggerFactory.getLogger(RuoliPermessiManager.class);

	@PersistenceContext EntityManager em;
	@Autowired private RuoliPermessiRepository repository;
	@Autowired private MenuManager menuManager;
	
	/**
	 * @param idRuolo
	 * @return ritorna i permessi di un determinato ruolo passato a parametro
	 * @throws Exception
	 */
	public List<RuoliPermessi> getPermessiByIdRuolo(Integer idRuolo) throws Exception {
		if (!Utils.isValidId(idRuolo)) {
			logger.error(Utils.errorInMethod("invalid filed idRuolo"));
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
					.append(Messages.getMessageFormatted("field.err.mandatory", new Object[] { "idRuolo" }));
			throw new Exception(sb.toString());
		}
		List<RuoliPermessi> data = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<RuoliPermessi> cq = cb.createQuery(RuoliPermessi.class);
		 
		    Root<RuoliPermessi> ruoloPermesso = cq.from(RuoliPermessi.class);
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    predicates.add(cb.equal(ruoloPermesso.get("idRuolo"), idRuolo));
		    cq.where(predicates.toArray(new Predicate[0]));
		    data = em.createQuery(cq)
		    		.getResultList();
		   			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
	
	
	/**
	 * 
	 * @param mappa
	 * @param nodo
	 */
	private void popolaPermesso(Map<Integer, String> mappa, TreeNodeMenu nodo, TreeNodeMenu parent) {
		if (!Utils.isEmptyList(nodo.getChildren())) {
			nodo.setPermesso(CostantiVVF.NESSUN_PERMESSO);
			for(TreeNodeMenu el : nodo.getChildren()) {
				popolaPermesso(mappa, el, nodo);
				
			}
		}
		if (nodo.isLeaf()) {
			String permesso = mappa.getOrDefault(nodo.getNodId(), CostantiVVF.NESSUN_PERMESSO);
			if (permesso != null) {
				nodo.setPermesso(permesso);
				if (!Utils.stringEguals(permesso, CostantiVVF.NESSUN_PERMESSO)) {
					parent.setPermesso(CostantiVVF.PERMESSO_SCRITTURA);
				}
			}
		}
	}
	/**
	 * 
	 * @param idRuolo
	 * @return
	 * @throws Exception
	 */
	public TreeNodeMenu getTree(Integer idRuolo) throws Exception {
		
		if (!Utils.isValidId(idRuolo)) {
			logger.error(Utils.errorInMethod("invalid filed idRuolo"));
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
					.append(Messages.getMessageFormatted("field.err.mandatory", new Object[] { "idRuolo" }));
			throw new Exception(sb.toString());
		}
		TreeNodeMenu nodoMenu = null;
		try {
			// recupero il menu 
			List<RuoliPermessi> ruoliPermessi = this.getPermessiByIdRuolo(idRuolo);
			Map<Integer, String> mappaRuoliPermessi = new HashMap<Integer, String>();
			ruoliPermessi.forEach(ruoloPermesso -> {
				mappaRuoliPermessi.put(ruoloPermesso.getIdMenu(), ruoloPermesso.getPermesso());
			});
			//menu programma
			nodoMenu = menuManager.getTree();
			if (nodoMenu != null) {
				nodoMenu.setRoot(true);
				//popolo i permessi in base al ruolo passato
				popolaPermesso(mappaRuoliPermessi, nodoMenu, null);
			}
									
		} catch (Exception e) {
			logger.error(Utils.errorInMethod(e.getMessage()));
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return nodoMenu;
	}




	@Override
	public boolean controllaCampiObbligatori(RuoliPermessi object, List<ResponseMessage> msg)
			throws CustomException, Exception {
		return true;
	}
}
