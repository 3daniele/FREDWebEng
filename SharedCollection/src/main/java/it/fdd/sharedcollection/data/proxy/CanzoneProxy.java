package it.fdd.sharedcollection.data.proxy;

import it.fdd.framework.data.DataItemProxy;
import it.fdd.framework.data.DataLayer;
import it.fdd.sharedcollection.data.impl.CanzoneImpl;

import java.sql.Time;

public class CanzoneProxy extends CanzoneImpl implements DataItemProxy {

    protected boolean modified;
    protected DataLayer dataLayer;

    public CanzoneProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.modified = false;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    @Override
    public void setDurata(Time durata) {
        super.setDurata(durata);
        this.modified = true;
    }

    // metodi proxy
    @Override
    public boolean isModified() {
        return this.modified;
    }

    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }
}
