package it.vvfriva.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.vvfriva.utils.Utils;
/**
 * 
 * @author simone
 *
 */
@Entity
public class Vigile implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6500814684794960282L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="mail")
	private String mail;
	
	@Column(name="postal_code")
	private Integer postalCode; //commento
	
	@Column(name="address")
	private String address;
	
	@Column(name="istat_provincia")
	private Integer istatProvincia;
	
	@Column(name="istat_comune")
	private Integer istatComune;
	
	@Column(name="codPhone")
	private String codPhone;
	
	@Column(name="phone_1")
	private String phone1;
	
	@Column(name="phone_2")
	private String phone2;
	
	@Column(name="phone_3")
	private String phone3;
	
	@Column(name="phone_4")
	private String phone4;
	
	@Column(name="mail_1")
	private String mail1;
	
	@Column(name="mail_2")
	private String mail2;
	
	@Column(name="mail_3")
	private String mail3;
	
	@Column(name="mail_4")
	private String mail4;
	
	@Transient
	private List<String> extra_mail;
	
	@Transient
	private List<String> extra_phone;
	
	@ManyToOne
	@JoinColumn(name = "istat_provincia", referencedColumnName = "istat", insertable=false,  updatable=false)
	private Provincie provincia;
	
	@ManyToOne
	@JoinColumn(name = "istat_comune", referencedColumnName = "istat", insertable=false,  updatable=false)
	private Comuni comune;
	
	@Column(name="fiscal_code")
	private String fiscalCode;
	
	@Column(name="birthday")
	private Date birthday;
	
    @JsonIgnore
	@Column(name="driving_licenses")
	private String drivingLicenses;
    
    @Column(name="phone")
	private String phone;
    
    @Column(name="color")
	private String color;
    
	@Column(name="no_salto_turno")
	private Boolean noSaltoTurno;
    
	
	@Transient
	private List<Integer> listDrivingLicenses;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@XmlTransient
	public String getDrivingLicenses() {
		return drivingLicenses;
	}

	public void setDrivingLicenses(String drivingLicenses) {
		this.drivingLicenses = drivingLicenses;
	}

	public List<Integer> getListDrivingLicenses() {
		if (getDrivingLicenses() != null && !getDrivingLicenses().isEmpty()) {
			
			List<Integer> listInt = new ArrayList<>();
 			List<String> str = Arrays.asList(getDrivingLicenses().split(";"));
			Iterator<String> it = str.iterator();
			
			while (it.hasNext()) {
				String id = (String) it.next();
				listInt.add(Integer.parseInt(id));
			}
			return listInt;
		}
		return listDrivingLicenses;
	}

	public void setListDrivingLicenses(List<Integer> listDrivingLicenses) {
		this.listDrivingLicenses = listDrivingLicenses;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Integer getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	public String getFiscalCode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}

	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the strUid
	 */
	public String getCodPhone() {
		return codPhone;
	}

	/**
	 * @param strUid the strUid to set
	 */
	public void setCodPhone(String strUid) {
		this.codPhone = strUid;
	}

	

	/**
	 * @return the comune
	 */
	public Comuni getComune() {
		return comune;
	}

	/**
	 * @param comune the comune to set
	 */
	public void setComune(Comuni comune) {
		this.comune = comune;
	}

	/**
	 * @return the istat_provincia
	 */
	public Integer getIstatProvincia() {
		return istatProvincia;
	}

	/**
	 * @param istat_provincia the istat_provincia to set
	 */
	public void setIstatProvincia(Integer istat_provincia) {
		this.istatProvincia = istat_provincia;
	}

	/**
	 * @return the istat_comune
	 */
	public Integer getIstatComune() {
		return istatComune;
	}

	/**
	 * @param istat_comune the istat_comune to set
	 */
	public void setIstatComune(Integer istat_comune) {
		this.istatComune = istat_comune;
	}

	/**
	 * @return the provincia
	 */
	public Provincie getProvincia() {
		return provincia;
	}

	/**
	 * @param provincia the provincia to set
	 */
	public void setProvincia(Provincie provincia) {
		this.provincia = provincia;
	}
	
	public Boolean getNoSaltoTurno() {
		return noSaltoTurno;
	}

	public void setNoSaltoTurno(Boolean noSaltoTurno) {
		this.noSaltoTurno = noSaltoTurno;
	}
	
	public String getNominativo() {
		return this.lastName + " " + this.firstName;
	}

	/**
	 * @return the phone1
	 */
	public String getPhone1() {
		return phone1;
	}

	/**
	 * @param phone1 the phone1 to set
	 */
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	/**
	 * @return the phone2
	 */
	public String getPhone2() {
		return phone2;
	}

	/**
	 * @param phone2 the phone2 to set
	 */
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	/**
	 * @return the phone3
	 */
	public String getPhone3() {
		return phone3;
	}

	/**
	 * @param phone3 the phone3 to set
	 */
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	/**
	 * @return the phone4
	 */
	public String getPhone4() {
		return phone4;
	}

	/**
	 * @param phone4 the phone4 to set
	 */
	public void setPhone4(String phone4) {
		this.phone4 = phone4;
	}

	/**
	 * @return the mail1
	 */
	public String getMail1() {
		return mail1;
	}

	/**
	 * @param mail1 the mail1 to set
	 */
	public void setMail1(String mail1) {
		this.mail1 = mail1;
	}

	/**
	 * @return the mail2
	 */
	public String getMail2() {
		return mail2;
	}

	/**
	 * @param mail2 the mail2 to set
	 */
	public void setMail2(String mail2) {
		this.mail2 = mail2;
	}

	/**
	 * @return the mail3
	 */
	public String getMail3() {
		return mail3;
	}

	/**
	 * @param mail3 the mail3 to set
	 */
	public void setMail3(String mail3) {
		this.mail3 = mail3;
	}

	/**
	 * @return the mail4
	 */
	public String getMail4() {
		return mail4;
	}

	/**
	 * @param mail4 the mail4 to set
	 */
	public void setMail4(String mail4) {
		this.mail4 = mail4;
	}

	/**
	 * @return the extra_mail
	 */
	public List<String> getExtra_mail() {
		List<String> emails = new ArrayList<String>();
		if (!Utils.isEmptyString(this.mail1)) {
			emails.add(this.mail1);
		}
		if (!Utils.isEmptyString(this.mail2)) {
			emails.add(this.mail2);
		}
		if (!Utils.isEmptyString(this.mail3)) {
			emails.add(this.mail3);
		}
		if (!Utils.isEmptyString(this.mail4)) {
			emails.add(this.mail4);
		}
		return emails;
	}

	/**
	 * @return the extra_phone
	 */
	public List<String> getExtra_phone() {
		List<String> phones = new ArrayList<String>();
		if (!Utils.isEmptyString(this.phone1)) {
			phones.add(this.phone1);
		}
		if (!Utils.isEmptyString(this.phone2)) {
			phones.add(this.phone2);
		}
		if (!Utils.isEmptyString(this.phone3)) {
			phones.add(this.phone3);
		}
		if (!Utils.isEmptyString(this.phone4)) {
			phones.add(this.phone4);
		}
		return phones;
	}
}
