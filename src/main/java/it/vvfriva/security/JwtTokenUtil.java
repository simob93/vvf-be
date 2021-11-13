package it.vvfriva.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.vvfriva.utils.Configs;

@Service
public class JwtTokenUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2361581095170120499L;

	// generate token for user
	public static String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
//		Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
//		if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
//			claims.put("isAdmin", true);
//		}
//		if (roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
//			claims.put("isUser", true);
//		}
		return doGenerateToken(claims, userDetails.getUsername());
	}

	public static String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(Configs.getJWT_SECRET_KEY()).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	/**
	 * 
	 * @param authToken
	 * @param request
	 * @return
	 */
	public static boolean validateToken(String authToken, HttpServletRequest rq) {
		Jws<Claims> claims = Jwts.parser().setSigningKey(Configs.getJWT_SECRET_KEY()).parseClaimsJws(authToken);
		return true;
	}

	/**
	 * 
	 * @param claims
	 * @param subject
	 * @return
	 */
	private static String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Configs.getJWT_EXPIRATION_TIME()))
				.signWith(SignatureAlgorithm.HS512, Configs.getJWT_SECRET_KEY()).compact();
	}
}
