package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Vigile;
import it.vvfriva.models.ModelInfoVigili;
import it.vvfriva.models.VigileModel;
import it.vvfriva.repository.VigileRepository;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;

@Service
public class VigileManager extends DbManagerStandard<Vigile> {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	VigileRepository repository;
	@Autowired
	VigilePatentiManager vigilePatentiManager;
	@Autowired
	VigileCertificatiManager vigileCertificatiManager;

	@PersistenceContext
	EntityManager em;

	public ModelInfoVigili getInfoVigili() throws Exception {
		ModelInfoVigili data = new ModelInfoVigili();
		try {
			// totale vigili
			long countTotal = repository.count();
			data.setTotal((int) countTotal);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception in function: " + this.getClass().getCanonicalName() + ".getInfoVigili", ex);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}

	public List<VigileModel> list() {
		return list(false, null, false, null, null);
	}

	/**
	 * 
	 * @param assenti
	 * @param idSquadra
	 * @param attivi
	 * @return
	 * @throws Exception
	 */
	public List<VigileModel> list(Boolean assenti, Integer idSquadra, Boolean attivi) throws Exception {
		return list(assenti, idSquadra, attivi, null, null);
	}

	/**
	 * 
	 * @param dateStart
	 * @param dateEnd
	 * @param tipo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<VigileModel> list(Boolean assenti, Integer idSquadra, Boolean nonAttivi, Date dal, Date al) {
		List<VigileModel> data = null;
		assenti = Utils.coalesce(assenti, false);
		nonAttivi = Utils.coalesce(nonAttivi, true);
		dal = Utils.coalesce(dal, new Date());
		al = Utils.coalesce(al, new Date());

		if (!Utils.isValidId(idSquadra)) {
			idSquadra = null;
		}

		String condizioneAssenze = "";
		if (assenti) {
			condizioneAssenze += " left join assenze assenza on assenza.id_vigile = vig.id and assenza.id = (select tmp_ass.id from assenze tmp_ass where tmp_ass.dal <= :al and ((tmp_ass.al >= :dal ) or (tmp_ass.al is null)) and tmp_ass.id_vigile = vig.id limit 1) ";
		}

		String query = ""
				+ " select vig.first_name as firstName, vig.last_name as lastName, vig.id as id, servizio.id as idServizio, servizio.letter as lettera "
				+ (!Utils.isEmptyString(condizioneAssenze) ? ", assenza.id as idAssenza " : "") + "from vigile vig "
				+ "left join serv servizio on servizio.id_vigile = vig.id  and servizio.date_start = ("
				+ "			select max(appoS.date_start) " + "			from serv appoS "
				+ "			where appoS.id_vigile = vig.id  and  appoS.date_start <= :al and ( (appoS.date_end is null) or (appoS.date_end >=:dal))) "
				+ condizioneAssenze + "where ((cast(:idTeam as unsigned) is null) or (servizio.id_team = :idTeam))";
		if (!assenti) {
			query += " and not exists ( select ass.id from assenze ass where ass.id_vigile = vig.id and ass.dal <= :al and ((ass.al >= :dal)  or (ass.al is null)) ) ";
		}
		if (!nonAttivi) {
			query += " and servizio.id is not null";
		}
		query += " order by vig.last_name";
		Query sql = em.createNativeQuery(query);
		sql.setParameter("dal", dal).setParameter("al", al);
		sql.setParameter("idTeam", idSquadra);
		data = (List<VigileModel>) sql.unwrap(org.hibernate.Query.class)
				.setResultTransformer(Transformers.aliasToBean(VigileModel.class)).list();
		return data;
	}

	/**
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<Vigile> listPaged(Integer start, Integer limit, String search) throws Exception {

		if (!Utils.geZero(start)) {
			logger.error("Excepetion in function: 'listpaged', invalid filed start");
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ").append(Messages
					.getMessageFormatted("field.err.mandatory", new Object[] { Messages.getMessage("field.start") }));
			throw new Exception(sb.toString());
		}

		if (!Utils.isValidId(limit)) {
			logger.error("Excepetion in function: 'listpaged', invalid filed start");
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ").append(Messages
					.getMessageFormatted("field.err.mandatory", new Object[] { Messages.getMessage("field.start") }));
			throw new Exception(sb.toString());
		}

		List<Vigile> data = null;
		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Vigile> cq = cb.createQuery(Vigile.class);

			Root<Vigile> person = cq.from(Vigile.class);
			List<Predicate> predicates = new ArrayList<Predicate>();

			if (!Utils.isEmptyString(search)) {

				Predicate or = cb.or(cb.like(cb.upper(person.get("lastName")), "%" + search.toUpperCase() + "%"),
						cb.like(cb.upper(person.get("firstName")), "%" + search.toUpperCase() + "%"));
				predicates.add(or);
			}
			cq.where(predicates.toArray(new Predicate[0]));
			cq.orderBy(cb.asc(person.get("lastName")));
			data = em.createQuery(cq).setFirstResult(start).setMaxResults(limit).getResultList();

		} catch (Exception e) {
			logger.error("Exception in function: " + this.getClass().getCanonicalName() + ".list", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Vigile getById(Integer id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public boolean controllaCampiObbligatori(Vigile object, List<ResponseMessage> msg) {

		if (Utils.isEmptyString(object.getFirstName())) {
			logger.error("Can't persist record invalid field 'first name'");
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new Object[] { "nome" })));
			return false;
		}
		if (Utils.isEmptyString(object.getLastName())) {
			logger.error("Can't persist record invalid field 'last name'");
			msg.add(new ResponseMessage(
					Messages.getMessageFormatted("field.err.mandatory", new Object[] { "cognome" })));
			return false;
		}
		/* compongo la lista delle patenti splittata con il ";" */
		if (!Utils.isEmptyList(object.getListDrivingLicenses())) {
			object.setDrivingLicenses(Utils.implodeList(object.getListDrivingLicenses()));
		}
		return true;
	}

}
