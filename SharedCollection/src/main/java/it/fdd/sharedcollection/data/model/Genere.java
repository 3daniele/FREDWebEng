package it.fdd.sharedcollection.data.model;

import it.fdd.framework.data.DataItem;

public interface Genere extends DataItem<Integer> {

    String getNome();

    void setNome(String nome);
}
