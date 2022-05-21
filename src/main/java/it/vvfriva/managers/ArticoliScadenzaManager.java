package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.ArticoliScadenza;
import it.vvfriva.enums.EnumTrueFalse;
import it.vvfriva.enums.TipoScadenzaArticolo;
import it.vvfriva.models.ArticoliScadenzaRinnovoInput;
import it.vvfriva.models.ArticoliScadenzaSearch;
import it.vvfriva.models.ArticoliScadenzeDto;
import it.vvfriva.models.KeyValuePeriodo;
import it.vvfriva.repository.ArticoliScadenzaRepository;
import it.vvfriva.specifications.ArticoliScadenzaSpecification;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;

/**
 * author Simone
 */
@Service
public class ArticoliScadenzaManager extends DbManagerStandard<ArticoliScadenza> {

	@Autowired
	EntityManager em;

	@Autowired
	ArticoliScadenzaRepository repository;

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public List<ArticoliScadenzeDto> getArticoliScadenza(ArticoliScadenzaSearch search) {
		
		Specification<ArticoliScadenza> queryFilter = ArticoliScadenzaSpecification.ConIdArticolo(search.getArticoloId());
		
		if (search.isStorico()) {
			queryFilter = queryFilter.and(ArticoliScadenzaSpecification.ConStorico());
		} else {
			queryFilter = queryFilter.and(ArticoliScadenzaSpecification.ConScadenzaAttiva());
		}
		List<ArticoliScadenza> listScadenza = null;
		if (!search.isStorico()) {
			listScadenza = repository
					.findAll(queryFilter, Sort.by(Order.asc("dataScadenza")));
		} else {
			listScadenza = repository
					.findAll(queryFilter, Sort.by(Order.asc("idArticolo"), Order.asc("dataScadenza")));
			ArticoliScadenza prev = null;
			List<ArticoliScadenza> tmp = new ArrayList<ArticoliScadenza>();
			for (ArticoliScadenza scadenzeStorico : listScadenza) {
				if (prev != null && 
						!scadenzeStorico.getIdArticolo().equals(prev.getIdArticolo())) {
					tmp.add(prev);
				}
				prev = scadenzeStorico;
			}
			listScadenza = tmp;
		}

		return listScadenza.stream()
				.map(scadenza -> new ArticoliScadenzeDto(scadenza.getId(), scadenza.getIdArticolo(),
						scadenza.getArticolo().getDescrizione(), scadenza.getDataScadenza(), scadenza.getNote(),
						scadenza.getTipoScadenza().getDescrizione(), scadenza.getTipoScadenza().getTipoScadenzaId()))
				.collect(Collectors.toList());
	}
	/**
	 * 
	 * @param articoloId
	 * @return
	 */
	public List<KeyValuePeriodo> getStoricoRinnoviArticolo(int articoloId) {	
		Specification<ArticoliScadenza> queryFilter = ArticoliScadenzaSpecification
				.ConIdArticolo(articoloId)
				.and(ArticoliScadenzaSpecification.ConStorico());
		List<ArticoliScadenza> listScadenza = repository
				.findAll(queryFilter, Sort.by(Order.asc("idArticolo"), Order.asc("dataScadenza")));
		return listScadenza.stream()
				.map(scadenza -> new KeyValuePeriodo(scadenza.getDataRinnovo(), scadenza.getDataScadenza()))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param scadenzaId
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@org.springframework.transaction.annotation.Transactional
	public Integer rinnovaScadenzaArticolo(int scadenzaId, ArticoliScadenzaRinnovoInput object) {
		
		ArticoliScadenza nuovaScadenza = null;
		ArticoliScadenza scadenzaAttuale = this.getObjById(scadenzaId);
		scadenzaAttuale.setEffettuato(EnumTrueFalse.T);
		this.update(scadenzaAttuale);
		
		nuovaScadenza = new ArticoliScadenza();
		nuovaScadenza.setIdArticolo(scadenzaAttuale.getIdArticolo());
		nuovaScadenza.setNote(object.getNote());
		nuovaScadenza.setDataScadenza(object.getDataScadenza());
		nuovaScadenza.setTipoScadenza(TipoScadenzaArticolo.getById(object.getTipoScadenza()));
		// salvo la nuova scadenza
		this.save(nuovaScadenza);
		
		return nuovaScadenza.getId();
	}

	@Override
	public boolean controllaCampiObbligatori(ArticoliScadenza object, List<ResponseMessage> msg) {
		if (!Utils.isValidId(object.getIdArticolo())) {
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory",
					new Object[] { Messages.getMessage("field.idArticolo") })));
			return false;
		}
		if (!Utils.isValidDate(object.getDataScadenza())) {
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory",
					new Object[] { Messages.getMessage("field.dataScadenza") })));
			return false;
		}
		if (object.getTipoScadenza() == null) {
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory",
					new Object[] { Messages.getMessage("field.articolo.scadenza.tipo.scadeza") })));
			return false;
		}
		if (!Utils.isValidId(object.getId())) {
			//in fase di inserimento
			List<ArticoliScadenza> scadenzaArticolo = this.repository.findAll(
					ArticoliScadenzaSpecification.ConIdArticolo(object.getIdArticolo())
					.and(ArticoliScadenzaSpecification.ConScadenzaAttiva()));
			
			if (!Utils.isEmptyList(scadenzaArticolo)) {
				msg.add(new ResponseMessage(Messages.getMessage("scadenza.articolo.gia.inserita")));
				return false;
			}
		}
		return true;
	}
}
