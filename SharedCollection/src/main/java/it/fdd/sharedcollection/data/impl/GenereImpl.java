package it.fdd.sharedcollection.data.impl;

import it.fdd.framework.data.DataItemImpl;
import it.fdd.sharedcollection.data.model.Genere;

public class GenereImpl extends DataItemImpl<Integer> implements Genere {

    private String nome;

    public GenereImpl() {
        super();
        this.nome = "";
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }
}
