package it.fdd.sharedcollection.data.model;

import it.fdd.framework.data.DataItem;

public interface UtentiAutorizzati extends DataItem<Integer> {

    Collezione getCollezione();

    Utente getUtente();

    void setCollezione(Collezione collezione);

    void setUtente(Utente utente);
}
