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

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.vvfriva.interfaces.EntityInfo;
/**
 * 
 * @author simone
 *
 */
@Entity
@Table(name = "ruoli")
public class Ruoli implements EntityInfo {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(name="descrizione")
	private String descrizione;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "id_ruolo", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private List<RuoliPermessi> ruoliPermessi;
	
	
	public Ruoli() {
		
	}

	public Ruoli(Integer id, String descrizione) {
		this.id = id;
		this.descrizione = descrizione;
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

	/**
	 * @return the ruoliPermessi
	 */
	@JsonIgnore
	public List<RuoliPermessi> getRuoliPermessi() {
		return ruoliPermessi;
	}

	/**
	 * @param ruoliPermessi the ruoliPermessi to set
	 */
	public void setRuoliPermessi(List<RuoliPermessi> ruoliPermessi) {
		this.ruoliPermessi = ruoliPermessi;
	}
}
