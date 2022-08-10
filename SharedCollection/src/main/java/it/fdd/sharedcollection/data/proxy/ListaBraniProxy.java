package it.fdd.sharedcollection.data.proxy;

import it.fdd.framework.data.DataException;
import it.fdd.framework.data.DataItemProxy;
import it.fdd.framework.data.DataLayer;
import it.fdd.sharedcollection.data.dao.CanzoneDAO;
import it.fdd.sharedcollection.data.dao.DiscoDAO;
import it.fdd.sharedcollection.data.impl.ListaBraniImpl;
import it.fdd.sharedcollection.data.model.Canzone;
import it.fdd.sharedcollection.data.model.Disco;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ListaBraniProxy extends ListaBraniImpl implements DataItemProxy {

    protected boolean modified;
    protected int disco_key;
    protected int canzone_key;
    protected DataLayer dataLayer;

    public ListaBraniProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.modified = false;
        this.disco_key = 0;
        this.canzone_key = 0;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public Disco getDisco() {
        if (super.getDisco() == null && disco_key > 0) {
            try {
                super.setDisco(((DiscoDAO) dataLayer.getDAO(Disco.class)).getDisco(disco_key));
            } catch (DataException ex) {
                Logger.getLogger(DiscoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getDisco();
    }

    @Override
    public Canzone getCanzone() {
        if (super.getCanzone() == null && canzone_key > 0) {
            try {
                super.setCanzone(((CanzoneDAO) dataLayer.getDAO(Canzone.class)).getCanzone(canzone_key));
            } catch (DataException ex) {
                Logger.getLogger(CanzoneProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getCanzone();
    }

    @Override
    public void setDisco(Disco disco) {
        super.setDisco(disco);
        this.disco_key = disco.getKey();
        this.modified = true;
    }

    @Override
    public void setCanzone(Canzone canzone) {
        super.setCanzone(canzone);
        this.canzone_key = canzone.getKey();
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

    public void setDiscoKey(int disco_key) {
        this.disco_key = disco_key;
        super.setDisco(null);
    }

    public void setCanzoneKey(int canzone_key) {
        this.canzone_key = canzone_key;
        super.setCanzone(null);
    }

}
