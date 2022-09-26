package it.fdd.sharedcollection.data.proxy;

import it.fdd.framework.data.DataItemProxy;
import it.fdd.framework.data.DataLayer;
import it.fdd.sharedcollection.data.impl.GenereImpl;

public class GenereProxy extends GenereImpl implements DataItemProxy {

    private boolean modified;
    private final DataLayer dataLayer;


    public GenereProxy(DataLayer dataLayer) {
        super();
        this.dataLayer = dataLayer;
        this.modified = false;
    }


    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    @Override
    public boolean isModified() {
        return this.modified;
    }

    @Override
    public void setModified(boolean modified) {
        this.modified = modified;

    }
}
