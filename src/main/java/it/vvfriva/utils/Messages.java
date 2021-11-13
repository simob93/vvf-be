package it.vvfriva.utils;

import java.text.MessageFormat;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:it.properties")
public class Messages implements EnvironmentAware {

    static Environment env;


	public static String getMessage(String key) {
		return env.getProperty(key);
	}
	
	public static String getMessageFormatted(String key, Object[] parameters) {
		return MessageFormat.format(getMessage(key), parameters);
	}

	@Override
	public void setEnvironment(Environment environment) {
		env = environment;
	}
}
