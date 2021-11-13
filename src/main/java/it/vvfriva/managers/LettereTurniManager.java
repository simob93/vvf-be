package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Servizio;
import it.vvfriva.enums.Lettere;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;
/**
 * Manager per la gestione del campo lettera vigile. 
 * 
 * @author simone
 *
 */
@Service
public class LettereTurniManager {
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired ServizioManager servizioManager;
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<String> list(Integer idSquadra) throws Exception {
		List<String> result = new ArrayList<>();
		try {
			//se ho un idSquadra valido
			List<String> appo_lettere = new ArrayList<String>();
			if (Utils.isValidId(idSquadra)) {
				List<Servizio> servizi = servizioManager.list(null, new Date(), new Date(), idSquadra, null, null);
				if (!Utils.isEmptyList(servizi)) {
					servizi.forEach(serv -> {
						if (!appo_lettere.contains(serv.getLetter())) { appo_lettere.add(serv.getLetter()); }
					});
				}
			}
			for(Lettere lettera: Lettere.values()) {
				if (!appo_lettere.contains(lettera.toString())) { 
					result.add(lettera.toString());
				}
			}
		} catch (Exception e) {
			logger.error("Exception in function: " + this.getClass().getCanonicalName() + ".list", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return result;
	}

}
