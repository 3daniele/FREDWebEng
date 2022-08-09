package it.fdd.sharedcollection.data.model;

import it.fdd.framework.data.DataItem;

public interface ListaBrani extends DataItem<Integer> {

    Disco getDisco();

    Canzone getCanzone();

    void setDisco(Disco disco);

    void setCanzone(Canzone canzone);
}
