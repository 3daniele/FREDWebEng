package it.fdd.sharedcollection.data.model;

import it.fdd.framework.data.DataItem;
import java.sql.Time;

public interface Canzone extends DataItem<Integer> {

    String getnome();

    Time getDurata();

    void setNome(String nome);

    void setDurata(Time durate);
}
