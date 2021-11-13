package it.vvfriva.models;

public class ModelPrntAutorizzazioni {
	
	private Integer idVigile;
	private String descrVigile;
	private String descrPerson;
//	private Integer idPerson;
//	private boolean ple = false;
//	private boolean man = false;
//	private boolean stralis = false;
//	private boolean trakker = false;
//	private boolean fuoristr = false;
//	private boolean guidaCarr = false;
//	private boolean poliR6 = false;
//	private boolean pinzaR7 = false;
//	private boolean unimog = false;
//	private boolean trial = false;
//	private boolean c9 = false;
//	private boolean gommone = false;
//	private boolean motoAcqua = false;
//	private boolean soccAcquatico = false;
//	
	
//	public ModelPrntAutorizzazioni(Integer idVigile, String descrVigile, Integer idPerson) {
//		this.descrVigile = descrVigile;
//		this.idPerson = idPerson;
//		this.idVigile = idVigile;
//	}
//	
//	public ModelPrntAutorizzazioni(String descrVigile, Integer idPerson, boolean ple, boolean man, boolean stralis,
//			boolean trakker, boolean fuoristr, boolean guidaCarr, boolean poliR6, boolean pinzaR7, boolean unimog,
//			boolean trial, boolean c9, boolean gommone, boolean motoAcqua, boolean soccAcquatico) {
//		this.descrVigile = descrVigile;
//		this.idPerson = idPerson;
//		this.ple = ple;
//		this.man = man;
//		this.stralis = stralis;
//		this.trakker = trakker;
//		this.fuoristr = fuoristr;
//		this.guidaCarr = guidaCarr;
//		this.poliR6 = poliR6;
//		this.pinzaR7 = pinzaR7;
//		this.unimog = unimog;
//		this.trial = trial;
//		this.c9 = c9;
//		this.gommone = gommone;
//		this.motoAcqua = motoAcqua;
//		this.soccAcquatico = soccAcquatico;
//	}
	/**
	 * @return the descrVigile
	 */
	public String getDescrVigile() {
		return descrVigile;
	}
	public ModelPrntAutorizzazioni(Integer idVigile, String descrVigile, String descrPerson) {
		super();
		this.idVigile = idVigile;
		this.descrVigile = descrVigile;
		this.descrPerson = descrPerson;
	}
	/**
	 * @param descrVigile the descrVigile to set
	 */
	public void setDescrVigile(String descrVigile) {
		this.descrVigile = descrVigile;
	}
//	/**
//	 * @return the ple
//	 */
//	public boolean isPle() {
//		return ple;
//	}
//	/**
//	 * @param ple the ple to set
//	 */
//	public void setPle(boolean ple) {
//		this.ple = ple;
//	}
//	/**
//	 * @return the man
//	 */
//	public boolean isMan() {
//		return man;
//	}
//	/**
//	 * @param man the man to set
//	 */
//	public void setMan(boolean man) {
//		this.man = man;
//	}
//	/**
//	 * @return the stralis
//	 */
//	public boolean isStralis() {
//		return stralis;
//	}
//	/**
//	 * @param stralis the stralis to set
//	 */
//	public void setStralis(boolean stralis) {
//		this.stralis = stralis;
//	}
//	/**
//	 * @return the trakker
//	 */
//	public boolean isTrakker() {
//		return trakker;
//	}
//	/**
//	 * @param trakker the trakker to set
//	 */
//	public void setTrakker(boolean trakker) {
//		this.trakker = trakker;
//	}
//	/**
//	 * @return the fuoristr
//	 */
//	public boolean isFuoristr() {
//		return fuoristr;
//	}
//	/**
//	 * @param fuoristr the fuoristr to set
//	 */
//	public void setFuoristr(boolean fuoristr) {
//		this.fuoristr = fuoristr;
//	}
//	/**
//	 * @return the guidaCarr
//	 */
//	public boolean isGuidaCarr() {
//		return guidaCarr;
//	}
//	/**
//	 * @param guidaCarr the guidaCarr to set
//	 */
//	public void setGuidaCarr(boolean guidaCarr) {
//		this.guidaCarr = guidaCarr;
//	}
//	/**
//	 * @return the poliR6
//	 */
//	public boolean isPoliR6() {
//		return poliR6;
//	}
//	/**
//	 * @param poliR6 the poliR6 to set
//	 */
//	public void setPoliR6(boolean poliR6) {
//		this.poliR6 = poliR6;
//	}
//	/**
//	 * @return the pinzaR7
//	 */
//	public boolean isPinzaR7() {
//		return pinzaR7;
//	}
//	/**
//	 * @param pinzaR7 the pinzaR7 to set
//	 */
//	public void setPinzaR7(boolean pinzaR7) {
//		this.pinzaR7 = pinzaR7;
//	}
//	/**
//	 * @return the unimog
//	 */
//	public boolean isUnimog() {
//		return unimog;
//	}
//	/**
//	 * @param unimog the unimog to set
//	 */
//	public void setUnimog(boolean unimog) {
//		this.unimog = unimog;
//	}
//	/**
//	 * @return the trial
//	 */
//	public boolean isTrial() {
//		return trial;
//	}
//	/**
//	 * @param trial the trial to set
//	 */
//	public void setTrial(boolean trial) {
//		this.trial = trial;
//	}
//	/**
//	 * @return the c9
//	 */
//	public boolean isC9() {
//		return c9;
//	}
//	/**
//	 * @param c9 the c9 to set
//	 */
//	public void setC9(boolean c9) {
//		this.c9 = c9;
//	}
//	/**
//	 * @return the gommone
//	 */
//	public boolean isGommone() {
//		return gommone;
//	}
//	/**
//	 * @param gommone the gommone to set
//	 */
//	public void setGommone(boolean gommone) {
//		this.gommone = gommone;
//	}
//	/**
//	 * @return the motoAcqua
//	 */
//	public boolean isMotoAcqua() {
//		return motoAcqua;
//	}
//	/**
//	 * @param motoAcqua the motoAcqua to set
//	 */
//	public void setMotoAcqua(boolean motoAcqua) {
//		this.motoAcqua = motoAcqua;
//	}
//	/**
//	 * @return the soccAcquatico
//	 */
//	public boolean isSoccAcquatico() {
//		return soccAcquatico;
//	}
//	/**
//	 * @param soccAcquatico the soccAcquatico to set
//	 */
//	public void setSoccAcquatico(boolean soccAcquatico) {
//		this.soccAcquatico = soccAcquatico;
//	}
//	/**
//	 * @return the idPerson
//	 */
//	public Integer getIdPerson() {
//		return idPerson;
//	}
//	/**
//	 * @param idPerson the idPerson to set
//	 */
//	public void setIdPerson(Integer idPerson) {
//		this.idPerson = idPerson;
//	}

	/**
	 * @return the idVigile
	 */
	public Integer getIdVigile() {
		return idVigile;
	}

	/**
	 * @param idVigile the idVigile to set
	 */
	public void setIdVigile(Integer idVigile) {
		this.idVigile = idVigile;
	}
	/**
	 * @return the descrPerson
	 */
	public String getDescrPerson() {
		return descrPerson;
	}
	/**
	 * @param descrPerson the descrPerson to set
	 */
	public void setDescrPerson(String descrPerson) {
		this.descrPerson = descrPerson;
	}


}
