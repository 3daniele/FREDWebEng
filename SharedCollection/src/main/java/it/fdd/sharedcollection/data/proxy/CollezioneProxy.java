package it.fdd.sharedcollection.data.proxy;

import it.fdd.framework.data.DataException;
import it.fdd.framework.data.DataItemProxy;
import it.fdd.framework.data.DataLayer;
import it.fdd.sharedcollection.data.dao.UtenteDAO;
import it.fdd.sharedcollection.data.impl.CollezioneImpl;
import it.fdd.sharedcollection.data.model.Utente;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CollezioneProxy extends CollezioneImpl implements DataItemProxy {

    protected boolean modified;
    protected int utente_key;

    protected DataLayer dataLayer;

    public CollezioneProxy(DataLayer d) {
        super();

        this.dataLayer = d;
        this.modified = false;
        this.utente_key = 0;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public Utente getUtente() {
        if (super.getUtente() == null && utente_key > 0) {
            try {
                super.setUtente(((UtenteDAO) dataLayer.getDAO(Utente.class)).getUtente(utente_key));
            } catch (DataException ex) {
                Logger.getLogger(CollezioneProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getUtente();
    }

    @Override
    public void setUtente(Utente utente) {
        super.setUtente(utente);
        this.utente_key = utente.getKey();
        this.modified = true;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    @Override
    public void setDataCreazione(LocalDate data) {
        super.setDataCreazione(data);
        this.modified = true;
    }

    @Override
    public void setCondivisione(String condivisione) {
        super.setCondivisione(condivisione);
        this.modified = true;
    }

    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

    public boolean isModified() {
        return modified;
    }

    public void setUtenteKey(int utente_key) {
        this.utente_key = utente_key;
        super.setUtente(null);
    }
}
