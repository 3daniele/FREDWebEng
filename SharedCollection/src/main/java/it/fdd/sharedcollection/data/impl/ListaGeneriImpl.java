package it.fdd.sharedcollection.data.impl;

import it.fdd.framework.data.DataItemImpl;
import it.fdd.sharedcollection.data.model.Canzone;
import it.fdd.sharedcollection.data.model.Genere;
import it.fdd.sharedcollection.data.model.ListaGeneri;

public class ListaGeneriImpl extends DataItemImpl<Integer> implements ListaGeneri {

    private Canzone canzone;
    private Genere genere;

    public ListaGeneriImpl() {
        super();
        this.canzone = null;
        this.genere = null;
    }

    @Override
    public Canzone getCanzone() {
        return this.canzone;
    }

    @Override
    public Genere getGenere() {
        return this.genere;
    }

    @Override
    public void setCanzone(Canzone canzone) {
        this.canzone = canzone;
    }

    @Override
    public void setGenere(Genere genere) {
        this.genere = genere;
    }
}
