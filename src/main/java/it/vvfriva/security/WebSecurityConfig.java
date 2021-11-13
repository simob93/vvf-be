package it.vvfriva.security;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import it.vvfriva.managers.UtentiManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired private JwtAuthenticationEntryPoint unauthorizedHandler;
	@Autowired private UtentiManager utentiManager;
	@Autowired private CustomJwtAuthenticationFilter customJwtAuthenticationFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService((UserDetailsService) utentiManager).passwordEncoder(passwordEncoder());
	}

	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().configurationSource(new CorsConfigurationSource() {

		    @Override
		    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
		        CorsConfiguration config = new CorsConfiguration();
		        config.setAllowedHeaders(Collections.singletonList("*"));
		        config.setAllowedMethods(Collections.singletonList("*"));
		        config.addAllowedOrigin("*");
		        config.setAllowCredentials(true);
		        return config;
		    }
		});

		http.csrf().disable().authorizeRequests()
		.antMatchers("/user/new").permitAll()
		.antMatchers("/user/update").permitAll()
		.antMatchers("/turni/*").permitAll()
		.antMatchers("/general/versione").permitAll()
		.antMatchers("/auth/login").permitAll()
		.antMatchers("/user/recoverpassword").permitAll()
		.anyRequest().authenticated()
		.and().exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler).and().
		// make sure we use stateless session; session won't be used to
		// store user's state.
		sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

// 		Add a filter to validate the tokens with every request
		http.addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
