
package it.vvfriva.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import org.hibernate.annotations.Formula;
/**
 * 
 * @author simone
 *
 */
@Entity
@NamedStoredProcedureQueries({
	  @NamedStoredProcedureQuery(
	    name = "calc_num_prot", 
	    procedureName = "calc_num_prot", 
	    resultClasses = {  }, 
	    parameters = { 
	        @StoredProcedureParameter(
	          name = "id", 
	          type = Integer.class, 
	          mode = ParameterMode.IN) }) 
	})
public class Protocol {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="date_protocol_in")
	private Date dateProtocolIn;
	
	@Column(name="date_protocol")
	private Date dateProtocol;
	
	@Column(name="year")
	private String year;
	
	@Column(name="str_uid")
	private String strUid;
	
	@Column(name="str_uid_protocol_in")
	private String strUidProtocolIn;
	
	@Column(name="type")
	private String type;
	
	@Column(name="object")
	private String object;
	
	@Column(name="id_typology")
	private Integer idTypology;
	
	@Column(name="id_archive")
	private Integer idArchive;
	
	@Column(name="id_organization")
	private Integer idOrganization;
	
	@Column(name="istat_comune")
	private Integer istatComune;
	
	@Column(name="istat_provincia")
	private Integer istatProvincia;
	
	@Column(name="address")
	private String address;
	
	@Column(name="old_citta")
	private String oldCitta;
	
	@Column(name="old_provincia")
	private String oldProvincia;
	
	@Column(name="old_nazione")
	private String oldNazione;
	
	@Column(name="converted")
	private Integer converted;
	
	@Column(name="cap")
	private Integer cap;
	
	@Column(name="business_name")
	private String businessName;
	
	@Formula("(select Concat(per.extra, ' - ', per.name) from person per where per.id = id_archive  limit 1 )")
	private String descrFaldone;

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
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the dateProtocolIn
	 */
	public Date getDateProtocolIn() {
		return dateProtocolIn;
	}

	/**
	 * @param dateProtocolIn the dateProtocolIn to set
	 */
	public void setDateProtocolIn(Date dateProtocolIn) {
		this.dateProtocolIn = dateProtocolIn;
	}

	/**
	 * @return the dateProtocol
	 */
	public Date getDateProtocol() {
		return dateProtocol;
	}

	/**
	 * @param dateProtocol the dateProtocol to set
	 */
	public void setDateProtocol(Date dateProtocol) {
		this.dateProtocol = dateProtocol;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the strUid
	 */
	public String getStrUid() {
		return strUid;
	}

	/**
	 * @param strUid the strUid to set
	 */
	public void setStrUid(String strUid) {
		this.strUid = strUid;
	}

	/**
	 * @return the strUidProtocolIn
	 */
	public String getStrUidProtocolIn() {
		return strUidProtocolIn;
	}

	/**
	 * @param strUidProtocolIn the strUidProtocolIn to set
	 */
	public void setStrUidProtocolIn(String strUidProtocolIn) {
		this.strUidProtocolIn = strUidProtocolIn;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the object
	 */
	public String getObject() {
		return object;
	}

	/**
	 * @param object the object to set
	 */
	public void setObject(String object) {
		this.object = object;
	}

	/**
	 * @return the idTypology
	 */
	public Integer getIdTypology() {
		return idTypology;
	}

	/**
	 * @param idTypology the idTypology to set
	 */
	public void setIdTypology(Integer idTypology) {
		this.idTypology = idTypology;
	}

	/**
	 * @return the idArchive
	 */
	public Integer getIdArchive() {
		return idArchive;
	}

	/**
	 * @param idArchive the idArchive to set
	 */
	public void setIdArchive(Integer idArchive) {
		this.idArchive = idArchive;
	}

	/**
	 * @return the idOrganization
	 */
	public Integer getIdOrganization() {
		return idOrganization;
	}

	/**
	 * @param idOrganization the idOrganization to set
	 */
	public void setIdOrganization(Integer idOrganization) {
		this.idOrganization = idOrganization;
	}

	/**
	 * @return the idTown
	 */
	public Integer getIstatComune() {
		return istatComune;
	}

	/**
	 * @param idTown the idTown to set
	 */
	public void setIstatComune(Integer idTown) {
		this.istatComune = idTown;
	}

	/**
	 * @return the idDistrict
	 */
	public Integer getIstatProvincia() {
		return istatProvincia;
	}

	/**
	 * @param idDistrict the idDistrict to set
	 */
	public void setIstatProvincia(Integer idDistrict) {
		this.istatProvincia = idDistrict;
	}

	/**
	 * @return the adress
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param adress the adress to set
	 */
	public void setAddress(String adress) {
		this.address = adress;
	}

	/**
	 * @return the cap
	 */
	public Integer getCap() {
		return cap;
	}

	/**
	 * @param cap the cap to set
	 */
	public void setCap(Integer cap) {
		this.cap = cap;
	}

	/**
	 * @return the businessName
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * @param businessName the businessName to set
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	/**
	 * @return the oldCitta
	 */
	public String getOldCitta() {
		return oldCitta;
	}

	/**
	 * @param oldCitta the oldCitta to set
	 */
	public void setOldCitta(String oldCitta) {
		this.oldCitta = oldCitta;
	}

	/**
	 * @return the oldProvincia
	 */
	public String getOldProvincia() {
		return oldProvincia;
	}

	/**
	 * @param oldProvincia the oldProvincia to set
	 */
	public void setOldProvincia(String oldProvincia) {
		this.oldProvincia = oldProvincia;
	}

	/**
	 * @return the oldNazione
	 */
	public String getOldNazione() {
		return oldNazione;
	}

	/**
	 * @param oldNazione the oldNazione to set
	 */
	public void setOldNazione(String oldNazione) {
		this.oldNazione = oldNazione;
	}

	/**
	 * @return the converted
	 */
	public Integer getConverted() {
		return converted;
	}

	/**
	 * @param converted the converted to set
	 */
	public void setConverted(Integer converted) {
		this.converted = converted;
	}

	/**
	 * @return the descrFaldone
	 */
	public String getDescrFaldone() {
		return descrFaldone;
	}

	/**
	 * @param descrFaldone the descrFaldone to set
	 */
	public void setDescrFaldone(String descrFaldone) {
		this.descrFaldone = descrFaldone;
	}
}
