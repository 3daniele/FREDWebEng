package it.fdd.sharedcollection.data.proxy;

import it.fdd.framework.data.DataItemProxy;
import it.fdd.framework.data.DataLayer;
import it.fdd.sharedcollection.data.impl.ArtistaImpl;

import java.sql.Time;

public class ArtistaProxy extends ArtistaImpl implements DataItemProxy {

    protected boolean modified;
    protected DataLayer dataLayer;

    public ArtistaProxy(DataLayer d) {
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
    public void setCognome(String cognome) {
        super.setCognome(cognome);
        this.modified = true;
    }

    @Override
    public void setNomeArte(String nomeArte) {
        super.setNomeArte(nomeArte);
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
