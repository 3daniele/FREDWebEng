package it.fdd.sharedcollection.data.proxy;

import it.fdd.framework.data.DataException;
import it.fdd.framework.data.DataItemProxy;
import it.fdd.framework.data.DataLayer;
import it.fdd.sharedcollection.data.dao.UtenteDAO;
import it.fdd.sharedcollection.data.impl.CollezioneImpl;
import it.fdd.sharedcollection.data.model.Utente;

import java.util.Date;
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
        //notare come l'autore in relazione venga caricato solo su richiesta
        //note how the related Utente is loaded only after it is requested
        if (super.getUtente() == null && utente_key > 0) {
            try {
                super.setUtente(((UtenteDAO) dataLayer.getDAO(Utente.class)).getUtente(utente_key));
            } catch (DataException ex) {
                Logger.getLogger(CollezioneProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //attenzione: l'autore caricato viene lagato all'oggetto in modo da non 
        //dover venir ricaricato alle richieste successive, tuttavia, questo
        //puo' rende i dati potenzialmente disallineati: se l'autore viene modificato
        //nel DB, qui rimarr� la sua "vecchia" versione
        //warning: the loaded Utente is embedded in this object so that further
        //requests do not require its reloading. However, this may cause data
        //problems since if the Utente is updated in the DB, here its "old"
        //version will be still attached
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
    public void setDataCreazione(Date data) {
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

    public void setUtenteKey(int utnete_key) {
        this.utente_key = utente_key;
        //resettiamo la cache dell'autore
        //reset author cache
        super.setUtente(null);
    }
}
