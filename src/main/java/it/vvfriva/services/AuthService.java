package it.vvfriva.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Utenti;
import it.vvfriva.managers.UtentiManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.LoginModel;
import it.vvfriva.models.LoginResponse;
import it.vvfriva.security.JwtTokenUtil;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;
/**
 * 
 * @author simone
 *
 */
@Service
public class AuthService {
	
	@Autowired UtentiManager utentiManager;
	@Autowired AuthenticationManager sipringAuthManager;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	/**
	 * Metodo di autenticazione tramite JWT token
	 * @param loginParam
	 * @return
	 */
	public JsonResponse<LoginResponse> doLogin(LoginModel loginParam) {
		boolean success = true;
		String message = null;
		JsonResponse<LoginResponse> result = null;
		LoginResponse data = new LoginResponse();
		UserDetails userDetails = null;
		try {
			
			sipringAuthManager.authenticate(new UsernamePasswordAuthenticationToken(loginParam.getUsername(), loginParam.getPassword()));
			
			userDetails = utentiManager.loadUserByUsername(loginParam.getUsername());
			
			Utenti ut = utentiManager.getByUserName(loginParam.getUsername());
			if (ut.getAbilitato() != null && !ut.getAbilitato().booleanValue()) {
				throw new Exception(Messages.getMessage("user.not.active"));
			}
			String token = JwtTokenUtil.generateToken(userDetails);
			data.setUsername(userDetails.getUsername());
			data.setAccessToken(token);
			data.setPrimoAccesso(ut.getPrimoAcesso());
			data.setIdRole(ut.getRuolo().get(0).getIdRuolo());
			message = Messages.getMessage("login.ok");
			logger.info(message);
			
		} catch (AuthenticationException authEx) {
			success = false;
			message = Messages.getMessage("login.ko");
			logger.error(Utils.errorInMethod(authEx.getMessage()));
			authEx.printStackTrace();
		} catch (Exception e) {
			success = false;
			logger.error(Utils.errorInMethod(e.getMessage()));
			message = e.getMessage() != null ? e.getMessage() : Messages.getMessage("login.ko");
			e.printStackTrace();
		} finally {
			result = new JsonResponse<LoginResponse>(success, message, data);
		}
		return result;
	}
	
}
