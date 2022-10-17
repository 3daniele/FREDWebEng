package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DataException;
import it.fdd.sharedcollection.data.model.Canzone;
import it.fdd.sharedcollection.data.model.ListaBrani;

import java.util.HashSet;
import java.util.List;

public interface ListaBraniDAO {

    ListaBrani createListaBrani();

    ListaBrani getListaBrani(int listaBrani_key) throws DataException;

    List<ListaBrani> getListeBrani() throws DataException;

    List<ListaBrani> getListeBrani(int disco_key) throws DataException;

    HashSet<ListaBrani> getListeBrani(Canzone canzone) throws DataException;

    void storeListaBrani(ListaBrani listaBrani) throws DataException;

    void deleteListaBrani(ListaBrani listaBrani) throws DataException;

}
