package it.fdd.sharedcollection.data.impl;

import it.fdd.framework.data.DataItemImpl;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.Utente;
import it.fdd.sharedcollection.data.model.UtentiAutorizzati;

public class UtentiAutorizzatiImpl extends DataItemImpl<Integer> implements UtentiAutorizzati {

    private Collezione collezione;
    private Utente utente;

    public UtentiAutorizzatiImpl() {
        super();
        this.collezione = null;
        this.utente = null;
    }

    @Override
    public Collezione getCollezione() {
        return this.collezione;
    }

    @Override
    public Utente getUtente() {
        return this.utente;
    }

    @Override
    public void setCollezione(Collezione collezione) {
        this.collezione = collezione;
    }

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}
