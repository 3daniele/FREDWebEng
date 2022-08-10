package it.fdd.sharedcollection.data.dao;
import it.fdd.framework.data.DataException;
import it.fdd.sharedcollection.data.model.Genere;
import java.util.List;

public interface GenereDAO {

    Genere createGenere();

    Genere getGenere(int genere_key) throws DataException;

    int getNumeroGeneri() throws DataException;

    List<Genere> getListaGeneri() throws DataException;


    void storeGenere(Genere c) throws DataException;







}
