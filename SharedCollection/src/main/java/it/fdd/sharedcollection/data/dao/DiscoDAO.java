package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DataException;
import it.fdd.sharedcollection.data.model.Disco;

import java.util.List;

public interface DiscoDAO {

    Disco createDisco();

    Disco getDisco(int disco_key) throws DataException;

    List<Disco> getDischi() throws DataException;

    void storeDisco(Disco disco) throws DataException;

}
