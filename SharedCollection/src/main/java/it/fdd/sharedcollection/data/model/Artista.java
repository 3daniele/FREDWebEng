package it.fdd.sharedcollection.data.model;

import it.fdd.framework.data.DataItem;

public interface Artista extends DataItem<Integer> {

    String getNome();

    String getCognome();

    String getNomeArte();

    void setNome(String nome);

    void setCognome(String cognome);

    void setNomeArte(String nomeArte);
}
