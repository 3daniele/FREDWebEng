package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DataException;
import it.fdd.sharedcollection.data.model.ListaGeneri;

import java.util.List;

public interface ListaGeneriDAO {
    ListaGeneri createListaGenere();

    ListaGeneri getGenere(int genere_key) throws DataException;


    List<ListaGeneri> getListaGeneri() throws DataException;

    List<ListaGeneri> getListaGeneriByCanzone(int canzone_key) throws DataException;

    void storeListaGeneri(ListaGeneri element) throws DataException;

    void deleteListaGeneri(ListaGeneri listaGeneri) throws DataException;
}
