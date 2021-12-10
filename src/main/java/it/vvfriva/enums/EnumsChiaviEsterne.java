package it.vvfriva.enums;

import java.util.ArrayList;
import java.util.List;

import it.vvfriva.utils.Utils;

/**
 * Enums per gestione integrita referenziale
 *  
 * @param tabella
 * @param tabellaForeignKey
 * @param fieldIdTabella
 * @param fieldIdTabellaForeignKey
 * @param message
 * @param updateAction
 * @param deleteAction
 */
public enum EnumsChiaviEsterne {
	
	FK_ARTICOLO_1("articoli", "articoli_depositi", "id", "id_articolo", "Dato utilizzato in Deposito", EnumsChiaviEsterneAction.NO_ACTION, EnumsChiaviEsterneAction.RESTRICT),
	FK_ARTICOLO_2("articoli", "articoli_categorie", "id", "id_articolo", "Dato utilizzato in Categorie", EnumsChiaviEsterneAction.NO_ACTION, EnumsChiaviEsterneAction.RESTRICT),
	FK_ARTICOLO_3("articoli", "vigile_dotazione", "id", "id_articolo", "Dato utilizzato in Dotazione vigile", EnumsChiaviEsterneAction.NO_ACTION, EnumsChiaviEsterneAction.RESTRICT),
	FK_LICENZE_VIGILE_1("vigile_licenses", "expiry", "id", "id_riferimento", "Dato utilizzato in Scadenze", EnumsChiaviEsterneAction.NO_ACTION, EnumsChiaviEsterneAction.RESTRICT, "id_area = 5"),
	FK_CERTIFICATI_VIGILE_1("vigile_certified", "expiry", "id", "id_riferimento", "Dato utilizzato in Scadenze", EnumsChiaviEsterneAction.NO_ACTION, EnumsChiaviEsterneAction.RESTRICT, "id_area = 2"),
	FK_DEPOSITO_1("deposito", "articoli_depositi", "id", "id_deposito", "Dato utilizzato in Articoli", EnumsChiaviEsterneAction.NO_ACTION, EnumsChiaviEsterneAction.RESTRICT),
	FK_CATEGORIA_1("categorie", "articoli_categorie", "id", "id_categoria", "Dato utilizzato in Articoli", EnumsChiaviEsterneAction.NO_ACTION, EnumsChiaviEsterneAction.RESTRICT);
	
	private String tabella;
	private String tabellaForeignKey;
	private String fieldIdTabella;
	private String fieldIdTabellaForeignKey;
	private String message;
	private EnumsChiaviEsterneAction updateAction;
	private EnumsChiaviEsterneAction deleteAction;
	private String extraCondition;
	
	EnumsChiaviEsterne(String tabella, String tabellaForegn, String fieldIdTabella,
			String fieldIdTabellaForegn, String message, EnumsChiaviEsterneAction updateAction, EnumsChiaviEsterneAction deleteAction) {
		this.tabella = tabella;
		this.tabellaForeignKey = tabellaForegn;
		this.fieldIdTabella = fieldIdTabella;
		this.fieldIdTabellaForeignKey = fieldIdTabellaForegn;
		this.message = message;
		this.updateAction = updateAction;
		this.deleteAction = deleteAction;
	}
	
	EnumsChiaviEsterne(String tabella, String tabellaForegn, String fieldIdTabella,
			String fieldIdTabellaForegn, String message, EnumsChiaviEsterneAction updateAction, EnumsChiaviEsterneAction deleteAction, String extraCondition) {
		this.tabella = tabella;
		this.tabellaForeignKey = tabellaForegn;
		this.fieldIdTabella = fieldIdTabella;
		this.fieldIdTabellaForeignKey = fieldIdTabellaForegn;
		this.message = message;
		this.updateAction = updateAction;
		this.deleteAction = deleteAction;
		this.extraCondition = extraCondition;
	}
	/**
	 * @return the tabella
	 */
	public String getTabella() {
		return tabella;
	}
	/**
	 * @param tabella the tabella to set
	 */
	public void setTabella(String tabella) {
		this.tabella = tabella;
	}
	/**
	 * @return the tabellaForegn
	 */
	public String getTabellaForeignKey() {
		return tabellaForeignKey;
	}
	/**
	 * @param tabellaForegn the tabellaForegn to set
	 */
	public void setTabellaForeignKey(String tabellaForegn) {
		this.tabellaForeignKey = tabellaForegn;
	}
	/**
	 * @return the fieldIdTabella
	 */
	public String getFieldIdTabella() {
		return fieldIdTabella;
	}
	/**
	 * @param fieldIdTabella the fieldIdTabella to set
	 */
	public void setFieldIdTabella(String fieldIdTabella) {
		this.fieldIdTabella = fieldIdTabella;
	}
	/**
	 * @return the fieldIdTabellaForegn
	 */
	public String getFieldIdTabellaForeignKey() {
		return fieldIdTabellaForeignKey;
	}
	/**
	 * @param fieldIdTabellaForegn the fieldIdTabellaForegn to set
	 */
	public void setFieldIdTabellaForeignKey(String fieldIdTabellaForegn) {
		this.fieldIdTabellaForeignKey = fieldIdTabellaForegn;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the updateAction
	 */
	public EnumsChiaviEsterneAction getUpdateAction() {
		return updateAction;
	}
	/**
	 * @param updateAction the updateAction to set
	 */
	public void setUpdateAction(EnumsChiaviEsterneAction updateAction) {
		this.updateAction = updateAction;
	}
	/**
	 * @return the deleteAction
	 */
	public EnumsChiaviEsterneAction getDeleteAction() {
		return deleteAction;
	}
	/**
	 * @param deleteAction the deleteAction to set
	 */
	public void setDeleteAction(EnumsChiaviEsterneAction deleteAction) {
		this.deleteAction = deleteAction;
	}
	/**
	 * 
	 * @param table
	 * @return
	 */
	public static List<EnumsChiaviEsterne> getForeignKeyTables(String table) {
		List<EnumsChiaviEsterne> result = new ArrayList<EnumsChiaviEsterne>();
		for (EnumsChiaviEsterne tabella: EnumsChiaviEsterne.values()) {
			if (Utils.stringEguals(table, tabella.getTabella())) {
				result.add(tabella);
			}
		}
		return result;
	}
	/**
	 * @return the extraCondition
	 */
	public String getExtraCondition() {
		return extraCondition;
	}
	/**
	 * @param extraCondition the extraCondition to set
	 */
	public void setExtraCondition(String extraCondition) {
		this.extraCondition = extraCondition;
	} 
}
