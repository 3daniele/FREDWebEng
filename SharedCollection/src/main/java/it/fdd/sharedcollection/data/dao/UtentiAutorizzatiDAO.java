package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DataException;
import it.fdd.sharedcollection.data.model.UtentiAutorizzati;

import java.util.List;

public interface UtentiAutorizzatiDAO {

    UtentiAutorizzati createUtentiAutorizzati();

    UtentiAutorizzati getUtentiAutorizzati(int utentiAutorizzati_key) throws DataException;

    List<Integer> getUtentiAutorizzatiByCollezione(int collezione_key) throws DataException;

    List<Integer> getUtentiAutorizzatiByUtente(int utente_key) throws DataException;

    void storeUtentiAutorizzati(UtentiAutorizzati utentiAutorizzati) throws DataException;
}
