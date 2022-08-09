package it.fdd.sharedcollection.data.impl;

import it.fdd.framework.data.DataItemImpl;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.Utente;

import java.util.Date;

public class CollezioneImpl extends DataItemImpl<Integer> implements Collezione {

    private Utente utente;
    private String nome;
    private String condivisione;
    private Date dataCreazione;

    public CollezioneImpl() {
        super();
        this.utente = null;
        this.nome = "";
        this.condivisione = "";
        this.dataCreazione = null;
    }

    @Override
    public Utente getUtente() {
        return this.utente;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public String getCondivisione() {
        return this.condivisione;
    }

    @Override
    public Date getDataCreazione() {
        return this.dataCreazione;
    }

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setCondivisione(String condivisione) {
        this.condivisione = condivisione;
    }

    @Override
    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }
}
