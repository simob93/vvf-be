package it.vvfriva.managers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
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
import it.vvfriva.models.DotazioneDto;
import it.vvfriva.models.KeyValueDate;
import it.vvfriva.models.ModelDotazionePortlet;
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

	@Autowired
	DotazioneRepository repository;
	@Autowired
	EntityManager em;

	/**
	 * Ricerca tutte le dotazione di un determinato vigile
	 * 
	 * @param idVigile
	 * @return
	 * @throws Exception
	 */
	public List<KeyValueDate> listKeyValueDate(Integer idVigile) throws Exception {

		if (!Utils.isValidId(idVigile)) {
			StringBuffer sbuf = new StringBuffer();
			sbuf.append(Messages.getMessage("search.ko")).append(": ").append(Messages
					.getMessageFormatted("item.not.valid", new Object[] { Messages.getMessage("field.idVigile") }));
			logger.error("Error on method: " + this.getClass().getCanonicalName() + ".list ", sbuf.toString());
			throw new Exception(sbuf.toString());
		}
		List<KeyValueDate> data = null;
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
			List<Dotazione> elencoDotazioni = query.getResultList();

			if (!Utils.isEmptyList(elencoDotazioni)) {
				data = elencoDotazioni
						.stream().map(dotazione -> new KeyValueDate(dotazione.getId(),
								dotazione.getArticolo().getDescrizione(), null, dotazione.getDataConsegna()))
						.collect(Collectors.toList());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in method: " + this.getClass().getCanonicalName() + ".list", e.getMessage());
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}

	public List<DotazioneDto> listDotazioni(Integer idVigile) throws Exception {

		if (!Utils.isValidId(idVigile)) {
			StringBuffer sbuf = new StringBuffer();
			sbuf.append(Messages.getMessage("search.ko")).append(": ").append(Messages
					.getMessageFormatted("item.not.valid", new Object[] { Messages.getMessage("field.idVigile") }));
			logger.error("Error on method: " + this.getClass().getCanonicalName() + ".list ", sbuf.toString());
			throw new Exception(sbuf.toString());
		}
		List<DotazioneDto> data = new ArrayList<DotazioneDto>();
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
			List<Dotazione> elencoDotazioni = query.getResultList();
			for (Dotazione dotazioneVigile : elencoDotazioni) {
				DotazioneDto dto = new DotazioneDto(dotazioneVigile);
				data.add(dto);
			}

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
	public List<ModelDotazionePortlet> listForPortlet(Integer idVigile) throws Exception {

		if (!Utils.isValidId(idVigile)) {
			StringBuffer sbuf = new StringBuffer();
			sbuf.append(Messages.getMessage("search.ko")).append(": ").append(Messages
					.getMessageFormatted("item.not.valid", new Object[] { Messages.getMessage("field.idVigile") }));
			logger.error("Error on method: " + this.getClass().getCanonicalName() + ".list ", sbuf.toString());
			throw new Exception(sbuf.toString());
		}
		List<ModelDotazionePortlet> result = new ArrayList<ModelDotazionePortlet>();
		try {

//			List<Dotazione> elencoDotazioni = (List<Dotazione>) repository.findAll();

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Dotazione> cr = cb.createQuery(Dotazione.class);
			Root<Dotazione> root = cr.from(Dotazione.class);

			cr.select(root);

			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(cb.equal(root.get("idVigile"), idVigile));
			cr.orderBy(cb.desc(root.get("dataConsegna")), cb.desc(root.get("idArticolo")));
			cr.where(predicates.toArray(new Predicate[0]));
			TypedQuery<Dotazione> query = em.createQuery(cr);
			List<Dotazione> elencoDotazioni = query.getResultList();
//			
			LinkedHashMap<Integer, Dotazione> mappaGroup = new LinkedHashMap<Integer, Dotazione>();
			if (!Utils.isEmptyList(elencoDotazioni)) {
				for (Dotazione dotazioneVigile : elencoDotazioni) {
					if (!mappaGroup.containsKey(dotazioneVigile.getIdArticolo())) {
						mappaGroup.put(dotazioneVigile.getIdArticolo(), dotazioneVigile);
					}
				}
			}
			elencoDotazioni = new ArrayList<Dotazione>(mappaGroup.values());
			for (Dotazione dotazione : elencoDotazioni) {
				result.add(new ModelDotazionePortlet(dotazione.getArticolo().getDescrizione(),
						dotazione.getDataConsegna()));
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in method: " + this.getClass().getCanonicalName() + ".list", e.getMessage());
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return result;
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

	/**
	 * 
	 * @param id
	 * @return
	 */
	public DotazioneDto getDotazione(Integer id) {
		Dotazione dotazione = repository.findById(id).get();
		if (dotazione != null) {
			return new DotazioneDto(dotazione);
		}
		return null;
	}
}
