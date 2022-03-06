package it.vvfriva.managers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Menu;
import it.vvfriva.exception.UserFriendlyException;
import it.vvfriva.models.TreeNodeMenu;
import it.vvfriva.repository.MenuRepository;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;

/**
 * 
 * @author simone
 *
 */
@Service
public class MenuManager extends DbManagerStandard<Menu> {
	
	private final Logger logger = LoggerFactory.getLogger(MenuManager.class);

	@Autowired private MenuRepository repository;
	
	@PersistenceContext EntityManager em;
	
	public TreeNodeMenu getTree() throws Exception {
		TreeNodeMenu data = new TreeNodeMenu();
		try {
			// recupero il menu 
			List<Menu> menus = this.listMenu();
			if (!Utils.isEmptyList(menus)) {
				for(Menu menu: menus) {
					TreeNodeMenu parent = new TreeNodeMenu();
					parent.setParentId(null);
					parent.setText(menu.getDescrizione());
					parent.setNodId(menu.getId());
					data.addChild(parent);
					buildTree(menu, parent);
					
				}
			}
		} catch (Exception e) {
			logger.error(Utils.errorInMethod(e.getMessage()));
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
	/**
	 * 
	 * @param menu
	 * @return
	 */
	private void buildTree(Menu menu, TreeNodeMenu nodo) {
		if (!Utils.isEmptyList(menu.getSubMenu())) {
			for(Menu sub: menu.getSubMenu()) {
				TreeNodeMenu child = new TreeNodeMenu();
				child.setText(sub.getDescrizione());
				child.setParentId(sub.getIdPadre());
				child.setNodId(sub.getId());
				nodo.addChild(child);
				buildTree(sub, child);
			}
		}
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listMenu() throws Exception {
		return listMenu(false);
	}
	/**
	 * 
	 * @param children
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listMenu(boolean children) {
		List<Menu> data = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Menu> cq = cb.createQuery(Menu.class);
		    Root<Menu> menu = cq.from(Menu.class);
		    // non preno i nodi figli.
		    if (children == false) {
		    	cq.where(cb.isNull(menu.get("idPadre")));
		    }
		    data = em.createQuery(cq)
		    		.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
			throw new UserFriendlyException(Messages.getMessage("search.ko"));
		}
		return data;
	}
	
	@Override
	public boolean controllaCampiObbligatori(Menu object, List<ResponseMessage> msg) {
		return false;
	}
}
