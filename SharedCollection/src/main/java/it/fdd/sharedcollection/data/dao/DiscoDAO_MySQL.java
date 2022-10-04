package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DAO;
import it.fdd.framework.data.DataException;
import it.fdd.framework.data.DataItemProxy;
import it.fdd.framework.data.DataLayer;
import it.fdd.framework.data.OptimisticLockException;
import it.fdd.sharedcollection.data.model.Disco;
import it.fdd.sharedcollection.data.model.ListaDischi;
import it.fdd.sharedcollection.data.proxy.DiscoProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DiscoDAO_MySQL extends DAO implements DiscoDAO {

    private PreparedStatement sDiscoByID;
    private PreparedStatement sDischi;
    private PreparedStatement iDisco, uDisco, dDisco;

    public DiscoDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            // precompilazione di tutte le query utilizzate nella classe
            sDiscoByID = connection.prepareStatement("SELECT * FROM Disco WHERE id = ?");
            sDischi = connection.prepareStatement("SELECT id AS discoID FROM Disco");
            iDisco = connection.prepareStatement("INSERT INTO Disco (nome, etichetta, anno, artista) VALUES(?,?,?, ?)", Statement.RETURN_GENERATED_KEYS);
            uDisco = connection.prepareStatement("UPDATE Disco SET nome = ?, etichetta = ?, anno = ?, artista = ? WHERE id = ?");
            dDisco = connection.prepareStatement("DELETE FROM Disco WHERE id = ?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del DataLayer", ex);
        }
    }

    // chiusura PreparedStatements
    @Override
    public void destroy() throws DataException {
        try {
            sDiscoByID.close();
            sDischi.close();
            iDisco.close();
            uDisco.close();
            dDisco.close();
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public DiscoProxy createDisco() {
        return new DiscoProxy(getDataLayer());
    }

    // helper
    private DiscoProxy createDisco(ResultSet rs) throws DataException {
        DiscoProxy disco = createDisco();
        try {
            disco.setKey(rs.getInt("id"));
            disco.setNome(rs.getString("nome"));
            disco.setEtichetta(rs.getString("etichetta"));
            disco.setAnno(rs.getDate("anno"));
            disco.setArtistaKey(rs.getInt("artista"));
            disco.setCreatoreKey(rs.getInt("creatore"));
        } catch (SQLException ex) {
            throw new DataException("Impossibile creare l'oggetto Disco dal ResultSet", ex);
        }
        return disco;
    }

    @Override
    public Disco getDisco(int disco_key) throws DataException {

        Disco disco = null;

        // controllo se l'oggetto è presente nella cache
        if (dataLayer.getCache().has(Disco.class, disco_key)) {
            disco = dataLayer.getCache().get(Disco.class, disco_key);
        } else {
            // altrimenti caricamento dal database
            try {
                sDiscoByID.setInt(1, disco_key);
                try (ResultSet rs = sDiscoByID.executeQuery()) {
                    if (rs.next()) {
                        disco = createDisco(rs);
                        // aggiunta nella cache
                        dataLayer.getCache().add(Disco.class, disco);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Impossibile caricare disco dall'ID", ex);
            }
        }
        return disco;
    }

    @Override
    public List<Disco> getDischi() throws DataException {

        List<Disco> result = new ArrayList<>();

        try (ResultSet rs = sDischi.executeQuery()) {
            while (rs.next()) {
                result.add((Disco) getDisco(rs.getInt("discoID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare i dischi", ex);
        }
        return result;
    }

    @Override
    public void storeDisco(Disco disco) throws DataException {

        try {
            if (disco.getKey() != null && disco.getKey() > 0) {

                // non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                if (disco instanceof DataItemProxy && !((DataItemProxy) disco).isModified()) {
                    return;
                }

                // update
                uDisco.setString(1, disco.getNome());
                uDisco.setString(2, disco.getEtichetta());
                uDisco.setDate(3, (Date) disco.getAnno());
                uDisco.setInt(4, disco.getArtista().getKey());

                if (uDisco.executeUpdate() == 0) {
                    throw new OptimisticLockException(disco);
                }
            } else {
                // insert
                iDisco.setString(1, disco.getNome());
                iDisco.setString(2, disco.getEtichetta());
                iDisco.setDate(3, (Date) disco.getAnno());
                iDisco.setInt(4, disco.getArtista().getKey());

                if (iDisco.executeUpdate() == 1) {
                    // get della chiave generata
                    try (ResultSet keys = iDisco.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            // update chiave
                            disco.setKey(key);
                            // inserimento nella cache
                            dataLayer.getCache().add(Disco.class, disco);
                        }
                    }
                }
            }
            // reset attributo dirty
            if (disco instanceof DataItemProxy) {
                ((DataItemProxy) disco).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Impossibile salvare il disco", ex);
        }
    }
}
