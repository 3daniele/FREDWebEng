package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DataException;
import it.fdd.sharedcollection.data.model.UtentiAutorizzati;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface UtentiAutorizzatiDAO {

    UtentiAutorizzati createUtentiAutorizzati();

    UtentiAutorizzati getUtentiAutorizzati(int utentiAutorizzati_key) throws DataException;

    List<UtentiAutorizzati> getUtentiAutorizzati() throws DataException;

    List<UtentiAutorizzati> getUtentiAutorizzatiByUser(int user_key) throws DataException;

    List<Integer> getUtentiAutorizzatiByCollezione(int collezione_key) throws DataException;

    void deleteUtenteAutorizzato(int collezioni_key, int user_key) throws DataException;

    void storeUtentiAutorizzati(UtentiAutorizzati utentiAutorizzati) throws DataException;
}
