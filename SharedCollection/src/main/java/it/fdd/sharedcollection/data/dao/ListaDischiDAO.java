package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DataException;
import it.fdd.sharedcollection.data.model.ListaDischi;

import java.util.List;

public interface ListaDischiDAO {

    ListaDischi createListaDischi();

    ListaDischi getListaDischi(int listaDischi_key) throws DataException;

    List<ListaDischi> getListeBrani() throws DataException;

    void storeListaDischi(ListaDischi listaDischi) throws DataException;

}
