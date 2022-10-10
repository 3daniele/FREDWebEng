package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.*;
import it.fdd.sharedcollection.data.model.ListaArtisti;
import it.fdd.sharedcollection.data.proxy.ListaArtistiProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListaArtistiDAO_MySQL extends DAO implements ListaArtistiDAO {
    private PreparedStatement sArtistaByID, sArtistaByCanzone;
    private PreparedStatement sArtista;

    private PreparedStatement iLista;
    private PreparedStatement uLista;
    private PreparedStatement dLista;


    public ListaArtistiDAO_MySQL(DataLayer d) {
        super(d);
    }


    @Override
    public void init() throws DataException {
        try {
            super.init();
            // precompilazione di tutte le query utilizzate nella classe
            sArtistaByID = connection.prepareStatement("SELECT * FROM ListaArtisti WHERE id = ?");
            sArtista = connection.prepareStatement("SELECT * FROM ListaArtisti");
            sArtistaByCanzone = connection.prepareStatement("SELECT * FROM ListaArtisti WHERE canzone = ?");
            iLista = connection.prepareStatement(" INSERT INTO ListaArtisti (ruolo, artista,canzone) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uLista = connection.prepareStatement("UPDATE ListaArtisti SET ruolo = ?, artista = ?, canzone = ? WHERE id = ?");
            dLista = connection.prepareStatement("DELETE FROM ListaArtisti WHERE id = ?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del DataLayer", ex);
        }
    }

    // chiusura PreparedStatements
    @Override
    public void destroy() throws DataException {
        try {
            sArtistaByID.close();
            sArtistaByCanzone.close();
            sArtista.close();
            iLista.close();
            uLista.close();
            dLista.close();
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public ListaArtistiProxy createListaArtisti() {
        return new ListaArtistiProxy(getDataLayer());
    }

    private ListaArtistiProxy createListaArtisti(ResultSet rs) throws DataException {
        ListaArtistiProxy artista = createListaArtisti();
        try {
            artista.setKey(rs.getInt("id"));
            artista.setArtistaKey(rs.getInt("artista"));
            artista.setCanzoneKey(rs.getInt("canzone"));
            artista.setRuolo(rs.getString("ruolo"));
        } catch (SQLException ex) {
            throw new DataException("Impossibile creare l'artista dal ResultSet", ex);
        }
        return artista;
    }


    @Override
    public ListaArtisti getArtista(int key) throws DataException {

        ListaArtisti listaArtisti = null;

        // controllo se l'oggetto è presente nella cache
        if (dataLayer.getCache().has(ListaArtisti.class, key)) {
            listaArtisti = dataLayer.getCache().get(ListaArtisti.class, key);
        } else {
            // altrimenti caricamento dal database
            try {
                sArtistaByID.setInt(1, key);
                try (ResultSet rs = sArtistaByID.executeQuery()) {
                    if (rs.next()) {
                        listaArtisti = createListaArtisti(rs);
                        // aggiunta nella cache
                        dataLayer.getCache().add(ListaArtisti.class, listaArtisti);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Impossibile caricare l'elemento   dall'ID", ex);
            }
        }
        return listaArtisti;
    }


    @Override
    public List<ListaArtisti> getListaArtisti() throws DataException {

        List<ListaArtisti> result = new ArrayList<>();

        try (ResultSet rs = sArtista.executeQuery()) {
            while (rs.next()) {
                result.add((ListaArtisti) getArtista(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare ", ex);
        }
        return result;
    }


    public List<ListaArtisti> getListaCanzoniByArtista(int artista_key) throws DataException {

        return null;
    }


    public List<ListaArtisti> getListaArtistiByCanzone(int canzone_key) throws DataException {

        List<ListaArtisti> result = new ArrayList<>();

        try {
            sArtistaByCanzone.setInt(1, canzone_key);
            try (ResultSet rs = sArtistaByCanzone.executeQuery()) {
                while (rs.next()) {
                    result.add((ListaArtisti) getArtista(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare ", ex);
        }
        return result;
    }

    @Override
    public List<ListaArtisti> getListaRuoliByCanzone(int canzone_key) throws DataException {
        return null;
    }

    @Override
    public List<ListaArtisti> getListaRuoliByArtista(int artista_key) throws DataException {
        return null;
    }

    @Override
    public List<ListaArtisti> getListaArtistiByRuolo(int ruolo_key) throws DataException {
        return null;
    }

    @Override
    public List<ListaArtisti> getListaCanzoniByRuolo(int ruolo_key) throws DataException {
        return null;
    }


    @Override
    public void storeListaArtisti(ListaArtisti listaArtisti) throws DataException {

        try {
            if (listaArtisti.getKey() != null && listaArtisti.getKey() > 0) {


                if (listaArtisti instanceof DataItemProxy && !((DataItemProxy) listaArtisti).isModified()) {
                    return;
                }

                // update
                uLista.setString(1, listaArtisti.getRuolo());
                uLista.setInt(2, listaArtisti.getArtista().getKey());
                uLista.setInt(3, listaArtisti.getCanzone().getKey());

                if (uLista.executeUpdate() == 0) {
                    throw new OptimisticLockException(listaArtisti);
                }
            } else {
                // insert
                iLista.setString(1, listaArtisti.getRuolo());
                iLista.setInt(2, listaArtisti.getArtista().getKey());
                iLista.setInt(3, listaArtisti.getCanzone().getKey());


                if (iLista.executeUpdate() == 1) {
                    // get della chiave generata
                    try (ResultSet keys = iLista.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            // update chiave
                            listaArtisti.setKey(key);
                            // inserimento nella cache
                            dataLayer.getCache().add(ListaArtisti.class, listaArtisti);
                        }
                    }
                }
            }
            // reset attributo dirty
            if (listaArtisti instanceof DataItemProxy) {
                ((DataItemProxy) listaArtisti).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Impossibile salvare artista", ex);
        }
    }


}
