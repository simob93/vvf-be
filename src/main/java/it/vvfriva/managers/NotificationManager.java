package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Scadenze;
import it.vvfriva.entity.Vigile;
import it.vvfriva.models.ModelNotification;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;

/**
 * 
 * @author simone
 *
 */
@Service
public class NotificationManager {
	
	final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired ScadenzeManager scadenzeManager;
	@Autowired VigileManager vigileManager;
	
	/**
	 * 
	 * @return
	 * @throws CustomException 
	 */
	public List<ModelNotification> list() throws CustomException {
		//recupero la lista delle scadenze
		List<ModelNotification> data = null;
		try {
			
			Date from =Utils.encodeDate(1899, 12, 31);
			Date to = Utils.dateAdd(new Date(), Calendar.MONTH, 6);
			
			//devo recuperare le scadenze da qui a sei mesi 
			List<Scadenze> listScadenze = scadenzeManager.listForReport(from, Utils.endOfDay(to), null, null);
			if (!Utils.isEmptyList(listScadenze)) {
				data = new ArrayList<ModelNotification>();
				ModelNotification not = null;
				for(Scadenze scadenza: listScadenze) {
					not = new ModelNotification();
					not.setIdRif(scadenza.getIdRiferimento());
					not.setIdVigile(scadenza.getIdVigile());
					not.setType(scadenza.getIdArea());
					not.setTypeFormatted(scadenza.getDescrFormatted());
					not.setDateExpiry(scadenza.getDateExpiration());
					//recupero nome vigile
					Vigile vigile = vigileManager.getObjById(scadenza.getIdVigile());
					if (vigile != null) {
						not.setNominativo(vigile.getFirstName() + " " + vigile.getLastName());
					}
					data.add(not);
				}
				
			}
		} catch (Exception ex) {
			log.error("Exceptio in function: "+ this.getClass().getCanonicalName() + ".list", ex);
			throw new CustomException(Messages.getMessage("search.ko"));
		}
		return data;
	}

}
