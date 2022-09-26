package it.fdd.sharedcollection.data.impl;

import it.fdd.framework.data.DataItemImpl;
import it.fdd.sharedcollection.data.model.Canzone;

import java.sql.Time;

public class CanzoneImpl extends DataItemImpl<Integer> implements Canzone {

    private String nome;

    private Time durata;

    public CanzoneImpl() {
        super();
        this.nome = "";
        this.durata = null;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public Time getDurata() {
        return this.durata;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setDurata(Time durata) {
        this.durata = durata;
    }
}
