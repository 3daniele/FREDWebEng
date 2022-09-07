package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DataException;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.UtentiAutorizzati;

import java.util.List;

public interface CollezioneDAO {

    Collezione createCollezione();

    Collezione getCollezione(int collezione_key) throws DataException;

    List<Collezione> getCollezioni() throws DataException;

    List<Collezione> getCollezioniPubbliche() throws DataException;

    List<Collezione> getCollezioniByUtente(int utente_key) throws DataException;

    List<Collezione> getCollezioniCondivise(List<UtentiAutorizzati> utenti) throws DataException;

    Collezione getLast() throws DataException;

    void storeCollezione(Collezione collezione) throws DataException;
}
