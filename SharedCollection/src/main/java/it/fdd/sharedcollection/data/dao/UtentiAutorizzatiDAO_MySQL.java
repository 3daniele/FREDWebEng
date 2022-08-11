package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.*;
import it.fdd.sharedcollection.data.model.UtentiAutorizzati;
import it.fdd.sharedcollection.data.proxy.UtentiAutorizzatiProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UtentiAutorizzatiDAO_MySQL extends DAO implements UtentiAutorizzatiDAO {
    private PreparedStatement sUtentiAutorizzatiByID;
    private PreparedStatement sUtentiAutorizzati;
    private PreparedStatement iUtentiAutorizzati, uUtentiAutorizzati, dUtentiAutorizzati;

    public UtentiAutorizzatiDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            // precompilazione di tutte le query utilizzate nella classe
            sUtentiAutorizzatiByID = connection.prepareStatement("SELECT * FROM UtentiAutorizzati WHERE id = ?");
            sUtentiAutorizzati = connection.prepareStatement("SELECT id AS ID FROM UtentiAutorizzati");
            iUtentiAutorizzati = connection.prepareStatement("INSERT INTO UtentiAutorizzati (utente, collezione) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
            uUtentiAutorizzati = connection.prepareStatement("UPDATE UtentiAutorizzati SET utente = ?, collezione = ? WHERE id = ?");
            dUtentiAutorizzati = connection.prepareStatement("DELETE FROM UtentiAutorizzati WHERE id = ?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del DataLayer", ex);
        }
    }

    // chiusura PreparedStatements
    @Override
    public void destroy() throws DataException {
        try {
            sUtentiAutorizzatiByID.close();
            sUtentiAutorizzati.close();
            iUtentiAutorizzati.close();
            uUtentiAutorizzati.close();
            dUtentiAutorizzati.close();
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public UtentiAutorizzatiProxy createUtentiAutorizzati() {
        return new UtentiAutorizzatiProxy(getDataLayer());
    }

    // helper
    private UtentiAutorizzatiProxy createUtentiAutorizzati(ResultSet rs) throws DataException {
        UtentiAutorizzatiProxy utentiAutorizzati = createUtentiAutorizzati();
        try {
            utentiAutorizzati.setKey(rs.getInt("id"));
            utentiAutorizzati.setUtenteKey(rs.getInt("disco"));
            utentiAutorizzati.setCollezioneKey(rs.getInt("canzone"));
        } catch (SQLException ex) {
            throw new DataException("Impossibile creare l'oggetto UtentiAutotizzati dal ResultSet", ex);
        }
        return utentiAutorizzati;
    }

    @Override
    public UtentiAutorizzati getUtentiAutorizzati(int utentiAutorizzati_key) throws DataException {

        UtentiAutorizzati utentiAutorizzati = null;

        // controllo se l'oggetto è presente nella cache
        if (dataLayer.getCache().has(UtentiAutorizzati.class, utentiAutorizzati_key)) {
            utentiAutorizzati = dataLayer.getCache().get(UtentiAutorizzati.class, utentiAutorizzati_key);
        } else {
            // altrimenti caricamento dal database
            try {
                sUtentiAutorizzatiByID.setInt(1, utentiAutorizzati_key);
                try (ResultSet rs = sUtentiAutorizzatiByID.executeQuery()) {
                    if (rs.next()) {
                        utentiAutorizzati = createUtentiAutorizzati(rs);
                        // aggiunta nella cache
                        dataLayer.getCache().add(UtentiAutorizzati.class, utentiAutorizzati);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Impossibile caricare la lista degli utenti autorizzati dall'ID", ex);
            }
        }
        return utentiAutorizzati;
    }

    @Override
    public List<UtentiAutorizzati> getUtentiAutorizzati() throws DataException {

        List<UtentiAutorizzati> result = new ArrayList<>();

        try (ResultSet rs = sUtentiAutorizzati.executeQuery()) {
            while (rs.next()) {
                result.add((UtentiAutorizzati) getUtentiAutorizzati(rs.getInt("ID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare la lista degli utenti autorizzati", ex);
        }
        return result;
    }

    @Override
    public void storeUtentiAutorizzati(UtentiAutorizzati utentiAutorizzati) throws DataException {

        try {
            if (utentiAutorizzati.getKey() != null && utentiAutorizzati.getKey() > 0) {

                // non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                if (utentiAutorizzati instanceof DataItemProxy && !((DataItemProxy) utentiAutorizzati).isModified()) {
                    return;
                }

                // update
                if (utentiAutorizzati.getUtente() != null) {
                    uUtentiAutorizzati.setInt(1, utentiAutorizzati.getUtente().getKey());
                } else {
                    uUtentiAutorizzati.setNull(1, java.sql.Types.INTEGER);
                }

                if (utentiAutorizzati.getCollezione() != null) {
                    uUtentiAutorizzati.setInt(2, utentiAutorizzati.getCollezione().getKey());
                } else {
                    uUtentiAutorizzati.setNull(2, java.sql.Types.INTEGER);
                }

                if (uUtentiAutorizzati.executeUpdate() == 0) {
                    throw new OptimisticLockException(utentiAutorizzati);
                }
            } else {
                // insert
                if (utentiAutorizzati.getUtente() != null) {
                    iUtentiAutorizzati.setInt(1, utentiAutorizzati.getUtente().getKey());
                } else {
                    iUtentiAutorizzati.setNull(1, java.sql.Types.INTEGER);
                }

                if (utentiAutorizzati.getCollezione() != null) {
                    iUtentiAutorizzati.setInt(2, utentiAutorizzati.getCollezione().getKey());
                } else {
                    iUtentiAutorizzati.setNull(2, java.sql.Types.INTEGER);
                }

                if (iUtentiAutorizzati.executeUpdate() == 1) {
                    // get della chiave generata
                    try (ResultSet keys = iUtentiAutorizzati.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            // update chiave
                            utentiAutorizzati.setKey(key);
                            // inserimento nella cache
                            dataLayer.getCache().add(UtentiAutorizzati.class, utentiAutorizzati);
                        }
                    }
                }
            }
            // reset attributo dirty
            if (utentiAutorizzati instanceof DataItemProxy) {
                ((DataItemProxy) utentiAutorizzati).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Impossibile salvare gli utenti autorizzati", ex);
        }
    }

}
