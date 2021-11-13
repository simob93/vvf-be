package it.vvfriva.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.RuoliPermessi;
import it.vvfriva.enums.DbOperation;
import it.vvfriva.managers.RuoliPermessiManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.KeyValue;
import it.vvfriva.models.TreeNodeMenu;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;

@Service
public class RuoliPermessiService extends DbServiceStandard<RuoliPermessi> {
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Autowired private RuoliPermessiManager manager;
	
	/**
	 * 
	 * @param idRuolo
	 * @return
	 */
	public JsonResponse<TreeNodeMenu> getTreePermissiByRuolo(Integer idRuolo) {
		JsonResponse<TreeNodeMenu> result = null;
		Boolean success = true;
		String message = null;
		TreeNodeMenu data = null;
		try {
			data = manager.getTree(idRuolo);
			
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			logger.error(Utils.errorInMethod(e.getMessage()));
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
		} finally {
			result = new JsonResponse<TreeNodeMenu>(success, message, data);
		}
		return result;
	}
	
	/**
	 * 
	 * @param idRuolo
	 * @return
	 */
	public JsonResponse<List<KeyValue>> getPermissiByRuoloCbox(Integer idRuolo) {
		JsonResponse<List<KeyValue>> result = null;
		Boolean success = true;
		String message = null;
		List<RuoliPermessi> permessi = null;
		List<KeyValue> data = new ArrayList<KeyValue>();
		try {
			permessi = manager.getPermessiByIdRuolo(idRuolo);
			
			if (!Utils.isEmptyList(permessi)) {
				boolean mostra = true;
				for (RuoliPermessi perm: permessi) {
					mostra = true;
					/*
					 * se la voce menu ha dei figli allora non ritorno il nodo padre ma i suoi
					 * singoli child
					 */
					if (perm.getMenu() != null && 
								!Utils.isEmptyList(perm.getMenu().getSubMenu())) {
						mostra = false;
					}
					if (mostra) {
						data.add(new KeyValue(perm.getId(), perm.getMenu().getDescrizione(), perm.getPermesso()));	
					}
				}
			}
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			logger.error(Utils.errorInMethod(e.getMessage()));
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
		} finally {
			result = new JsonResponse<List<KeyValue>>(success, message, data);
		}
		return result;
	}

	/**
	 * il metodo cambia il valore del peremsso ad una voce menu {@code idPeremsso}
	 * passata a parametro
	 * 
	 * @param idPermesso
	 * @param nuovoPermesso
	 * @return
	 */
	public JsonResponse<String> changePermesso(Integer idPermesso, String nuovoPermesso) {
		boolean success = true;
		String data = null;
		String message = "";
		JsonResponse<String> result = null;
		
		if (!Utils.isValidId(idPermesso)) {
			Utils.errorInMethod("Can't update record invalid field 'idPermesso'");
			return new JsonResponse<String>(false, Messages.getMessageFormatted("field.err.mandatory",
					new String[] { Messages.getMessage("field.idPermesso") }), null);
		}
		
		if (Utils.isEmptyString(nuovoPermesso)) {
			Utils.errorInMethod("Can't update record invalid field 'permesso'");
			return new JsonResponse<String>(false, Messages.getMessageFormatted("field.err.mandatory",
					new String[] { Messages.getMessage("field.permesso") }), null);
		}
		
		try {
			RuoliPermessi permessoDB = this.manager.getObjById(idPermesso);
			if (permessoDB != null) {
				permessoDB.setPermesso(nuovoPermesso);
				this.manager.dbManager(DbOperation.UPDATE, permessoDB);
			}
			message = Messages.getMessage("operation.ok");
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			message = Messages.getMessage("operation.ko");
		} finally {
			result = new JsonResponse<String>(success, message, data);
		}
		return result;
	}
	
}
