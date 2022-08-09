package it.fdd.sharedcollection.data.impl;

import it.fdd.framework.data.DataItemImpl;
import it.fdd.sharedcollection.data.model.Artista;

public class ArtistaImpl extends DataItemImpl<Integer> implements Artista {

    private String nome;

    private String cognome;

    private String nomeArte;

    public ArtistaImpl() {
        super();
        this.nome = "";
        this.cognome = "";
        this.nomeArte = "";
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public String getCognome() {
        return this.cognome;
    }

    @Override
    public String getNomeArte() {
        return this.nomeArte;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public void setNomeArte(String nomeArte) {
        this.nomeArte = nomeArte;
    }
}
