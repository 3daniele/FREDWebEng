package it.fdd.sharedcollection.data.proxy;

import it.fdd.framework.data.DataException;
import it.fdd.framework.data.DataItemProxy;
import it.fdd.framework.data.DataLayer;
import it.fdd.sharedcollection.data.dao.ArtistaDAO;
import it.fdd.sharedcollection.data.impl.DiscoImpl;
import it.fdd.sharedcollection.data.model.Artista;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscoProxy extends DiscoImpl implements DataItemProxy {

    protected boolean modified;
    protected int artista_key;

    protected DataLayer dataLayer;

    public DiscoProxy(DataLayer d) {
        super();
        this.artista_key = 0;
        this.dataLayer = d;
        this.modified = false;
    }

    @Override
    public Artista getArtista() {
        if (super.getArtista() == null && artista_key > 0) {
            try {
                super.setArtista(((ArtistaDAO) dataLayer.getDAO(Artista.class)).getArtista(artista_key));
            } catch (DataException ex) {
                Logger.getLogger(CollezioneProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getArtista();
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

    @Override
    public void setArtista(Artista artista) {
        super.setArtista(artista);
        this.artista_key = artista.getKey();
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

    public void setArtistaKey(int artista_key) {
        this.artista_key = artista_key;
        super.setArtista(null);
    }
}
