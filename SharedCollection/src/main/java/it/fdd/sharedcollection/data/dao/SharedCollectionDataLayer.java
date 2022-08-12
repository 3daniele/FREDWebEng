package it.fdd.sharedcollection.data.dao;

import it.fdd.sharedcollection.data.model.Canzone;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.Disco;
import it.fdd.sharedcollection.data.model.Utente;
import it.fdd.sharedcollection.data.model.Artista;
import it.fdd.sharedcollection.data.model.Genere;
import it.fdd.sharedcollection.data.model.ListaArtisti;
import it.fdd.sharedcollection.data.model.ListaBrani;
import it.fdd.sharedcollection.data.model.ListaDischi;
import it.fdd.sharedcollection.data.model.ListaGeneri;
import it.fdd.sharedcollection.data.model.UtentiAutorizzati;
import it.fdd.framework.data.DataException;
import it.fdd.framework.data.DataLayer;

import java.sql.SQLException;
import javax.sql.DataSource;

public class SharedCollectionDataLayer extends DataLayer {

    public SharedCollectionDataLayer(DataSource datasource) throws SQLException {
        super(datasource);
    }

    @Override
    public void init() throws DataException {
        //registriamo i nostri dao
        //register our daos
        registerDAO(Artista.class, new ArtistaDAO_MySQL(this));
        registerDAO(Canzone.class, new CanzoneDAO_MySQL(this));
        registerDAO(Collezione.class, new CollezioneDAO_MySQL(this));
        registerDAO(Disco.class, new DiscoDAO_MySQL(this));
        registerDAO(Genere.class, new GenereDAO_MySQL(this));
        registerDAO(ListaArtisti.class, new ListaArtistiDAO_MySQL(this));
        registerDAO(ListaBrani.class, new ListaBraniDAO_MySQL(this));
        registerDAO(ListaDischi.class, new ListaDischiDAO_MySQL(this));
        registerDAO(ListaGeneri.class, new ListaGeneriDAO_MySQL(this));
        registerDAO(Utente.class, new UtenteDAO_MySQL(this));
        registerDAO(UtentiAutorizzati.class, new UtentiAutorizzatiDAO_MySQL(this));
    }

    //helpers
    public ArtistaDAO getArtistaDAO() {
        return (ArtistaDAO) getDAO(Artista.class);
    }

    public CanzoneDAO getCanzoneDAO() {
        return (CanzoneDAO) getDAO(Canzone.class);
    }

    public CollezioneDAO getCollezioneDAO() {
        return (CollezioneDAO) getDAO(Collezione.class);
    }

    public DiscoDAO getDiscoDAO() {
        return (DiscoDAO) getDAO(Disco.class);
    }

    public GenereDAO getGenereDAO() {
        return (GenereDAO) getDAO(Genere.class);
    }

    public ListaArtistiDAO getListaArtistiDAO() {
        return (ListaArtistiDAO) getDAO(ListaArtisti.class);
    }

    public ListaBraniDAO getListaBraniDAO() {
        return (ListaBraniDAO) getDAO(ListaBrani.class);
    }

    public ListaDischiDAO getListaDischiDAO() {
        return (ListaDischiDAO) getDAO(ListaDischi.class);
    }

    public ListaGeneriDAO getListaGeneriDAO() {
        return (ListaGeneriDAO) getDAO(ListaGeneri.class);
    }

    public UtenteDAO getUtenteDAO() {
        return (UtenteDAO) getDAO(Utente.class);
    }

    public UtentiAutorizzatiDAO getUtentiAutorizzatiDAO() {
        return (UtentiAutorizzatiDAO) getDAO(UtentiAutorizzati.class);
    }

}