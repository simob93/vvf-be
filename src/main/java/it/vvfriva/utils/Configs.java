package it.vvfriva.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 
 * @author simone
 *
 */
@Configuration
public class Configs implements EnvironmentAware {
	

	@Autowired
    static Environment env;
	
	protected static String SMTP_HOST = null;
	protected static String SMTP_PASSWORD = null;
	protected static String SMTP_PORT = null;
	protected static String MAIL_FROM = null;
	protected static String SMTP_USERNAME = null;
	protected static String JWT_SECRET_KEY = null;
	protected static String VERSIONE_PROGRAMMA = null;
	protected static Integer JWT_EXPIRATION_TIME = null;
	

	/**
	 * @return the sMTP_HOST
	 */
	public static String getSMTP_HOST() {
		if (SMTP_HOST == null) {
			SMTP_HOST = env.getProperty("vvf.smtp.host", "");
		}
		return  SMTP_HOST;
	}


	/**
	 * @return the sMTP_PASSWORD
	 */
	public static String getSMTP_PASSWORD() {
		
		if (SMTP_PASSWORD == null) {
			SMTP_PASSWORD = env.getProperty("vvf.smtp.pwd", "");
		}
		return SMTP_PASSWORD;
	}
	/**
	 * @return the sMTP_PORT
	 */
	public static String getSMTP_PORT() {
		
		if (SMTP_PORT == null) {
			SMTP_PORT = env.getProperty("vvf.smtp.port", "");
		}
		return SMTP_PORT;
	}

	/**
	 * @return the sMTP_FROM
	 */
	public static String getMailFROM() {
		if (MAIL_FROM == null) {
			MAIL_FROM =  env.getProperty("vvf.mail.from", "");
		}
		return MAIL_FROM;
	}


	@Override
	public void setEnvironment(Environment environment) {
		env = environment;
	}
	/**
	 * @return the sMTP_USERNAME
	 */
	public static String getSMTP_USERNAME() {
		if (SMTP_USERNAME == null) {
			SMTP_USERNAME = env.getProperty("vvf.smtp.username", "");
		}
		return SMTP_USERNAME;
	}


	/**
	 * @return the jWT_SECRET_KEY
	 */
	public static String getJWT_SECRET_KEY() {
		if (JWT_SECRET_KEY == null) {
			JWT_SECRET_KEY = env.getProperty("jwt.secret");
		}
		return JWT_SECRET_KEY;
	}


	/**
	 * @return the jWT_EXPIRATION_TIME
	 */
	public static Integer getJWT_EXPIRATION_TIME() {
		if (JWT_EXPIRATION_TIME == null) {
			JWT_EXPIRATION_TIME = Integer.parseInt(env.getProperty("jwt.jwtExpirationInMs"));
		}
		return JWT_EXPIRATION_TIME;
	}
	
	public static String getVERSIONE_PROGRAMMA() {
		if (VERSIONE_PROGRAMMA == null) {
			VERSIONE_PROGRAMMA = env.getProperty("gradleVersion", "");
		}
		return VERSIONE_PROGRAMMA;
	}
}
