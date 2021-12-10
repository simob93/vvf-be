package it.vvfriva.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "menu")
public class Menu implements EntityInfo {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="descrizione")
	private String descrizione;
	
	@Column(name="id_padre")
	private Integer idPadre;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "id_padre", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public List<Menu> subMenu;
	
	
	public Menu() {
		
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
	 * @return the idPadre
	 */
	public Integer getIdPadre() {
		return idPadre;
	}

	/**
	 * @param idPadre the idPadre to set
	 */
	public void setIdPadre(Integer idPadre) {
		this.idPadre = idPadre;
	}

	/**
	 * @return the subMenu
	 */
	public List<Menu> getSubMenu() {
		return subMenu;
	}

	/**
	 * @param subMenu the subMenu to set
	 */
	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}

	/**
	 * @return the descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * @param descrizione the descrizione to set
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
