package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import it.vvfriva.enums.DbOperation;
import it.vvfriva.enums.EnumsChiaviEsterne;
import it.vvfriva.enums.EnumsChiaviEsterneAction;
import it.vvfriva.interfaces.EntityInfo;
import it.vvfriva.repository.DbManagerStandardInt;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;

@Service
@org.springframework.transaction.annotation.Transactional(rollbackFor = Exception.class)
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public abstract class DbManagerStandard<T> implements DbManagerStandardInt<T>   {
	
	@Autowired EntityManager em;
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	private List<ResponseMessage> message;
	
	public abstract boolean checkCampiObbligatori(T object);
	public abstract boolean checkObjectForInsert(T object);
	public abstract boolean checkObjectForDelete(T object);
	public abstract boolean checkObjectForUpdate(T object);
	/**
	 * 
	 * @param action
	 * @param object
	 * @return
	 */
	public boolean beforeDbAction(DbOperation action , T object) {
		return true;
	}
	/**
	 * 
	 * @param action
	 * @param object
	 * @return
	 */
	public boolean afterDbAction(DbOperation action , T object) {
		return true;
	}
	
	public T getObjById(Integer id) {
		return (T) getRepository().findById(id).get();
	}
	/**
	 * 
	 * @param action
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public List<ResponseMessage> dbManager(DbOperation action, Integer id) throws CustomException {
		return dbManager(action, getObjById(id));
	}
	/**
	 * 
	 * @param action
	 * @param object
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	public List<ResponseMessage> dbManager(DbOperation action, T object) throws CustomException {
		
		boolean result = isValidObject(action, object);
		if (result == false) {
			logger.error("Exception in function: "+ this.getClass().getCanonicalName() + ".dbManager invalid object");
			throw new CustomException(getMessage());
		}
		try {
			result = beforeDbAction(action, object);
			if (!result) {
				throw new CustomException(getMessage());
			}
			switch (action) {
			case INSERT:
			case UPDATE:
				if (result) {
					getRepository().save(object);
				} 
				break;
			case DELETE:
				getRepository().delete(object);
				break;
			default:
				break;
			}
			result = afterDbAction(action, object);
			if (!result) {
				throw new CustomException(getMessage());
			}
			addMessage(Messages.getMessage("operation.ok"));

		} catch (Exception e) {
			e.printStackTrace();
			addMessage(e.getMessage());
			throw new CustomException(getMessage());
		}
		return getMessage();
	}
	/**
	 * 
	 * @param action
	 * @param object
	 * @return
	 */
	private boolean isValidObject(DbOperation action, T object) {
		
		boolean result = true;
		switch (action) {
		case INSERT:
			result = checkCampiObbligatori(object);
			if (result) {
				result = checkObjectForInsert(object);
			}
			break;
		case UPDATE:
			result = checkCampiObbligatori(object);
			if (result) {
				result = checkObjectForUpdate(object);
			}
			break;
		case DELETE:
			result &= checkObjectForDelete(object);
			result &= checkReferentialIntegrity(object, action);
			
		default:
			break;
		}
		if (result == false && Utils.isEmptyList(getMessage())) {
			addMessage(Messages.getMessage("object.ko"));
		}
		
		return result;
	}
	/**
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean checkReferentialIntegrity(T object, DbOperation action) {
		boolean canDo = true;
		try {
			if (object == null) {
				return false;
			} 
			if (object.getClass().isAnnotationPresent(Table.class)) {
				if (object instanceof EntityInfo) {
					// verifico l'esistenza del tag sulla classe 
					Integer id = ((EntityInfo) object).getId();
					String nomeTabella = object.getClass().getAnnotation(Table.class).name();
					if (!Utils.isEmptyString(nomeTabella)) {
						List<EnumsChiaviEsterne> tabelleCollegate = EnumsChiaviEsterne.getForeignKeyTables(nomeTabella);
						if (!Utils.isEmptyList(tabelleCollegate)) {
							for (EnumsChiaviEsterne tabella: tabelleCollegate) {
								String sql = "Select " + tabella.getFieldIdTabellaForeignKey() + " From "
										+ tabella.getTabellaForeignKey() + " Where " + tabella.getFieldIdTabellaForeignKey()
										+ "= :id ";
								
								if (!Utils.isEmptyString(tabella.getExtraCondition())) {
									sql += "And " + tabella.getExtraCondition();
								}
								
								List<Object[]> result = em.createNativeQuery(sql).setParameter("id", id)
										.setMaxResults(1)
										.getResultList();
								
								if (!Utils.isEmptyList(result)) {
									if (DbOperation.DELETE.compareTo(action) == 0) {
										if (tabella.getDeleteAction().compareTo(EnumsChiaviEsterneAction.RESTRICT) == 0) {
											// blocco delete
											canDo = false;
										}
									} else if (DbOperation.UPDATE.compareTo(action) == 0) {
										if (tabella.getUpdateAction().compareTo(EnumsChiaviEsterneAction.RESTRICT) == 0) {
											// blocco update
											canDo = false;
										}
									}
									if (!canDo) {
										addMessage(tabella.getMessage());
										break;
									}
								}
							}
						}
					}
				}
			} 				
		} catch (Exception e) {
			canDo = false;
			logger.error("Exception in Method: " + this.getClass().getCanonicalName() + ".checkReferentialIntegrity", e.getMessage());
			e.printStackTrace();
		}
		return canDo;
	}
	public List<ResponseMessage> getMessage() {
		return message;
	}
	public void setMessage(List<ResponseMessage> message) {
		this.message = message;
	}
	
	public void addMessage(String message) {
		addMessage(message, ResponseMessage.MSG_TYPE_LOUD);
	}
	
	public void addMessage(String message, Integer type) {
		if (Utils.isEmptyList(this.message)) {
			this.message  = new ArrayList<ResponseMessage>();
		}
		this.message.add(new ResponseMessage(type, message));
	}

}
