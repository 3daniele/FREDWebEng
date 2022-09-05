package it.fdd.sharedcollection.data.proxy;

import it.fdd.framework.data.DataException;
import it.fdd.framework.data.DataItemProxy;
import it.fdd.framework.data.DataLayer;
import it.fdd.sharedcollection.data.dao.CanzoneDAO;
import it.fdd.sharedcollection.data.dao.GenereDAO;
import it.fdd.sharedcollection.data.impl.ListaGeneriImpl;
import it.fdd.sharedcollection.data.model.Canzone;
import it.fdd.sharedcollection.data.model.Genere;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ListaGeneriProxy extends ListaGeneriImpl implements DataItemProxy {


    private boolean modified; 
    
    private int key_genere = 0;

    private int key_canzone = 0;

    private final DataLayer dataLayer;

    public ListaGeneriProxy(DataLayer dataLayer) {
        super();
        this.dataLayer = dataLayer;
        this.modified = false;
        this.key_genere = 0;
        this.key_canzone = 0;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public boolean isModified() {
        return this.modified;
    }

    @Override
    public void setModified(boolean modified) {
        this.modified=modified;

    }

  public   void setCanzone(Canzone canzone){
        super.setCanzone(canzone);
        this.modified = true;
    }

    public void setGenere(Genere genere){
        super.setGenere(genere);
        this.modified = true;
    }

    public void setGenereKey(int genereKey) {
        this.key_genere = genereKey;
        super.setGenere(null);
    }
    public void setCanzoneKey(int canzoneKey) {
        this.key_canzone = canzoneKey;
        super.setCanzone(null);
    }

    @Override
    public Genere getGenere() {

        if (super.getGenere() == null && key_genere > 0) {
            try {
                super.setGenere(((GenereDAO) dataLayer.getDAO(Genere.class)).getGenere(key_genere));
            } catch (DataException ex) {
                Logger.getLogger(GenereProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return super.getGenere();
    }

    @Override
    public Canzone getCanzone() {

        if (super.getCanzone() == null && key_canzone > 0) {
            try {
                super.setCanzone(((CanzoneDAO) dataLayer.getDAO(Canzone.class)).getCanzone(key_canzone));
            } catch (DataException ex) {
                Logger.getLogger(CanzoneProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return super.getCanzone();
    }
}
