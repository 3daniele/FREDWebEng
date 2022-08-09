package it.fdd.sharedcollection.data.model;

import it.fdd.framework.data.DataItem;

import java.util.Date;

public interface Collezione extends DataItem<Integer> {

    Utente getUtente();

    String getNome();

    String getCondivisione();

    Date getDataCreazione();

    void setUtente(Utente utente);

    void setNome(String nome);

    void setCondivisione(String condivisione);

    void setDataCreazione(Date dataCreazione);
}
