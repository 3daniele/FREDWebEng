package it.fdd.sharedcollection.data.proxy;

import it.fdd.framework.data.DataException;
import it.fdd.framework.data.DataItemProxy;
import it.fdd.framework.data.DataLayer;
import it.fdd.sharedcollection.data.dao.ArtistaDAO;
import it.fdd.sharedcollection.data.dao.CanzoneDAO;
import it.fdd.sharedcollection.data.impl.ListaArtistiImpl;
import it.fdd.sharedcollection.data.model.Artista;
import it.fdd.sharedcollection.data.model.Canzone;

import java.util.logging.Level;
import java.util.logging.Logger;


public class ListaArtistiProxy extends ListaArtistiImpl implements DataItemProxy {
    protected boolean modified;

    protected int key_artista = 0;

    protected int key_canzone = 0;

    protected DataLayer dataLayer;

    public ListaArtistiProxy(DataLayer dataLayer) {
        super();
        this.dataLayer = dataLayer;
        this.modified = false;
        this.key_artista = 0;
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
        this.modified = modified;

    }

    public void setArtista(Artista artista) {
        super.setArtista(artista);
        this.modified = true;

    }

    public void setCanzone(Canzone canzone) {
        super.setCanzone(canzone);
        this.modified = true;
    }

    public void setRuolo(String ruolo) {
        super.setRuolo(ruolo);
        this.modified = true;
    }

    public void setArtistaKey(int genereKey) {
        this.key_artista = genereKey;
        super.setArtista(null);
    }

    public void setCanzoneKey(int canzoneKey) {
        this.key_canzone = canzoneKey;
        super.setCanzone(null);
    }

    @Override
    public Artista getArtista() {

        if (super.getArtista() == null && key_artista > 0) {
            try {
                super.setArtista(((ArtistaDAO) dataLayer.getDAO(Artista.class)).getArtista(key_artista));
            } catch (DataException ex) {
                Logger.getLogger(ArtistaProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return super.getArtista();
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
