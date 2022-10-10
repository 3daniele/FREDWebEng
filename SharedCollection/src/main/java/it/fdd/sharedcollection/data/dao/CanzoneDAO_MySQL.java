package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DAO;
import it.fdd.framework.data.DataException;
import it.fdd.framework.data.DataItemProxy;
import it.fdd.framework.data.DataLayer;
import it.fdd.framework.data.OptimisticLockException;
import it.fdd.sharedcollection.data.model.Canzone;
import it.fdd.sharedcollection.data.proxy.CanzoneProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CanzoneDAO_MySQL extends DAO implements CanzoneDAO {

    private PreparedStatement sCanzoneByID;
    private PreparedStatement sCanzoni;
    private PreparedStatement iCanzone, uCanzone, dCanzone;

    public CanzoneDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            // precompilazione di tutte le query utilizzate nella classe
            sCanzoneByID = connection.prepareStatement("SELECT * FROM Canzone WHERE id = ?");
            sCanzoni = connection.prepareStatement("SELECT id AS canzoneID FROM Canzone");
            iCanzone = connection.prepareStatement("INSERT INTO Canzone (nome, durata) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            uCanzone = connection.prepareStatement("UPDATE Canzone SET nome = ?, durata = ? WHERE id = ?");
            dCanzone = connection.prepareStatement("DELETE FROM Canzone WHERE id = ?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del DataLayer", ex);
        }
    }

    // chiusura PreparedStatements
    @Override
    public void destroy() throws DataException {
        try {
            sCanzoneByID.close();
            sCanzoni.close();
            iCanzone.close();
            uCanzone.close();
            dCanzone.close();
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public CanzoneProxy createCanzone() {
        return new CanzoneProxy(getDataLayer());
    }

    // helper
    private CanzoneProxy createCanzone(ResultSet rs) throws DataException {
        CanzoneProxy canzone = createCanzone();
        try {
            canzone.setKey(rs.getInt("id"));
            canzone.setNome(rs.getString("nome"));
            canzone.setDurata(rs.getTime("durata"));
        } catch (SQLException ex) {
            throw new DataException("Impossibile creare l'oggetto Canzone dal ResultSet", ex);
        }
        return canzone;
    }

    @Override
    public Canzone getCanzone(int canzone_key) throws DataException {

        Canzone canzone = null;

        // controllo se l'oggetto è presente nella cache
        if (dataLayer.getCache().has(Canzone.class, canzone_key)) {
            canzone = dataLayer.getCache().get(Canzone.class, canzone_key);
        } else {
            // altrimenti caricamento dal database
            try {
                sCanzoneByID.setInt(1, canzone_key);
                try (ResultSet rs = sCanzoneByID.executeQuery()) {
                    if (rs.next()) {
                        canzone = createCanzone(rs);
                        // aggiunta nella cache
                        dataLayer.getCache().add(Canzone.class, canzone);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Impossibile caricare canzone dall'ID", ex);
            }
        }
        return canzone;
    }

    @Override
    public List<Canzone> getCanzoni() throws DataException {

        List<Canzone> result = new ArrayList<>();

        try (ResultSet rs = sCanzoni.executeQuery()) {
            while (rs.next()) {
                result.add((Canzone) getCanzone(rs.getInt("canzoneID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare le canzoni", ex);
        }
        return result;
    }

    @Override
    public void storeCanzone(Canzone canzone) throws DataException {

        try {
            if (canzone.getKey() != null && canzone.getKey() > 0) {

                // non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                if (canzone instanceof DataItemProxy && !((DataItemProxy) canzone).isModified()) {
                    return;
                }

                // update
                uCanzone.setString(1, canzone.getNome());
                uCanzone.setTime(2, canzone.getDurata());
                uCanzone.setInt(3, canzone.getKey());

                if (uCanzone.executeUpdate() == 0) {
                    throw new OptimisticLockException(canzone);
                }
            } else {
                // insert
                iCanzone.setString(1, canzone.getNome());
                iCanzone.setTime(2, canzone.getDurata());

                if (iCanzone.executeUpdate() == 1) {
                    // get della chiave generata
                    try (ResultSet keys = iCanzone.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            // update chiave
                            canzone.setKey(key);
                            // inserimento nella cache
                            dataLayer.getCache().add(Canzone.class, canzone);
                        }
                    }
                }
            }
            // reset attributo dirty
            if (canzone instanceof DataItemProxy) {
                ((DataItemProxy) canzone).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Impossibile salvare la canzone", ex);
        }
    }
}
