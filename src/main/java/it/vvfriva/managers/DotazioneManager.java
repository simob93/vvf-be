package it.vvfriva.managers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Dotazione;
import it.vvfriva.repository.DotazioneRepository;
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
public class DotazioneManager extends DbManagerStandard<Dotazione> {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired DotazioneRepository repository;
	@Autowired EntityManager em;
	/**
	 * Ricerca tutte le dotazione di un determinato vigile
	 * 
	 * @param idVigile
	 * @return
	 * @throws Exception
	 */
	public List<Dotazione> list(Integer idVigile) throws Exception {
		
		if (!Utils.isValidId(idVigile)) {
			StringBuffer sbuf = new StringBuffer();
			sbuf.append(Messages.getMessage("search.ko")).append(": ").append(
					Messages.getMessageFormatted("item.not.valid", new Object[] { Messages.getMessage("field.idVigile") }));
			logger.error("Error on method: " + this.getClass().getCanonicalName() + ".list ", sbuf.toString());
			throw new Exception(sbuf.toString());
		}
		 List<Dotazione> data = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Dotazione> cr = cb.createQuery(Dotazione.class);
			Root<Dotazione> root = cr.from(Dotazione.class);
			
			cr.select(root);
			
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(cb.equal(root.get("idVigile"), idVigile));
			cr.orderBy(cb.desc(root.get("dataConsegna")));
		    cr.where(predicates.toArray(new Predicate[0]));
		    TypedQuery<Dotazione> query = em.createQuery(cr);
			data = query.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in method: " + this.getClass().getCanonicalName() + ".list", e.getMessage());
			throw new Exception(Messages.getMessage("search.ko"));
		} 
		return data;
	}
	/**
	 * @param idVigile
	 * @return lista delle ultime dotazioni consegnate 
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<Dotazione> listForPortlet(Integer idVigile) throws Exception {

		if (!Utils.isValidId(idVigile)) {
			StringBuffer sbuf = new StringBuffer();
			sbuf.append(Messages.getMessage("search.ko")).append(": ").append(
					Messages.getMessageFormatted("item.not.valid", new Object[] { Messages.getMessage("field.idVigile") }));
			logger.error("Error on method: " + this.getClass().getCanonicalName() + ".list ", sbuf.toString());
			throw new Exception(sbuf.toString());
		}
		 List<Dotazione> data = null;
		try {
			
			String sql = "Select dot.*, (Select art.descrizione from articoli art where art.id = dot.id_articolo) as descrizione  "
					+ "From vigile_dotazione dot "
					+ "where dot.id_vigile = :idVigile and "
					+ "dot.id = ("
							+ "Select appo.id "
							+ "From vigile_dotazione appo "
							+ "Where appo.id_vigile = dot.id_vigile and "
								  + "appo.id_articolo = dot.id_articolo "
							+ "Order by appo.data_consegna Desc, appo.id Desc  "
							+ "LIMIT 1"
					  + ") "
					  + "Order by dot.data_consegna Desc";
			
			Query query = em.createNativeQuery(sql)
					.setParameter("idVigile", idVigile);
			List<Object[]> queryResult = query.getResultList();
			if (!Utils.isEmptyList(queryResult)) {
				data = new ArrayList<Dotazione>();
				for (Object[] obj : queryResult) {
					Dotazione dot = new Dotazione();
					dot.setId((Integer)obj[0]);
					dot.setIdVigile((Integer)obj[1]);
					dot.setIdArticolo((Integer)obj[2]);
					dot.setQuantita((BigDecimal)obj[3]);
					dot.setNote((String)obj[4]);
					dot.setDataConsegna((Date)obj[5]);
					dot.setTaglia((String)obj[6]);
					dot.setDescrArticolo((String)obj[7]);
					data.add(dot);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in method: " + this.getClass().getCanonicalName() + ".list", e.getMessage());
			throw new Exception(Messages.getMessage("search.ko"));
		} 
		return data;
	}
	@Override
	public boolean controllaCampiObbligatori(Dotazione object, List<ResponseMessage> msg)
			throws CustomException, Exception {
		

		if (!Utils.isValidId(object.getIdVigile())) {
			logger.warn("Cant' persist record invalid field 'idVigile'"); 
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory",
					new Object[] { Messages.getMessage("field.idVgile") })));
			return false;
		}
		if (!Utils.isValidId(object.getIdArticolo())) {
			logger.warn("Cant' persist record invalid field 'idArticolo'"); 
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory",
					new Object[] { Messages.getMessage("field.idArticolo") })));
			return false;
		}
		if ((object.getQuantita() != null) && (object.getQuantita().compareTo(new BigDecimal("0")) <= 0)) {
			logger.warn("Cant' persist record invalid field 'quantita'"); 
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory",
					new Object[] { Messages.getMessage("field.quantita") })));
			return false;
		}
		if (!Utils.isValidDate(object.getDataConsegna())) {
			logger.warn("Cant' persist record invalid field 'dataConsegna'"); 
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory",
					new Object[] { Messages.getMessage("field.dataConsegna") })));
			return false;
		}
		return true;
	}

}
