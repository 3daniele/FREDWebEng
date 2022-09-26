package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DataException;
import it.fdd.sharedcollection.data.model.ListaArtisti;

import java.util.List;


public interface ListaArtistiDAO {

    ListaArtisti createListaArtisti();

    ListaArtisti getArtista(int key) throws DataException;

    List<ListaArtisti> getListaArtisti() throws DataException;

    List<ListaArtisti> getListaCanzoniByArtista(int artista_key) throws DataException;

    List<ListaArtisti> getListaArtistiByCanzone(int canzone_key) throws DataException;

    List<ListaArtisti> getListaRuoliByCanzone(int canzone_key) throws DataException;

    List<ListaArtisti> getListaRuoliByArtista(int artista_key) throws DataException;

    List<ListaArtisti> getListaArtistiByRuolo(int ruolo_key) throws DataException;

    List<ListaArtisti> getListaCanzoniByRuolo(int ruolo_key) throws DataException;

    void storeListaArtisti(ListaArtisti element) throws DataException;


}
