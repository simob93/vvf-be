package it.vvfriva.services.auth;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;
/**
 * 
 * @author simone
 *
 */
@Service
public class SecurityService {
	
	/**
	 * Metodo crea una passaword casuale 
	 * @return password casuale di 4 cifre
	 */
	public static String generateRandomPasswordInt() {
		SecureRandom random = new SecureRandom();
		StringBuilder temporal_pwd = new StringBuilder();
		int idx = 0;
		while(idx < 4) {
			temporal_pwd.append(String.valueOf(random.nextInt(1000)).charAt(0));
			idx++;
		}
		return temporal_pwd.toString();
	}
	
}
