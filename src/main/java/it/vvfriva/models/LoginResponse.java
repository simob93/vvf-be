package it.vvfriva.models;
/**
 * 
 * @author simone
 *
 */
public class LoginResponse {
	private String username;
	private String accessToken;
	private Boolean primoAccesso; 
	private Integer idRole;
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}
	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	/**
	 * @return the idRole
	 */
	public Integer getIdRole() {
		return idRole;
	}
	/**
	 * @param idRole the idRole to set
	 */
	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}
	/**
	 * @return the primoAccesso
	 */
	public Boolean getPrimoAccesso() {
		return primoAccesso;
	}
	/**
	 * @param primoAccesso the primoAccesso to set
	 */
	public void setPrimoAccesso(Boolean primoAccesso) {
		this.primoAccesso = primoAccesso;
	} 
}
