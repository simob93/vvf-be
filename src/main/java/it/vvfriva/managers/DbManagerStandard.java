package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.vvfriva.enums.DbOperation;
import it.vvfriva.enums.EnumsChiaviEsterne;
import it.vvfriva.enums.EnumsChiaviEsterneAction;
import it.vvfriva.interfaces.EntityInfo;
import it.vvfriva.interfaces.IDBManagerOperation;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;

@Service
@Transactional(rollbackFor = Exception.class)
public abstract class DbManagerStandard<T>  implements IDBManagerOperation<T> {
	
	
	@Autowired EntityManager em;
	@Autowired CrudRepository<T, Integer> repository;
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public T getObjById(Integer id) {
		return (T) repository.findById(id).get();
	}
	/**
	 * 
	 * @param object
	 * @throws Exception 
	 */
	@Transactional
	public void save(T object) throws Exception, CustomException {
		List<ResponseMessage> msg = new ArrayList<ResponseMessage>();
		boolean puoiProcedere = controllaCampiObbligatori(object, msg);
		if (!puoiProcedere) {
			throw new CustomException(msg);
		}
		operazionePrimaDiInserire(object);
		repository.save(object);
		operazioneDopoInserimento(object);
	}
	/**
	 * 
	 * @param object
	 * @throws Exception 
	 */
	@Transactional
	public void update(T object) throws Exception, CustomException {
		List<ResponseMessage> msg = new ArrayList<ResponseMessage>();
		boolean puoiProcedere = controllaCampiObbligatori(object, msg);
		if (!puoiProcedere) {
			throw new CustomException(msg);
		}
		puoiProcedere = controllaIntegritaReferenziale(object, DbOperation.UPDATE, msg);
		if (!puoiProcedere) {
			throw new CustomException(msg);
		}
		operazionePrimaDiModificare(object);
		repository.save(object);
		operazioneDopoModifica(object);
	}
	/**
	 * 
	 * @param object
	 * @throws Exception 
	 */
	@Transactional
	public void delete(T object) throws Exception, CustomException {
		List<ResponseMessage> msg = new ArrayList<ResponseMessage>();
		boolean puoiProcedere = false;
		puoiProcedere = controllaIntegritaReferenziale(object, DbOperation.DELETE, msg);
		if (!puoiProcedere) {
			throw new CustomException(msg);
		}
		operazionePrimaDiCancellare(object);
		repository.delete(object);
		operazioneDopoCancellazione(object);
	}
	/**
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean controllaIntegritaReferenziale(T object, DbOperation action, List<ResponseMessage> msg) {
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
}
