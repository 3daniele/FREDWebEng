package it.fdd.sharedcollection.data.proxy;

import it.fdd.framework.data.DataItemProxy;
import it.fdd.framework.data.DataLayer;
import it.fdd.sharedcollection.data.impl.DiscoImpl;

import java.util.Date;

public class DiscoProxy extends DiscoImpl implements DataItemProxy {

    protected boolean modified;
    protected DataLayer dataLayer;

    public DiscoProxy(DataLayer d) {
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
    public void setEtichetta(String etichetta) {
        super.setEtichetta(etichetta);
        this.modified = true;
    }

    @Override
    public void setAnno(Date anno) {
        super.setAnno(anno);
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
