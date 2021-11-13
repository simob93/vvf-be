package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import it.vvfriva.enums.DbOperation;
import it.vvfriva.repository.DbManagerStandardInt;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;

@Service
@org.springframework.transaction.annotation.Transactional(rollbackFor = Exception.class)
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public abstract class DbManagerStandard<T> implements DbManagerStandardInt<T>   {
	
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
	public List<ResponseMessage> dbManager(DbOperation action, T object) throws CustomException {
		return dbManager(action, object, null);
	}
	/**
	 * 
	 * @param action
	 * @param object
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	public List<ResponseMessage> dbManager(DbOperation action, T object, Integer id) throws CustomException {
		
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
				getRepository().deleteById(id);
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
			result = checkObjectForDelete(object);
		default:
			break;
		}
		if (result == false && Utils.isEmptyList(getMessage())) {
			addMessage(Messages.getMessage("object.ko"));
		}
		
		return result;
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
