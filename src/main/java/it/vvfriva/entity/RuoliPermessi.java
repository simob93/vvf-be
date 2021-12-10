package it.vvfriva.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import it.vvfriva.interfaces.EntityInfo;

/**
 * 
 * @author simone
 *
 */
@Entity
@Table(name = "ruoli_permessi")
public class RuoliPermessi implements EntityInfo {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="id_menu")
	private Integer idMenu;
	
	@Column(name="id_ruolo")
	private Integer idRuolo;
	
	@Column(name="permesso")
	private String permesso;
	
	@ManyToOne
	@JoinColumn(name = "id_menu", referencedColumnName="id", insertable = false, updatable = false)
	@Fetch(FetchMode.SELECT)
	@NotFound(action=NotFoundAction.IGNORE)
	Menu menu;
	
	@ManyToOne
	@JoinColumn(name = "id_ruolo", referencedColumnName="id", insertable = false, updatable = false)
	@Fetch(FetchMode.SELECT)
	@NotFound(action=NotFoundAction.IGNORE)
	Ruoli ruolo;
	
	public RuoliPermessi() {
		
	}
	/**
	 * 
	 * @param id
	 * @param idMenu
	 * @param idRuolo
	 * @param permesso
	 */
	public RuoliPermessi(Integer id, Integer idMenu, Integer idRuolo, String permesso) {
		this.id = id;
		this.idMenu = idMenu;
		this.idRuolo = idRuolo;
		this.permesso = permesso;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the idMenu
	 */
	public Integer getIdMenu() {
		return idMenu;
	}

	/**
	 * @param idMenu the idMenu to set
	 */
	public void setIdMenu(Integer idMenu) {
		this.idMenu = idMenu;
	}

	/**
	 * @return the idRuolo
	 */
	public Integer getIdRuolo() {
		return idRuolo;
	}

	/**
	 * @param idRuolo the idRuolo to set
	 */
	public void setIdRuolo(Integer idRuolo) {
		this.idRuolo = idRuolo;
	}

	/**
	 * @return the permesso
	 */
	public String getPermesso() {
		return permesso;
	}

	/**
	 * @param permesso the permesso to set
	 */
	public void setPermesso(String permesso) {
		this.permesso = permesso;
	}
	/**
	 * @return the menu
	 */
	public Menu getMenu() {
		return menu;
	}
	/**
	 * @param menu the menu to set
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	/**
	 * @return the ruolo
	 */
	public Ruoli getRuolo() {
		return ruolo;
	}
	/**
	 * @param ruolo the ruolo to set
	 */
	public void setRuolo(Ruoli ruolo) {
		this.ruolo = ruolo;
	}
}
