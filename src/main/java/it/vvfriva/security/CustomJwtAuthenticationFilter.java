package it.vvfriva.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;

@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {
	
	public static final Logger log = LoggerFactory.getLogger(CustomJwtAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// JWT Token is in the form "Bearer token". Remove Bearer word and
		// get only the Token
		String jwtToken = extractJwtFromRequest(request);
		try {

			if (StringUtils.hasText(jwtToken) && JwtTokenUtil.validateToken(jwtToken, request)) {
				UserDetails userDetails = new User(JwtTokenUtil.getUsernameFromToken(jwtToken), "",
						new ArrayList<SimpleGrantedAuthority>()/* JwtTokenUtil.getRolesFromToken(jwtToken) */);

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				log.info("token validate correct!");
			} else {
				log.error(Utils.errorInMethod("Cannot set the Security Context"));
			}

		} catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			request.setAttribute("message", ex.getMessage());
		} catch (ExpiredJwtException ex) {
			request.setAttribute("message", Messages.getMessage("token.ko"));
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private String extractJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}
