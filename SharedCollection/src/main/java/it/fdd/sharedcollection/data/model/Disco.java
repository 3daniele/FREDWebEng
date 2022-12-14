package it.fdd.sharedcollection.data.model;

import it.fdd.framework.data.DataItem;

import java.util.ArrayList;
import java.util.Date;

public interface Disco extends DataItem<Integer> {

    String getNome();

    String getEtichetta();

    Date getAnno();

    Artista getArtista();

    Utente getCreatore();

    void setNome(String nome);

    void setEtichetta(String etichetta);

    void setAnno(Date anno);

    void setArtista(Artista artista);

    void setCreatore(Utente creatore);
}
