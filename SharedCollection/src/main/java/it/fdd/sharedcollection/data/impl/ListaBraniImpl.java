package it.fdd.sharedcollection.data.impl;

import it.fdd.framework.data.DataItemImpl;
import it.fdd.sharedcollection.data.model.Canzone;
import it.fdd.sharedcollection.data.model.Disco;
import it.fdd.sharedcollection.data.model.ListaBrani;

public class ListaBraniImpl extends DataItemImpl<Integer> implements ListaBrani {

    private Disco disco;
    private Canzone canzone;

    public ListaBraniImpl() {
        super();
        this.disco = null;
        this.canzone = null;
    }

    @Override
    public Disco getDisco() {
        return this.disco;
    }

    @Override
    public Canzone getCanzone() {
        return this.canzone;
    }

    @Override
    public void setDisco(Disco disco) {
        this.disco = disco;
    }

    @Override
    public void setCanzone(Canzone canzone) {
        this.canzone = canzone;
    }
}
