package it.vvfriva.interfaces;

import java.util.List;

import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.ResponseMessage;

public interface IDBManagerOperation<T> {
	
	void save(T object) throws CustomException, Exception;
	void update(T object) throws CustomException, Exception;
	void delete(T object) throws CustomException, Exception;
	
	boolean controllaCampiObbligatori(T object, List<ResponseMessage> msg) throws CustomException, Exception;
	default void operazioneDopoInserimento(T object)  throws Exception, CustomException {return;}
	default void operazionePrimaDiInserire(T object) throws Exception, CustomException  {return;}
	
	default void operazioneDopoModifica(T object)  throws Exception, CustomException {return;}
	default void operazionePrimaDiModificare(T object) throws Exception, CustomException  {return;}
	
	default void operazioneDopoCancellazione(T object)  throws Exception, CustomException {return;}
	default void operazionePrimaDiCancellare(T object) throws Exception, CustomException  {return;}

}
