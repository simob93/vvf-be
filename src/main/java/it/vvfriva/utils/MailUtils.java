package it.vvfriva.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.vvfriva.models.MailObject;

/**
 *  classe di gestione invio MAIL
 *  
 * @author simone
 *
 */
public class MailUtils {
	
	final static Logger logger = LoggerFactory.getLogger(MailUtils.class);

	/**
	 * @return true (email inviata correttamente) false (problemi di invio email)
	 */
	public static boolean sendMailTo(MailObject objMail) {
		
		try {
			
			//recuper informazioni relative al serve di posto in uscita 
			Properties props = System.getProperties();
	    		    	
	    	props.put("mail.smtp.starttls.enable", "true");
	    	props.put("mail.smtp.auth", "true");
	    	props.put("mail.smtp.ssl.trust", "*");
	    	props.put("mail.smtp.host", Configs.getSMTP_HOST());
	    	props.put("mail.smtp.port", Configs.getSMTP_PORT()); 
	    	//props.put("mail.smtp.ssl.trust", "*");

	    	
	        if (Utils.isEmptyString(objMail.getFrom())) {
	        	throw new Exception(Messages.getMessage("mail.from.not.found"));
	        }
	        
	        logger.info("start autenticazione smpt server");
			Session mailSession = Session.getDefaultInstance(props, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(Configs.getMailFROM(), Configs.getSMTP_PASSWORD());
	            }
	        });
			MimeMessage msg = new MimeMessage(mailSession);
			  
	        msg.setFrom(new InternetAddress(objMail.getFrom()));
	        
	        if (!Utils.isEmptyList(objMail.getTo())) {
		        objMail.getTo().forEach(dest -> {
		        	try {
						msg.addRecipients(Message.RecipientType.TO, dest);
					} catch (MessagingException e) {
						e.printStackTrace();
					}	
		        });
	        } else {
	        	throw new Exception(Messages.getMessage("mail.dest.not.found"));
	        }
	        
	        msg.setSubject(objMail.getObject());
	        msg.setContent(objMail.getContent(),"text/html; charset=utf-8");
	        Transport.send(msg);
	        logger.info(String.format("Messaggio success: from %s to %s: ", objMail.getFrom(), String.join(",", objMail.getTo())));
	    	return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
		}
		return false;
	}

}
