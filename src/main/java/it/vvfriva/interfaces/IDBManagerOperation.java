package it.vvfriva.interfaces;

import java.util.List;

import it.vvfriva.utils.ResponseMessage;

public interface IDBManagerOperation<T> {
	
	void save(T object);
	void update(T object);
	void delete(T object);
	
	boolean controllaCampiObbligatori(T object, List<ResponseMessage> msg);
	default void operazioneDopoInserimento(T object) {return;}
	default void operazionePrimaDiInserire(T object) {return;}
	
	default void operazioneDopoModifica(T object)  {return;}
	default void operazionePrimaDiModificare(T object) {return;}
	
	default void operazioneDopoCancellazione(T object) {return;}
	default void operazionePrimaDiCancellare(T object) {return;}

}
