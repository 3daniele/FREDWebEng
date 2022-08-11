package it.fdd.sharedcollection.data.proxy;

import it.fdd.framework.data.DataException;
import it.fdd.framework.data.DataItemProxy;
import it.fdd.framework.data.DataLayer;
import it.fdd.sharedcollection.data.dao.CollezioneDAO;
import it.fdd.sharedcollection.data.dao.UtenteDAO;
import it.fdd.sharedcollection.data.impl.UtentiAutorizzatiImpl;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.Utente;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UtentiAutorizzatiProxy extends UtentiAutorizzatiImpl implements DataItemProxy {
    protected boolean modified;
    protected int utente_key;
    protected int collezione_key;
    protected DataLayer dataLayer;

    public UtentiAutorizzatiProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.modified = false;
        this.collezione_key = 0;
        this.utente_key = 0;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public Collezione getCollezione() {
        if (super.getCollezione() == null && collezione_key > 0) {
            try {
                super.setCollezione(((CollezioneDAO) dataLayer.getDAO(Collezione.class)).getCollezione(collezione_key));
            } catch (DataException ex) {
                Logger.getLogger(DiscoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getCollezione();
    }

    @Override
    public Utente getUtente() {
        if (super.getUtente() == null && utente_key > 0) {
            try {
                super.setUtente(((UtenteDAO) dataLayer.getDAO(Utente.class)).getUtente(utente_key));
            } catch (DataException ex) {
                Logger.getLogger(CanzoneProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getUtente();
    }

    @Override
    public void setCollezione(Collezione collezione) {
        super.setCollezione(collezione);
        this.collezione_key = collezione.getKey();
        this.modified = true;
    }

    @Override
    public void setUtente(Utente utente) {
        super.setUtente(utente);
        this.utente_key = utente.getKey();
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

    public void setUtenteKey(int utente_key) {
        this.utente_key = utente_key;
        super.setUtente(null);
    }

    public void setCollezioneKey(int collezione_key) {
        this.collezione_key = collezione_key;
        super.setCollezione(null);
    }
}
