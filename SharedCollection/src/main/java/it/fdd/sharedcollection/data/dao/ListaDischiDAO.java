package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DataException;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.ListaDischi;

import java.util.List;

public interface ListaDischiDAO {

    ListaDischi createListaDischi();

    ListaDischi getListaDischi(int listaDischi_key) throws DataException;

    ListaDischi getListaDisco(int collezione_key, int disco_key, String formato) throws DataException;

    List<ListaDischi> getListeDischi() throws DataException;

    List<ListaDischi> getDischiByCollezione(int collezione_key) throws DataException;

    void storeListaDischi(ListaDischi listaDischi) throws DataException;

    void deleteListaDischi(ListaDischi listaDischi) throws DataException;

}
