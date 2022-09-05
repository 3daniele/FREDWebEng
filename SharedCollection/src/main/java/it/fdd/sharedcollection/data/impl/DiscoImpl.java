package it.fdd.sharedcollection.data.impl;

import it.fdd.framework.data.DataItemImpl;
import it.fdd.sharedcollection.data.model.Artista;
import it.fdd.sharedcollection.data.model.Disco;

import java.util.Date;

public class DiscoImpl extends DataItemImpl<Integer> implements Disco {

    private String nome;
    private String etichetta;
    private Date anno;

    private Artista artista;

    public DiscoImpl() {
        super();
        this.nome = "";
        this.etichetta = "";
        this.anno = null;
        this.artista = null;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public String getEtichetta() {
        return this.etichetta;
    }

    @Override
    public Date getAnno() {
        return this.anno;
    }

    @Override
    public Artista getArtista() {
        return this.artista;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setEtichetta(String etichetta) {
        this.etichetta = etichetta;
    }

    @Override
    public void setAnno(Date anno) {
        this.anno = anno;
    }

    @Override
    public void setArtista(Artista artista) {
        this.artista = artista;
    }
}
