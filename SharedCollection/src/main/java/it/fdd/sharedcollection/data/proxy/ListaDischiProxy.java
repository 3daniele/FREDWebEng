package it.fdd.sharedcollection.data.proxy;

import it.fdd.framework.data.DataException;
import it.fdd.framework.data.DataItemProxy;
import it.fdd.framework.data.DataLayer;
import it.fdd.sharedcollection.data.dao.CollezioneDAO;
import it.fdd.sharedcollection.data.dao.DiscoDAO;
import it.fdd.sharedcollection.data.impl.ListaDischiImpl;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.Disco;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ListaDischiProxy extends ListaDischiImpl implements DataItemProxy {
    protected boolean modified;
    protected int collezione_key;
    protected int disco_key;
    protected DataLayer dataLayer;

    public ListaDischiProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.modified = false;
        this.collezione_key = 0;
        this.disco_key = 0;
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
    public void setDisco(Disco disco) {
        super.setDisco(disco);
        this.disco_key = disco.getKey();
        this.modified = true;
    }

    @Override
    public void setCollezione(Collezione collezione) {
        super.setCollezione(collezione);
        this.collezione_key = collezione.getKey();
        this.modified = true;
    }

    @Override
    public void setNumeroCopie(int numeroCopie) {
        super.setNumeroCopie(numeroCopie);
        this.modified = true;
    }

    @Override
    public void setStato(String stato) {
        super.setStato(stato);
        this.modified = true;
    }

    @Override
    public void setImgCopertina(String imgCopertina) {
        super.setImgCopertina(imgCopertina);
        this.modified = true;
    }

    @Override
    public void setImgFronte(String imgFronte) {
        super.setImgFronte(imgFronte);
        this.modified = true;
    }

    @Override
    public void setImgRetro(String imgRetro) {
        super.setImgRetro(imgRetro);
        this.modified = true;
    }

    @Override
    public void setImgLibretto(String imgLibretto) {
        super.setImgLibretto(imgLibretto);
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

    public void setCollezioneKey(int collezione_key) {
        this.collezione_key= collezione_key;
        super.setCollezione(null);
    }
}
