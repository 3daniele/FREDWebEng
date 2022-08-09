package it.fdd.sharedcollection.data.model;

import it.fdd.framework.data.DataItem;

public interface ListaGeneri extends DataItem<Integer> {

    Canzone getCanzone();

    Genere getGenere();

    void setCanzone(Canzone canzone);

    void setGenere(Genere genere);
}
