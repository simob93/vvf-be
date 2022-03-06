package it.vvfriva.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Utenti;
import it.vvfriva.managers.DbManagerStandard;
import it.vvfriva.managers.UtentiManager;
import it.vvfriva.models.ChangePasswordModel;
/**
 * 
 * @author simone
 *
 */
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.MailObject;
import it.vvfriva.services.auth.SecurityService;
import it.vvfriva.utils.CostantiVVF;
import it.vvfriva.utils.MailUtils;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;

@Service
public class UtentiService extends DbServiceStandard<Utenti> {

	
	@Autowired
	UtentiManager utentiManager;
	@Autowired
	PasswordEncoder bcryptEncoder;

	@Override
	public DbManagerStandard<Utenti> getManager() {
		return utentiManager;
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	public JsonResponse<Utenti> save(Utenti object) {
		
		object.setAbilitato(true);
		object.setPrimoAcesso(false);
		String pwd = SecurityService.generateRandomPasswordInt();
		object.setPwd(bcryptEncoder.encode(pwd));
		
		JsonResponse<Utenti> resp = super.save(object);
		if (resp != null && resp.getSuccess() == true) {
			/*
			 * procedo con il notificare all'utente registrato, i suoi estremi per poter
			 * accedere alla piattaforma
			 */

			String content = String.format(
					"Di seguito sono riportare le informazioni per accedere al gestionale: " + "<br>Nome utente: %s "
							+ "<br>Password (Provvisoria): %s"
							+ "<br>Al primo accesso al gestionale ti sarà richiesto di cambiare la password"
							+ "<br>Se non sai perchè hai ricevuto questa e-mail, cancellala pure.\r\n"
							+ "<br><hr>Non rispondere a questa email perche viene generata automaticamente",
					object.getUsername(), pwd);
			MailObject obj = new MailObject(CostantiVVF.MAIL_OBJECT_NEW_ACCOUNT, object.getEmail(), content);
			boolean success = MailUtils.sendMailTo(obj);
			if (!success) {
				// notifico comunque l'errore
				resp.getMessage().add(new ResponseMessage(Messages.getMessage("mail.send.ko")));
			}
		}
		return resp;
	}

	/**
	 * metodo per il cambio password
	 * 
	 * @param changePasswordModel
	 * @return
	 */
	public JsonResponse<Boolean> changePassword(ChangePasswordModel changePasswordModel) {
		boolean success = true;
		String message = null;
		JsonResponse<Boolean> result = null;
		Boolean data = false;
		try {
			if (Utils.isEmptyString(changePasswordModel.getOldPassword())) {
				logger.error(Utils.errorInMethod("invalid field 'oldPassword'"));
				return new JsonResponse<Boolean>(false,
						Messages.getMessageFormatted("field.err.mandatory", new String[] { "vecchia password" }),
						false);
			}
			if (Utils.isEmptyString(changePasswordModel.getNewPassword())) {
				logger.error(Utils.errorInMethod("invalid field 'newPassword'"));
				return new JsonResponse<Boolean>(false,
						Messages.getMessageFormatted("field.err.mandatory", new String[] { "nuova password" }), false);
			}

			if (Utils.stringEquals(changePasswordModel.getNewPassword(), changePasswordModel.getOldPassword(), false)) {
				return new JsonResponse<Boolean>(false, Messages.getMessage("change.password.old.not.same.new"), false);
			}
			Utenti utente = utentiManager
					.getByUserName(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
							.getUsername());
			if (utente.getPrimoAcesso() != null && utente.getPrimoAcesso() == false) {
				utente.setPrimoAcesso(true);
			}
			utente.setPwd(bcryptEncoder.encode(changePasswordModel.getNewPassword()));
			utentiManager.update(utente);

			String content = String.format(
					"Gentile utente <br> " + "La sua password è stata cambiata correttamente."
							+ "<br>Se non sai perchè hai ricevuto questa e-mail, cancellala pure.\r\n"
							+ "<br><hr>Non rispondere a questa email perche viene generata automaticamente",
					utente.getUsername());
			MailObject obj = new MailObject(CostantiVVF.MAIL_OBJECT_CHANGE_PASSWORD_ACCOUNT, utente.getEmail(), content);
			MailUtils.sendMailTo(obj);
			data = true;
			message = Messages.getMessage("change.password.ok");
		} catch (Exception e) {
			success = false;
			data = false;
			e.printStackTrace();
			message = Messages.getMessage("change.password.ko");
		} finally {
			result = new JsonResponse<Boolean>(success, message, data);
		}
		return result;
	}

	public JsonResponse<List<Utenti>> listUtenti() {
		boolean success = true;
		String message = null;
		List<Utenti> data = null;
		JsonResponse<List<Utenti>> result = null;
		try {
			data = utentiManager.listUtenti();
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			success = false;
			e.printStackTrace();
		} finally {
			result = new JsonResponse<List<Utenti>>(success, message, data);
		}
		return result;
	}

	public JsonResponse<Utenti> getById(Integer id) {
		boolean success = true;
		String message = null;
		Utenti data = null;
		JsonResponse<Utenti> result = null;
		try {
			data = utentiManager.getObjById(id);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			success = false;
			e.printStackTrace();
		} finally {
			result = new JsonResponse<Utenti>(success, message, data);
		}
		return result;
	}
	/**
	 * 
	 * @param id
	 * @param b
	 * @return
	 */
	public JsonResponse<Utenti> enableDisable(Integer id, boolean attiva) {
		boolean success = true;
		String message = null;
		JsonResponse<Utenti> result = null;
		Utenti data = null;
		try {
			data = utentiManager.getObjById(id);
			if (data != null) {
				data.setAbilitato(attiva);
				utentiManager.update(data);
			}
			message = Messages.getMessage("operation.ok");
		} catch (Exception e) {
			message = Messages.getMessage("operation.ko");
			success = false;
			e.printStackTrace();
		} finally {
			result = new JsonResponse<Utenti>(success, message, data);
		}
		return result;
	}
	/**
	 * 
	 * @param id
	 * @param b
	 * @return
	 */
	public JsonResponse<Utenti> resetPassword(Integer id) {
		boolean success = true;
		String message = null;
		JsonResponse<Utenti> result = null;
		Utenti data = null;
		try {
			data = utentiManager.getObjById(id);
			if (data != null) {
				data.setPrimoAcesso(false);
				String randomPwd = SecurityService.generateRandomPasswordInt();
				data.setPwd(bcryptEncoder.encode(randomPwd));
				utentiManager.update(data);
				String content = String.format(
						"Di seguito sono riportare le informazioni per accedere al gestionale: " + "<br>Nome utente: %s "
								+ "<br>Password (Provvisoria): %s"
								+ "<br>Al primo accesso al gestionale ti sarà richiesto di cambiare la password"
								+ "<br>Se non sai perchè hai ricevuto questa e-mail, cancellala pure.\r\n"
								+ "<br><hr>Non rispondere a questa email perche viene generata automaticamente",
								data.getUsername(), randomPwd);
				MailObject obj = new MailObject(CostantiVVF.MAIL_OBJECT_RESET_PASSWORD, data.getEmail(), content);
				success = MailUtils.sendMailTo(obj);
			}
			message = Messages.getMessage("operation.ok");
		} catch (Exception e) {
			message = Messages.getMessage("operation.ko");
			success = false;
			e.printStackTrace();
		} finally {
			result = new JsonResponse<Utenti>(success, message, data);
		}
		return result;
	}
	/**
	 * 
	 * @param username
	 * @param email
	 * @return
	 */
	public JsonResponse<Boolean> recoverPassword(String username, String email) {
		JsonResponse<Boolean> result = null;
		Boolean success = true;
		String message = "";
		try {
			
			if (Utils.isEmptyString(username)) {
				return new JsonResponse<Boolean>(false, Messages.getMessageFormatted("field.err.mandatory",
						new String[] { Messages.getMessage("field.username") }), false);
			}
			
			if (Utils.isEmptyString(email)) {
				return new JsonResponse<Boolean>(false, Messages.getMessageFormatted("field.err.mandatory",
						new String[] { Messages.getMessage("field.email") }), false);
			}
			
			Utenti utente = utentiManager.getByUserName(username);
			if (utente == null) {
				return new JsonResponse<Boolean>(false, Messages.getMessage("account.recover.username.not.found"), false);
			}
			
			String newPwd = SecurityService.generateRandomPasswordInt();
			utente.setPwd(bcryptEncoder.encode(newPwd));
			utente.setPrimoAcesso(false);
			utentiManager.update(utente);
			String content = String.format(
					"Gentile utente, in seguito alla richiesta di recupero password, sono riportare le nuove informazioni per accedere al gestionale: "
							+ "<br>Nome utente: %s " + "<br>Password (Provvisoria): %s"
							+ "<br>Al primo accesso al gestionale ti sarà richiesto di cambiare la password"
							+ "<br>Se non sai perchè hai ricevuto questa e-mail, cancellala pure.\r\n"
							+ "<br><hr>Non rispondere a questa email perche viene generata automaticamente",
							utente.getUsername(), newPwd);
			MailObject obj = new MailObject(CostantiVVF.MAIL_OBJECT_RECOVER_PASSWORD, email, content);
			success = MailUtils.sendMailTo(obj);
			
			
			message = Messages.getMessage("account.recover.ok");
			
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("operation.ko");
			e.printStackTrace();
		} finally {
			result = new JsonResponse<Boolean>(success, message, success);
		}
		return result;
	}

}
