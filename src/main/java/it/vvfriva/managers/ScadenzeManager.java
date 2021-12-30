package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.vvfriva.entity.Area;
import it.vvfriva.entity.Person;
import it.vvfriva.entity.Scadenze;
import it.vvfriva.entity.SettingScadenze;
import it.vvfriva.entity.VigileCertificati;
import it.vvfriva.entity.VigilePatenti;
import it.vvfriva.models.KeyValueColumn;
import it.vvfriva.models.KeyValueTipiScadenza;
import it.vvfriva.models.VigileModel;
import it.vvfriva.repository.ScadenzeRepository;
import it.vvfriva.utils.CostantiVVF;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;

@Service
public class ScadenzeManager extends DbManagerStandard<Scadenze> {
	
	final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private @Autowired AutowireCapableBeanFactory beanFactory;
	
	@Autowired
	ScadenzeRepository repository;
	
	@Autowired
	PersonScadenzeManager personScadenzeManager;

	@Autowired
	VigilePatentiManager vigilePatentiManager;
	
	@Autowired
	VigileCertificatiManager vigileCertificatiManager;
	
	@Autowired
	VigileManager vigileManager;
	
	@Autowired
	PersonManager personManager;
	
	@Autowired
	AreaManager areaManager;
	
	@PersistenceContext
	EntityManager em;
	/**
	 * 
	 * @param idVigile     - id vigile
	 * @param from         - dal
	 * @param to           - al
	 * @param idArea       - filtra per una o + particolare/i area/e (es: patenti di servizio)
	 * @param storico      - mostra anche quelle che non sono state rinnovate
	 * @return ritorna una lista di scadenze filtrate per i parametri in ingresso
	 * @throws Exception
	 */
	public List<Scadenze> listAll(Integer idVigile, Date from, Date to, List<Integer> idArea, Boolean storico) throws Exception {
		return listAll(idVigile, from, to, idArea, storico, null);
	}
	/**
	 * ritona tutte le scadenze
	 * @param idVigile    - identificativo del vigile
	 * @param from        - data inizio
	 * @param to          - data fine
	 * @param idArea      - argomento (patenti or certificati per il momento)
	 * @param storico     - informazioni storiche
	 * @return
	 * @throws Exception
	 */
	public List<Scadenze> listAll(Integer idVigile, Date from, Date to, List<Integer> idArea, Boolean storico, Sort sort) throws Exception {
		
		List<Scadenze> data = null;
		storico = Utils.coalesce(storico, false);
		
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Scadenze> cq = cb.createQuery(Scadenze.class);
		 
		    Root<Scadenze> scad = cq.from(Scadenze.class);
		    List<Predicate> predList = new ArrayList<Predicate>();
		    
		    predList.add((cb.or(
    			cb.isNull(scad.get("renew")),
    			cb.equal(scad.get("renew"), storico ? 1 : 0)
			)));
		    
		    if (Utils.isValidDate(to)) {
		    	to = Utils.startOfDay(to);
		    	predList.add(cb.lessThanOrEqualTo(scad.get("dateFrom"), to));
		    }
		    
		    if (Utils.isValidDate(from)) {
		    	from = Utils.startOfDay(from);
		    	predList.add(
	    			cb.or(
    					cb.greaterThanOrEqualTo(scad.get("dateExpiration"), from),
    					cb.isNull(scad.get("dateExpiration"))
    				)
    			);
		    }
		    if (!Utils.isEmptyList(idArea)) {
		    	In<Integer> inClause = cb.in(scad.get("idArea"));
		    	for (Integer area : idArea) {
		    		inClause.value(area);
		    	}
		    	predList.add(inClause);
		    }
		    predList.add(cb.equal(scad.get("idVigile"), idVigile));
		    cq.where(predList.toArray(new Predicate[predList.size()]));
		    cq.orderBy(cb.asc(scad.get("idArea")), cb.asc(scad.get("dateExpiration")));
		    
		    data = em.createQuery(cq).getResultList();	  
		    
			if (!Utils.isEmptyList(data)) {
				for (Scadenze scadenza : data) {
					
					if (scadenza.getIdArea().compareTo(CostantiVVF.AREA_PATENTI_SERVIZIO) == 0) {
						VigilePatenti vgilePatente = vigilePatentiManager.getObjById(scadenza.getIdRiferimento());
						scadenza.setDescrFormatted(vgilePatente.getDescrPerson());
						scadenza.setIdPerson(vgilePatente.getIdPerson());
					}
					
					if (scadenza.getIdArea().compareTo(CostantiVVF.AREA_AUTORIZZAZIONI) == 0) {
						VigileCertificati vigileCertificato = vigileCertificatiManager.getObjById(scadenza.getIdRiferimento());
						scadenza.setDescrFormatted(vigileCertificato.getDescrPerson());
						scadenza.setIdPerson(vigileCertificato.getIdPerson());
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Excpetion in function: " + this.getClass().getCanonicalName() + ".listAll", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
	/**
	 * 
	 * @param idVigile
	 * @param idArea
	 * @return ritorna una lista in formato Codice, Valore delle voci di scadenza
	 * @throws Exception
	 */
	public List<KeyValueTipiScadenza> getCboxTipiScadenza(Integer idVigile, Integer idArea) throws Exception {
		
		if (!Utils.isValidId(idVigile)) {
			logger.error("Excepetion in function: 'getCboxTipiScadenza', invalid filed idVigile");
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
					.append(Messages.getMessageFormatted("field.err.mandatory", new Object[] { "idVigile" }));
			throw new Exception(sb.toString());
		}
		
		if (!Utils.isValidId(idArea)) {
			logger.error("Excepetion in function: 'getCboxTipiScadenza', invalid filed idArea");
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
					.append(Messages.getMessageFormatted("field.err.mandatory", new Object[] { "idArea" }));
			throw new Exception(sb.toString());
		}
		List<KeyValueTipiScadenza> data = new ArrayList<KeyValueTipiScadenza>();
		try {
			
			if (Utils.integerCompareTo(idArea, 5) == 0) {
				data.addAll(vigilePatentiManager.getCbox(idVigile)); 
			} else if(Utils.integerCompareTo(idArea, 2) == 0) {
				data.addAll(vigileCertificatiManager.getCbox(idVigile));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in function: " + this.getClass().getCanonicalName() + ".getCboxTipiScadenza", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
	/**
	 * Il metodo in base alla voce personalizzabile, calcola la data di scadenza
	 * prevista. 
	 * 
	 * @param idPerson
	 * @param dateStart
	 * @return
	 * @throws Exception 
	 */
	public Date calcExpirationDate(Integer idPerson, Date dateStart) throws Exception {
		if (!Utils.isValidDate(dateStart)) {
			log.error("Can't persist record invalid field 'dateStart'");
			StringBuilder sb = new StringBuilder(Messages.getMessage("search.ko")).append(": ")
			.append(Messages.getMessageFormatted("field.err.mandatory", new Object[] {"dateStart"}));
			throw new Exception(sb.toString());
		}
		
		if (!Utils.isValidId(idPerson)) {
			log.error("Can't persist record invalid field 'idPerson'");
			StringBuilder sb = new StringBuilder(Messages.getMessage("search.ko")).append(": ")
			.append(Messages.getMessageFormatted("field.err.mandatory", new Object[] {"idPerson"}));
			throw new Exception(sb.toString());
		}
		Date dateCalc = null;
		try {
			
			SettingScadenze person = personScadenzeManager.getByIdPerson(idPerson);
			if (person != null) {
				Integer repeat = person.getExpirationEvery();
				String type = person.getExpirationType();
				
				if (Utils.stringCompareTo(type, CostantiVVF.ANNUALE) == 0) {
					dateCalc = Utils.dateAdd(dateStart, Calendar.YEAR, repeat);
				} else {
					dateCalc = Utils.dateAdd(dateStart, Calendar.MONTH, repeat);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in function: " + this.getClass().getCanonicalName() + ".calcExpirationDate", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return dateCalc;
	}
	/**
	 * il metodo viene eseguito in fase di creazione tabellone di scadenze, 
	 * ritorna le colonne possibili (abilitate alla scadenza)
	 * @param cmp
	 * @return
	 * @throws Exception
	 */
	public List<KeyValueColumn> getColumnsTabel(String cmp) throws Exception {
		List<KeyValueColumn> data = new ArrayList<KeyValueColumn>();
		try {
			List<Person> listPerson = null;
			if (Utils.isEmptyString(cmp)) {
				listPerson = personManager.list(Arrays.asList(CostantiVVF.AREA_PATENTI_SERVIZIO, CostantiVVF.AREA_AUTORIZZAZIONI), null, null, false);
			} else {
				listPerson = personManager.list(null, cmp, null, false);
			}
			//data.add(new KeyValue(null, "nominativo", "Nominativo"));
			if(!Utils.isEmptyList(listPerson)) {
				for(Person person: listPerson) {
					KeyValueColumn keyValueColumn = new KeyValueColumn(person.getId(), CostantiVVF.pref_prs + person.getId(), person.getName());
					keyValueColumn.setIdArea(person.getIdArea());
					Area area = areaManager.getObjById(person.getIdArea());
					if (area != null) {
						keyValueColumn.setName(area.getName());
					}
					data.add(keyValueColumn);
				}	
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Exception in function: " + this.getClass().getCanonicalName() + ".getColumnTabel", ex);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
	/**
	 * Il metodo calcola il tabellone di scadenza
	 * @return json tabel
	 * @throws Exception
	 */
	public String cal() throws Exception {
		String result = "";
		try {
			//attivi non - attivi - in attesa
			List<VigileModel> listaVigili = vigileManager.list();
			List<Person> listPerson = personManager.list(Arrays.asList(CostantiVVF.AREA_PATENTI_SERVIZIO, CostantiVVF.AREA_AUTORIZZAZIONI), null, null, false);
			
			//json da manadare alla gui
			JsonArray jsonArray = new JsonArray();
			
			//per ogni vigile
			
			if (!Utils.isEmptyList(listaVigili)) {
				for (VigileModel vigile: listaVigili) {
					JsonObject jObj = new JsonObject();
					jObj.addProperty("idVigile", vigile.getId());
					jObj.addProperty("nominativo", vigile.getFirstName() + " " + vigile.getLastName());
					
					List<Integer> idPersonCerrificati = 
							vigileCertificatiManager.getCbox(vigile.getId(), false)
							.stream()
							.map(certificato -> certificato.getIdPerson())
							.collect(Collectors.toList());
							
					//scadenze del vigile
					
					List<Scadenze> listScadenze = this.listAll(vigile.getId(), Utils.encodeDate(1899, 12, 31), Utils.endOfDay(new Date()), null, null);
					for(Person person: listPerson) {
						/*
						 * se non � abilita la scadenza verifico se il vigile possiede l'autorizzazione
						 * e la includo nella gestione come abilitata
						 */
						JsonObject jsonScadeza = null; 
						if (!person.getEnabledExpiry()) {
							if (idPersonCerrificati.contains(person.getId())) {
								jsonScadeza = new JsonObject(); 
								jsonScadeza.addProperty("stato", CostantiVVF.EVENTO_VALIDO);

							}
						}
						jObj.add(CostantiVVF.pref_prs + person.getId().toString() ,jsonScadeza);
					}
								
					
					for (Scadenze scadenza: listScadenze) {

						JsonObject jsonScadeza = new JsonObject(); 
						jsonScadeza.addProperty("stato", scadenza.getStato());
						jsonScadeza.addProperty("dateExpiration", scadenza.getDateExpirationFormatted());
						jObj.add(CostantiVVF.pref_prs + scadenza.getIdPerson().toString(), jsonScadeza);
						
					}
					jsonArray.add(jObj);
				}
			}
			result = jsonArray.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Exception in function: " + this.getClass().getCanonicalName() + ".calc", ex);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return result;
	}
	/**
	 * 
	 * @param idCert
	 * @param idLicense
	 * @param dateFrom
	 * @param dateExipration
	 * @param idArea
	 * @return
	 * @throws Exception 
	 */
	public Scadenze insertExp(Integer idRiferimento, Date dateFrom, Date dateExipration, Integer idArea, Integer idVigile) throws Exception {
		Scadenze scadenza = null;
		try {
			
			scadenza = new Scadenze();
			scadenza.setDateFrom(dateFrom);
			scadenza.setDate(new Date());
			scadenza.setDateExpiration(dateExipration);
			scadenza.setIdArea(idArea);
			scadenza.setIdRiferimento(idRiferimento);
			scadenza.setIdVigile(idVigile);
			this.save(scadenza);
			
		} catch (CustomException ex) {
			ex.printStackTrace();
			log.error("Exception in function: " + this.getClass().getCanonicalName() + ".insertExp", ex);
			throw new CustomException(ex.getErrorList());
		}
		return scadenza;
	}
	
	/**
	 * 
	 * @param date
	 * @param idVigilePatente
	 * @param idVigileCertificato
	 * @param renew
	 * @return
	 * @throws Exception
	 */
	private Scadenze getLastFrom(Date date, Integer idRiferimento, Integer idArea, Integer renew) throws Exception {
		List<Scadenze> data = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Scadenze> cq = cb.createQuery(Scadenze.class);
		 
		    Root<Scadenze> scad = cq.from(Scadenze.class);
		    List<Predicate> predList = new ArrayList<Predicate>();

		    if (!Utils.isValidId(renew)) {
				predList.add((cb.or(
						cb.isNull(scad.get("renew")),
						cb.equal(scad.get("renew"), 0)
				)));
			} else {
		    	predList.add(cb.equal(scad.get("renew"), 1));
			}
		    
		    if (Utils.isValidDate(date)) {
		    	predList.add(cb.lessThan(scad.get("dateFrom"), date));
		    }
		    
		    if (Utils.isValidId(idRiferimento)) {
		    	predList.add(cb.equal(scad.get("idRiferimento"), idRiferimento));
		    }
		    
		    if (Utils.isValidId(idArea)) {
		    	predList.add(cb.equal(scad.get("idArea"), idArea));
		    }
		    
		    cq.where(predList.toArray(new Predicate[predList.size()]));
		    cq.orderBy(cb.desc(scad.get("dateFrom")));
		    
		    data = em.createQuery(cq)
		    		.setMaxResults(1)
	    			.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in function" + this.getClass().getCanonicalName() + "getLastFrom", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return Utils.isEmptyList(data) ? null : data.get(0);
	}
	/**
	 * 
	 * @param from
	 * @param to
	 * @param asList
	 * @return
	 * @throws Exception 
	 */
	public List<Scadenze> listForReport(Date from, Date to, List<Integer> aree, List<Integer> idsVigile) throws Exception {
		
		if (!Utils.isValidDate(from)) {
			log.error("Invalid filed dateFrom");
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
			.append(Messages.getMessageFormatted("field.err.mandatory", new String[] {"dateFrom"}));
			throw new Exception(sb.toString());
		}
		if (!Utils.isValidDate(to)) {
			log.error("Invalid filed dataTo");
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
			.append(Messages.getMessageFormatted("field.err.mandatory", new String[] {"dataTo"}));
			throw new Exception(sb.toString());
		}
		
				
		List<Scadenze> data = null;
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Scadenze> cq = cb.createQuery(Scadenze.class);
		    Root<Scadenze> scad = cq.from(Scadenze.class);
		    List<Predicate> predList = new ArrayList<Predicate>();
		   
	    	predList.add(cb.lessThanOrEqualTo(scad.get("dateExpiration"), to));
	    	predList.add(cb.greaterThanOrEqualTo(scad.get("dateExpiration"), from));
		    
	    	if (!Utils.isEmptyList(aree)) {
		    	In<Integer> inClause = cb.in(scad.get("idArea"));
		    	for (Integer area: aree) {
		    		inClause.value(area);
		    	}
		    	predList.add(inClause);
	    	}
	    	if (!Utils.isEmptyList(idsVigile)) {
		    	In<Integer> inClauseVigile = cb.in(scad.get("idVigile"));
		    	for (Integer idVigile: idsVigile) {
		    		inClauseVigile.value(idVigile);
		    	}
			    predList.add(inClauseVigile);

	    	}
	    	
		    cq.where(predList.toArray(new Predicate[predList.size()]));
		    cq.orderBy(cb.asc(scad.get("dateExpiration")));
		    data = em.createQuery(cq).getResultList();	  
			
			if (!Utils.isEmptyList(data)) {
				for (Scadenze scadenza: data) {
					if (scadenza.getIdArea().compareTo(CostantiVVF.AREA_AUTORIZZAZIONI) == 0) {
						scadenza.setDescrFormatted(vigileCertificatiManager.getObjById(scadenza.getIdRiferimento()).getDescrPerson());
					} else if (scadenza.getIdArea().compareTo(CostantiVVF.AREA_PATENTI_SERVIZIO) == 0) {
						scadenza.setDescrFormatted(vigilePatentiManager.getObjById(scadenza.getIdRiferimento()).getDescrPerson());
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in function" + this.getClass().getCanonicalName() + "listForReport", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
	
	@Override
	public boolean controllaCampiObbligatori(Scadenze object, List<ResponseMessage> msg) 
			throws CustomException, Exception {
		if (!Utils.isValidDate(object.getDateFrom())) {
			log.error("Can't persist record invalid field 'expiration date'");
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new Object[] {"expiration date"})));
			return false;
		}
		
		if (!Utils.isValidId(object.getIdRiferimento())) {
			log.error("Can't persist record invalid field 'idRiferimento'");
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new Object[] {"idRif"})));
			return false;
		}
		return true;
	}
	
	@Override
	public void operazioneDopoInserimento(Scadenze object) throws Exception, CustomException {
		Scadenze scadenza = getLastFrom(object.getDateFrom(), object.getIdRiferimento(), object.getIdArea(), 0);
		if (scadenza != null) {
			scadenza.setUpdateData(new Date());
			scadenza.setRenew(1);
			this.update(object);
		}
	}
	@Override
	public void operazionePrimaDiCancellare(Scadenze object) throws Exception, CustomException {
		Scadenze scadenza = getLastFrom(object.getDateFrom(), object.getIdRiferimento(), object.getIdArea(), 1);
		if (scadenza != null) {
			scadenza.setRenew(null);
			scadenza.setUpdateData(null);
			this.update(object);
		}
	}
}
