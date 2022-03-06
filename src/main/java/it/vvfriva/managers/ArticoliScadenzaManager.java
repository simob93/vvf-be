package it.vvfriva.managers;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.ArticoliScadenza;
import it.vvfriva.enums.EnumTrueFalse;
import it.vvfriva.enums.TipoScadenzaArticolo;
import it.vvfriva.models.ArticoliScadenzaRinnovoInput;
import it.vvfriva.models.ArticoliScadenzaSearch;
import it.vvfriva.models.ArticoliScadenzeDto;
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
		List<ArticoliScadenza> listScadenza = repository
				.findAll(ArticoliScadenzaSpecification.ConIdArticolo(search.getArticoloId())
						.and(ArticoliScadenzaSpecification.ConScadenzeAttive()));
		return listScadenza.stream()
				.map(scadenza -> new ArticoliScadenzeDto(scadenza.getId(), scadenza.getIdArticolo(),
						scadenza.getArticolo().getDescrizione(), scadenza.getDataRinnovo(), scadenza.getDataScadenza()))
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
		return true;
	}
}
