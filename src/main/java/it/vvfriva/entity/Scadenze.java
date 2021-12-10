package it.vvfriva.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.vvfriva.interfaces.EntityInfo;
import it.vvfriva.utils.Utils;

/**
 * 
 * @author simone
 *
 */
@Entity
@Table(name = "expiry")
public class Scadenze implements EntityInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "date")
	private Date date = new Date();

	@Column(name = "update_data")
	private Date updateData;

	@Column(name = "date_from")
	private Date dateFrom;

	@Column(name = "date_expiration")
	private Date dateExpiration;
	
	@Column(name = "id_riferimento")
	private Integer idRiferimento;

	@Column(name = "id_area")
	private Integer idArea;

	@Column(name = "id_vigile")
	private Integer idVigile;

	@Column(name = "renew")
	private Integer renew;

	@Column(name = "note")
	private String note;

	@ManyToOne
	@JoinColumn(name = "id_vigile", referencedColumnName="id", insertable = false, updatable = false)
	@Fetch(FetchMode.SELECT)
	@NotFound(action=NotFoundAction.IGNORE)
	Vigile vigile;
	
	@ManyToOne
	@JoinColumn(name = "id_area", referencedColumnName="id", insertable = false, updatable = false)
	@Fetch(FetchMode.SELECT)
	@NotFound(action=NotFoundAction.IGNORE)
	Area area;
	
	@Transient
	private Integer stato;

	@Transient
	private String descrFormatted;

	@Transient
	private String descrAreaFormatted;

	@Transient
	private Integer idPerson;

	@Transient
	private String descrVigile;

	public Scadenze() {

	}


	public Scadenze(String descrFormatted) {
		this.descrFormatted = descrFormatted;
		
	}



	public Scadenze(Integer id, Date dateExpiration, Date dateFrom, Integer idRiferimento, 
			String note, String descrAreaFormatted, Integer idArea, Integer idVigile, String descrVigile) {
		super();
		this.id = id;
		this.dateExpiration = dateExpiration;
		this.dateFrom = dateFrom;
		this.note = note;
		this.descrAreaFormatted = descrAreaFormatted;
		this.idArea = idArea;
		this.idVigile = idVigile;
		this.descrVigile = descrVigile;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescrFormatted() {
		return descrFormatted;
	}

	public void setDescrFormatted(String descrFormatted) {
		this.descrFormatted = descrFormatted;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getUpdateData() {
		return updateData;
	}

	public void setUpdateData(Date updateData) {
		this.updateData = updateData;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Integer getStato() {
		/*
		 * stato: -1 scaduta 0 - in scadenza 1 - valida
		 */
		stato = 1;
		Date dateTo = getDateExpiration();
		Date today = Utils.startOfDay(new Date());
		if (Utils.isValidDate(dateFrom) && Utils.isValidDate(dateTo)) {
			// calcolo la data di alert
			if (dateTo.compareTo(today) < 0) {
				stato = -1;
			} else {
				Date dateAlert = Utils.dateAdd(today, Calendar.MONTH, 6);
				stato = dateTo.compareTo(dateAlert) < 0 ? 0 : 1;
			}
		}
		return stato;
	}

	public void setStato(Integer stato) {
		this.stato = stato;
	}

	public Date getDateExpiration() {
		return dateExpiration;
	}

	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public Integer getRenew() {
		return renew;
	}

	public void setRenew(Integer renew) {
		this.renew = renew;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date data) {
		this.date = data;
	}

	public Integer getIdArea() {
		return idArea;
	}

	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}

	public String getDescrAreaFormatted() {
		if (this.getArea() != null) {
			return this.getArea().getName(); 
		}
		return this.descrAreaFormatted;
	}

	public void setDescrAreaFormatted(String descrAreaFormatted) {
		this.descrAreaFormatted = descrAreaFormatted;
	}

	/**
	 * +
	 * 
	 * @return the idPerson
	 */
	public Integer getIdPerson() {
		return idPerson;
	}

	/**
	 * @param idPerson
	 *            the idPerson to set
	 */
	public void setIdPerson(Integer idPerson) {
		this.idPerson = idPerson;
	}

	/**
	 * @return the idVigile
	 */
	public Integer getIdVigile() {
		return idVigile;
	}

	/**
	 * @param idVigile
	 *            the idVigile to set
	 */
	public void setIdVigile(Integer idVigile) {
		this.idVigile = idVigile;
	}

	/**
	 * @return the descrVigile
	 */
	public String getDescrVigile() {
		if (this.getVigile() != null) {
			return this.getVigile().getFirstName() + " " + this.getVigile().getLastName();
		}
		return this.descrVigile;
	}

	/**
	 * @param descrVigile
	 *            the descrVigile to set
	 */
	public void setDescrVigile(String descrVigile) {
		this.descrVigile = descrVigile;
	}

	/**
	 * @return the dateExpirationFormatted
	 */
	public String getDateExpirationFormatted() {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		if (dateExpiration != null) {
			return format.format(dateExpiration);
		}
		return "";
	}

	/**
	 * @return the vigile
	 */
	@JsonIgnore
	public Vigile getVigile() {
		return vigile;
	}

	/**
	 * @param vigile the vigile to set
	 */
	public void setVigile(Vigile vigile) {
		this.vigile = vigile;
	}

	/**
	 * @return the area
	 */
	@JsonIgnore
	public Area getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(Area area) {
		this.area = area;
	}


	/**
	 * @return the idRiferimento
	 */
	public Integer getIdRiferimento() {
		return idRiferimento;
	}


	/**
	 * @param idRiferimento the idRiferimento to set
	 */
	public void setIdRiferimento(Integer idRiferimento) {
		this.idRiferimento = idRiferimento;
	}
}
