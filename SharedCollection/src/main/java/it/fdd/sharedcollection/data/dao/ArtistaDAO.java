package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DataException;
import it.fdd.sharedcollection.data.model.Artista;

import java.util.List;

public interface ArtistaDAO {

    Artista createArtista();

    Artista getArtista(int artista_key) throws DataException;

    Artista getArtista(String nomeArte) throws DataException;

    List<Artista> getArtisti() throws DataException;

    void storeArtista(Artista artista) throws DataException;

}
