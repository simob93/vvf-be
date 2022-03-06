package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.TurniPianificaSaltoTurno;
import it.vvfriva.repository.TurniPianificaSaltoTurnoRepository;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
/**
 * 
 * @author simone
 *
 */
@Service
public class TurniPianificaSaltoTurnoManager extends DbManagerStandard<TurniPianificaSaltoTurno>  {
	private final Logger log = LoggerFactory.getLogger(TurniPianificaSaltoTurnoManager.class);
	@Autowired EntityManager em;
	@Autowired TurniPianificaSaltoTurnoRepository turniPianificaSaltoTurnoRepository;
	/**
	 * 
	 * @param idPianificazione
	 * @throws Exception
	 */
	public void deleteByIdPianificazione(Integer idPianificazione) throws Exception {
		try {
			
			CriteriaBuilder criteriaBuilder  = em.getCriteriaBuilder();
			CriteriaDelete<TurniPianificaSaltoTurno> query = criteriaBuilder.createCriteriaDelete(TurniPianificaSaltoTurno.class);
			Root<TurniPianificaSaltoTurno> root = query.from(TurniPianificaSaltoTurno.class);
			query.where(criteriaBuilder.equal(root.get("idPianificazione"), idPianificazione));
			em.createQuery(query).executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(Messages.getMessage("operation.ko"));
		}
	}
	/**
	 * 
	 * @param idPianificazione
	 * @return
	 * @throws Exception
	 */
	public List<TurniPianificaSaltoTurno> listByPianificazione(Integer idPianificazione) throws Exception {
		List<TurniPianificaSaltoTurno> data = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<TurniPianificaSaltoTurno> cq = cb.createQuery(TurniPianificaSaltoTurno.class);
		    Root<TurniPianificaSaltoTurno> evento = cq.from(TurniPianificaSaltoTurno.class);
		    List<Predicate> predicates = new ArrayList<Predicate>();

		    predicates.add(cb.equal(evento.get("idPianificazione"), idPianificazione));
		    cq.orderBy(cb.asc(evento.get("servizio").get("letter")));
		    cq.where(predicates.toArray(new Predicate[0]));
		    data = em.createQuery(cq).getResultList();
		    
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
	
	@Override
	public boolean controllaCampiObbligatori(TurniPianificaSaltoTurno object, List<ResponseMessage> msg) {
		return true;
	}

}
