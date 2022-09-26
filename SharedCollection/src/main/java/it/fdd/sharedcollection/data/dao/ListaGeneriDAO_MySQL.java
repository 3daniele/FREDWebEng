package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.*;
import it.fdd.sharedcollection.data.model.ListaArtisti;
import it.fdd.sharedcollection.data.model.ListaGeneri;
import it.fdd.sharedcollection.data.proxy.ListaArtistiProxy;
import it.fdd.sharedcollection.data.proxy.ListaGeneriProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListaGeneriDAO_MySQL extends DAO implements ListaGeneriDAO {

    private PreparedStatement sGenereByID, sGenereByCanzone;
    private PreparedStatement sGenere;

    private PreparedStatement iLista;
    private PreparedStatement uLista;
    private PreparedStatement dLista;


    public ListaGeneriDAO_MySQL(DataLayer d) {
        super(d);
    }


    @Override
    public void init() throws DataException {
        try {
            super.init();
            // precompilazione di tutte le query utilizzate nella classe
            sGenereByID = connection.prepareStatement("SELECT * FROM ListaGeneri WHERE id = ?");
            sGenere = connection.prepareStatement("SELECT id as ListaGeneri_id FROM ListaGeneri WHERE id = ?");
            sGenereByCanzone = connection.prepareStatement("SELECT * FROM ListaGeneri WHERE canzone = ?");
            iLista = connection.prepareStatement(" INSERT INTO ListaGeneri ( genere,canzone) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            uLista = connection.prepareStatement("UPDATE ListaGeneri SET   genere = ?, canzone = ? WHERE id = ?");
            dLista = connection.prepareStatement("DELETE FROM ListaGeneri WHERE id = ?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del DataLayer", ex);
        }
    }

    // chiusura PreparedStatements
    @Override
    public void destroy() throws DataException {
        try {
            sGenereByID.close();
            sGenereByCanzone.close();
            sGenere.close();
            iLista.close();
            uLista.close();
            dLista.close();
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public ListaGeneriProxy createListaGenere() {
        return new ListaGeneriProxy(getDataLayer());
    }

    private ListaGeneriProxy createListaGenere(ResultSet rs) throws DataException {
        ListaGeneriProxy listaGenere = createListaGenere();
        try {
            listaGenere.setKey(rs.getInt("id"));
            listaGenere.setGenereKey(rs.getInt("genere"));
            listaGenere.setCanzoneKey(rs.getInt("canzone"));

        } catch (SQLException ex) {
            throw new DataException("Errore", ex);
        }
        return listaGenere;
    }

    @Override
    public ListaGeneri getGenere(int genere_key) throws DataException {
        ListaGeneri listaGeneri = null;

        // controllo se l'oggetto è presente nella cache
        if (dataLayer.getCache().has(ListaGeneri.class, genere_key)) {
            listaGeneri = dataLayer.getCache().get(ListaGeneri.class, genere_key);
        } else {
            // altrimenti caricamento dal database
            try {
                sGenereByID.setInt(1, genere_key);
                try (ResultSet rs = sGenereByID.executeQuery()) {
                    if (rs.next()) {
                        listaGeneri = createListaGenere(rs);
                        // aggiunta nella cache
                        dataLayer.getCache().add(ListaGeneri.class, listaGeneri);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Impossibile caricare l'elemento   dall'ID", ex);
            }
        }
        return listaGeneri;
    }

    @Override
    public List<ListaGeneri> getListaGeneri() throws DataException {
        List<ListaGeneri> result = new ArrayList<>();

        try (ResultSet rs = sGenere.executeQuery()) {
            while (rs.next()) {
                result.add((ListaGeneri) getGenere(rs.getInt("ListaArtisti_id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare ", ex);
        }
        return result;
    }

    @Override
    public List<ListaGeneri> getListaGeneriByCanzone(int canzone_key) throws DataException {

        List<ListaGeneri> result = new ArrayList<>();

        try {
            sGenereByCanzone.setInt(1, canzone_key);
            try (ResultSet rs = sGenereByCanzone.executeQuery()) {
                while (rs.next()) {
                    result.add((ListaGeneri) getGenere(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare ", ex);
        }
        return result;
    }

    @Override
    public void storeListaGeneri(ListaGeneri listaGeneri) throws DataException {
        try {
            if (listaGeneri.getKey() != null && listaGeneri.getKey() > 0) {


                if (listaGeneri instanceof DataItemProxy && !((DataItemProxy) listaGeneri).isModified()) {
                    return;
                }

                // update

                uLista.setInt(1, listaGeneri.getGenere().getKey());
                uLista.setInt(2, listaGeneri.getCanzone().getKey());

                if (uLista.executeUpdate() == 0) {
                    throw new OptimisticLockException(listaGeneri);
                }
            } else {
                // insert
                uLista.setInt(1, listaGeneri.getGenere().getKey());
                uLista.setInt(2, listaGeneri.getCanzone().getKey());


                if (iLista.executeUpdate() == 1) {
                    // get della chiave generata
                    try (ResultSet keys = iLista.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            // update chiave
                            listaGeneri.setKey(key);
                            // inserimento nella cache
                            dataLayer.getCache().add(ListaGeneri.class, listaGeneri);
                        }
                    }
                }
            }
            // reset attributo dirty
            if (listaGeneri instanceof DataItemProxy) {
                ((DataItemProxy) listaGeneri).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Impossibile salvare la GENERE", ex);
        }
    }
}
