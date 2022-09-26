package it.fdd.sharedcollection.data.dao;

import it.fdd.sharedcollection.data.model.Utente;
import it.fdd.framework.data.DataException;

import java.util.List;

public interface UtenteDAO {

    Utente createUtente();

    Utente getUtente(int utente_key) throws DataException;

    List<Utente> getUtenti() throws DataException;

    List<Integer> getUtenteInfo(int utente_key) throws DataException;

    Utente getUtente(String email) throws DataException;

    Utente getUtenteByUsername(String username) throws DataException;

    Utente login(String email, String password) throws DataException;

    String getPassword(String email) throws DataException;

    int getToken(Utente utente) throws DataException;

    Utente getLink(String link) throws DataException;

    void insertLink(Utente utente, String link) throws DataException;

    void insertToken(Utente utente, int token) throws DataException;


    Utente storeUtente(Utente utente) throws DataException;


}
