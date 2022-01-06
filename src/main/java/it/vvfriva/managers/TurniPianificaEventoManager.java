package it.vvfriva.managers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.TurniPianificaEvento;
import it.vvfriva.repository.TurniPianificaEventoRepository;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;
/**
 * Manager per la gestione dei singoli eventi che compongo una pianificazione 
 * 
 *  
 * @author simone
 *
 */
@Service
public class TurniPianificaEventoManager extends DbManagerStandard<TurniPianificaEvento>  {

	private final Logger log = LoggerFactory.getLogger(TurniPianificaEventoManager.class);
 
	@Autowired EntityManager em;
	
	@Autowired TurniPianificaEventoRepository turnoPianificaEvento;
	
	
	/****************************************************************************************
	 * METODO DI SUPPORTO
	 ****************************************************************************************/
	
	/**
	 * 
	 * @param id
	 * @throws Exception 
	 */
	public void deleteTurniByPianificazione(Integer idPianificazione) throws Exception {
		try {

			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaDelete<TurniPianificaEvento> query = criteriaBuilder
					.createCriteriaDelete(TurniPianificaEvento.class);
			Root<TurniPianificaEvento> root = query.from(TurniPianificaEvento.class);
			query.where(criteriaBuilder.equal(root.get("idPianificazione"), idPianificazione));
			em.createQuery(query).executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			log.error(Utils.errorInMethod(e.getMessage()));
			throw new Exception(Messages.getMessage("operation.ko"));
		}
	}


	@Override
	public boolean controllaCampiObbligatori(TurniPianificaEvento object, List<ResponseMessage> msg)
			throws CustomException, Exception {
		return true;
	}

}
