package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DAO;
import it.fdd.framework.data.DataException;
import it.fdd.framework.data.DataItemProxy;
import it.fdd.framework.data.DataLayer;
import it.fdd.framework.data.OptimisticLockException;
import it.fdd.sharedcollection.data.model.Artista;
import it.fdd.sharedcollection.data.proxy.ArtistaProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArtistaDAO_MySQL extends DAO implements ArtistaDAO {

    private PreparedStatement sArtistaByID;
    private PreparedStatement sArtisti;
    private PreparedStatement iArtista, uArtista, dArtista;

    public ArtistaDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            // precompilazione di tutte le query utilizzate nella classe
            sArtistaByID = connection.prepareStatement("SELECT * FROM Artista WHERE id = ?");
            sArtisti = connection.prepareStatement("SELECT * FROM Artista ORDER BY nomeDArte");
            iArtista = connection.prepareStatement("INSERT INTO Artista (nome, cognome, nomeDArte) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            uArtista = connection.prepareStatement("UPDATE Artista SET nome = ?, cognome = ?, nomeDArte = ? WHERE id = ?");
            dArtista = connection.prepareStatement("DELETE FROM Artista WHERE id = ?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del DataLayer", ex);
        }
    }

    // chiusura PreparedStatements
    @Override
    public void destroy() throws DataException {
        try {
            sArtistaByID.close();
            sArtisti.close();
            iArtista.close();
            uArtista.close();
            dArtista.close();
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public ArtistaProxy createArtista() {
        return new ArtistaProxy(getDataLayer());
    }

    // helper
    private ArtistaProxy createArtista(ResultSet rs) throws DataException {
        ArtistaProxy artista = createArtista();
        try {
            artista.setKey(rs.getInt("id"));
            artista.setNome(rs.getString("nome"));
            artista.setCognome(rs.getString("cognome"));
            artista.setNomeArte(rs.getString("nomeDArte"));
        } catch (SQLException ex) {
            throw new DataException("Impossibile creare l'oggetto Artista dal ResultSet", ex);
        }
        return artista;
    }

    @Override
    public Artista getArtista(int artista_key) throws DataException {

        Artista artista = null;

        // controllo se l'oggetto è presente nella cache
        if (dataLayer.getCache().has(Artista.class, artista_key)) {
            artista = dataLayer.getCache().get(Artista.class, artista_key);
        } else {
            // altrimenti caricamento dal database
            try {
                sArtistaByID.setInt(1, artista_key);
                try (ResultSet rs = sArtistaByID.executeQuery()) {
                    if (rs.next()) {
                        artista = createArtista(rs);
                        // aggiunta nella cache
                        dataLayer.getCache().add(Artista.class, artista);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Impossibile caricare artista dall'ID", ex);
            }
        }
        return artista;
    }

    @Override
    public List<Artista> getArtisti() throws DataException {

        List<Artista> result = new ArrayList<>();

        try (ResultSet rs = sArtisti.executeQuery()) {
            while (rs.next()) {
                result.add((Artista) getArtista(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare gli artisti", ex);
        }
        return result;
    }

    @Override
    public void storeArtista(Artista artista) throws DataException {

        try {
            if (artista.getKey() != null && artista.getKey() > 0) {

                // non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                if (artista instanceof DataItemProxy && !((DataItemProxy) artista).isModified()) {
                    return;
                }

                // update
                uArtista.setString(1, artista.getNome());
                uArtista.setString(2, artista.getCognome());
                uArtista.setString(3, artista.getNomeArte());

                if (uArtista.executeUpdate() == 0) {
                    throw new OptimisticLockException(artista);
                }
            } else {
                // insert
                iArtista.setString(1, artista.getNome());
                iArtista.setString(2, artista.getCognome());
                iArtista.setString(3, artista.getNomeArte());

                if (iArtista.executeUpdate() == 1) {
                    // get della chiave generata
                    try (ResultSet keys = iArtista.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            // update chiave
                            artista.setKey(key);
                            // inserimento nella cache
                            dataLayer.getCache().add(Artista.class, artista);
                        }
                    }
                }
            }
            // reset attributo dirty
            if (artista instanceof DataItemProxy) {
                ((DataItemProxy) artista).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Impossibile salvare l'artista", ex);
        }
    }
}
