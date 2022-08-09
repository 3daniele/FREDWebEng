package it.fdd.sharedcollection.data.model;

import it.fdd.framework.data.DataItem;

public interface ListaArtisti extends DataItem<Integer> {

    Artista getArtista();

    Canzone getCanzone();

    String getRuolo();

    void setArtista(Artista artista);

    void setCanzone(Canzone canzone);

    void setRuolo(String ruolo);

}
