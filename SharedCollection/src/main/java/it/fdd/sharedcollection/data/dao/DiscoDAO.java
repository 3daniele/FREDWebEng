package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DataException;
import it.fdd.sharedcollection.data.model.Artista;
import it.fdd.sharedcollection.data.model.Disco;
import it.fdd.sharedcollection.data.model.ListaDischi;

import java.util.HashSet;
import java.util.List;

public interface DiscoDAO {

    Disco createDisco();

    Disco getDisco(int disco_key) throws DataException;

    Disco getLast() throws DataException;

    List<Disco> getDischi() throws DataException;

    HashSet<Disco> getDischi(Artista artista) throws DataException;

    void storeDisco(Disco disco) throws DataException;

}
