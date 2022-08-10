package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.*;
import it.fdd.sharedcollection.data.model.ListaBrani;
import it.fdd.sharedcollection.data.proxy.ListaBraniProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListaBraniDAO_MySQL extends DAO implements ListaBraniDAO {

    private PreparedStatement sListaBraniByID;
    private PreparedStatement sListeBrani;
    private PreparedStatement iListaBrani, uListaBrani, dListaBrani;

    public ListaBraniDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            // precompilazione di tutte le query utilizzate nella classe
            sListaBraniByID = connection.prepareStatement("SELECT * FROM ListaBrani WHERE id = ?");
            sListeBrani = connection.prepareStatement("SELECT id AS listaBraniID FROM ListaBrani");
            iListaBrani = connection.prepareStatement("INSERT INTO ListaBrani (disco, canzone) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
            uListaBrani = connection.prepareStatement("UPDATE ListaBrani SET disco = ?, canzone = ? WHERE id = ?");
            dListaBrani = connection.prepareStatement("DELETE FROM ListaBrani WHERE id = ?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del DataLayer", ex);
        }
    }

    // chiusura PreparedStatements
    @Override
    public void destroy() throws DataException {
        try {
            sListaBraniByID.close();
            sListeBrani.close();
            iListaBrani.close();
            uListaBrani.close();
            dListaBrani.close();
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public ListaBraniProxy createListaBrani() {
        return new ListaBraniProxy(getDataLayer());
    }

    // helper
    private ListaBraniProxy createListaBrani(ResultSet rs) throws DataException {
        ListaBraniProxy listaBrani = createListaBrani();
        try {
            listaBrani.setKey(rs.getInt("id"));
            listaBrani.setDiscoKey(rs.getInt("disco"));
            listaBrani.setCanzoneKey(rs.getInt("canzone"));
        } catch (SQLException ex) {
            throw new DataException("Impossibile creare l'oggetto ListaBrani dal ResultSet", ex);
        }
        return listaBrani;
    }

    @Override
    public ListaBrani getListaBrani(int listaBrani_key) throws DataException {

        ListaBrani listaBrani = null;

        // controllo se l'oggetto è presente nella cache
        if (dataLayer.getCache().has(ListaBrani.class, listaBrani_key)) {
            listaBrani = dataLayer.getCache().get(ListaBrani.class, listaBrani_key);
        } else {
            // altrimenti caricamento dal database
            try {
                sListaBraniByID.setInt(1, listaBrani_key);
                try (ResultSet rs = sListaBraniByID.executeQuery()) {
                    if (rs.next()) {
                        listaBrani = createListaBrani(rs);
                        // aggiunta nella cache
                        dataLayer.getCache().add(ListaBrani.class, listaBrani);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Impossibile caricare la lista dei brani dall'ID", ex);
            }
        }
        return listaBrani;
    }

    @Override
    public List<ListaBrani> getListeBrani() throws DataException {

        List<ListaBrani> result = new ArrayList<>();

        try (ResultSet rs = sListeBrani.executeQuery()) {
            while (rs.next()) {
                result.add((ListaBrani) getListaBrani(rs.getInt("listaBraniID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare la lista dei brani", ex);
        }
        return result;
    }

    @Override
    public void storeListaBrani(ListaBrani listaBrani) throws DataException {

        try {
            if (listaBrani.getKey() != null && listaBrani.getKey() > 0) {

                // non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                if (listaBrani instanceof DataItemProxy && !((DataItemProxy) listaBrani).isModified()) {
                    return;
                }

                // update
                if (listaBrani.getDisco() != null) {
                    uListaBrani.setInt(1, listaBrani.getDisco().getKey());
                } else {
                    uListaBrani.setNull(1, java.sql.Types.INTEGER);
                }

                if (listaBrani.getCanzone() != null) {
                    uListaBrani.setInt(2, listaBrani.getCanzone().getKey());
                } else {
                    uListaBrani.setNull(2, java.sql.Types.INTEGER);
                }

                if (uListaBrani.executeUpdate() == 0) {
                    throw new OptimisticLockException(listaBrani);
                }
            } else {
                // insert
                if (listaBrani.getDisco() != null) {
                    iListaBrani.setInt(1, listaBrani.getDisco().getKey());
                } else {
                    iListaBrani.setNull(1, java.sql.Types.INTEGER);
                }

                if (listaBrani.getCanzone() != null) {
                    iListaBrani.setInt(2, listaBrani.getCanzone().getKey());
                } else {
                    iListaBrani.setNull(2, java.sql.Types.INTEGER);
                }

                if (iListaBrani.executeUpdate() == 1) {
                    // get della chiave generata
                    try (ResultSet keys = iListaBrani.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            // update chiave
                            listaBrani.setKey(key);
                            // inserimento nella cache
                            dataLayer.getCache().add(ListaBrani.class, listaBrani);
                        }
                    }
                }
            }
            // reset attributo dirty
            if (listaBrani instanceof DataItemProxy) {
                ((DataItemProxy) listaBrani).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Impossibile salvare la listaBrani", ex);
        }
    }
}
