package it.vvfriva.managers;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Articoli;
import it.vvfriva.exception.UserFriendlyException;
import it.vvfriva.models.ArticoliSearch;
import it.vvfriva.repository.ArticoliRepository;
import it.vvfriva.specifications.ArticoliSpecification;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;

/**
 * @author simone
 *
 */
@Service
public class ArticoliManager extends DbManagerStandard<Articoli> {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ArticoliRepository repository;
	@Autowired
	EntityManager em;

	/**
	 * Ritorna tutti gli articoli
	 * 
	 * @param idCategoria
	 * @param idDeposito
	 * @param descrizione
	 * @return
	 * @throws Exception
	 */
	public List<Articoli> list(ArticoliSearch articoliSearch) {

		List<Articoli> data = repository.findAll(ArticoliSpecification.ConDescrizione(articoliSearch.getDescrizione())
				.and(ArticoliSpecification.ConCategoriaId(articoliSearch.getCategoriaId())
						.and(ArticoliSpecification.ConDepositoId(articoliSearch.getDepositoId()))
						.and(ArticoliSpecification.ConGestioneScadenza(articoliSearch.isConGestScadenza()))));

		return data;
	}

	@Override
	public boolean controllaCampiObbligatori(Articoli object, List<ResponseMessage> msg) {

		if (Utils.isEmptyString(object.getDescrizione())) {
			logger.warn("Can't persist record 'Articoli'  invalid field 'descrizione'");
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory",
					new String[] { Messages.getMessage("field.descrizione") })));
			return false;
		}
		return true;
	}

	@Override
	public void operazionePrimaDiInserire(Articoli object) {
		if (!Utils.isEmptyString(object.getCodice())) {
			List<Articoli> articolo = repository.findAll(ArticoliSpecification.ConCodice(object.getCodice()));
			if (articolo.size() > 0) {
				throw new UserFriendlyException(Messages.getMessage("articoli.codice.alreadyexist"));
			}
		}
	}

	@Override
	public void operazionePrimaDiModificare(Articoli object) {
		if (!Utils.isEmptyString(object.getCodice())) {
			List<Articoli> articolo = repository.findAll(ArticoliSpecification.ConCodice(object.getCodice())
					.and(ArticoliSpecification.ConIdDiversoDa(object.getId())));
			if (articolo.size() > 0) {
				throw new UserFriendlyException(Messages.getMessage("articoli.codice.alreadyexist"));
			}
		}
	}
}
