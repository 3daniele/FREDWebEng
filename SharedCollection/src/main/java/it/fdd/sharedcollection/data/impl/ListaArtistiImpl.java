package it.fdd.sharedcollection.data.impl;

import it.fdd.framework.data.DataItemImpl;
import it.fdd.sharedcollection.data.model.Artista;
import it.fdd.sharedcollection.data.model.Canzone;
import it.fdd.sharedcollection.data.model.ListaArtisti;

public class ListaArtistiImpl extends DataItemImpl<Integer> implements ListaArtisti {

    private Artista artista;
    private Canzone canzone;
    private String ruolo;

    public ListaArtistiImpl() {
        super();
        this.artista = null;
        this.canzone = null;
        this.ruolo = "";
    }

    @Override
    public Artista getArtista() {
        return this.artista;
    }

    @Override
    public Canzone getCanzone() {
        return this.canzone;
    }

    @Override
    public String getRuolo() {
        return this.ruolo;
    }

    @Override
    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    @Override
    public void setCanzone(Canzone canzone) {
        this.canzone = canzone;
    }

    @Override
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}
