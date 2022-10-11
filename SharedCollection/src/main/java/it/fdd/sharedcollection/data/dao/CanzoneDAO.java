package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DataException;
import it.fdd.sharedcollection.data.model.Canzone;

import java.util.List;

public interface CanzoneDAO {

    Canzone createCanzone();

    Canzone getCanzone(int canzone_key) throws DataException;

    Canzone getLast() throws DataException;

    List<Canzone> getCanzoni() throws DataException;

    void storeCanzone(Canzone canzone) throws DataException;

}
