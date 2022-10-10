package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.*;
import it.fdd.sharedcollection.data.model.Genere;
import it.fdd.sharedcollection.data.proxy.GenereProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenereDAO_MySQL extends DAO implements GenereDAO {

    private PreparedStatement sGenereByID;
    private PreparedStatement sGeneri;


    private PreparedStatement iGenere;
    private PreparedStatement uGenere;
    private PreparedStatement dGenere;


    public GenereDAO_MySQL(DataLayer d) {
        super(d);
    }


    @Override
    public void init() throws DataException {
        try {
            super.init();
            // precompilazione di tutte le query utilizzate nella classe
            sGenereByID = connection.prepareStatement("SELECT * FROM Genere WHERE id = ?");
            sGeneri = connection.prepareStatement("SELECT * FROM Genere");
            iGenere = connection.prepareStatement("INSERT INTO Genere (nome) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            uGenere = connection.prepareStatement("UPDATE Genere SET nome = ? WHERE id = ?");
            dGenere = connection.prepareStatement("DELETE FROM Genere WHERE id = ?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del DataLayer", ex);
        }
    }

    // chiusura PreparedStatements
    @Override
    public void destroy() throws DataException {
        try {
            sGenereByID.close();
            sGeneri.close();
            iGenere.close();
            uGenere.close();
            dGenere.close();
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public GenereProxy createGenere() {
        return new GenereProxy(getDataLayer());
    }

    private GenereProxy createGenere(ResultSet rs) throws DataException {
        GenereProxy genere = createGenere();
        try {
            genere.setKey(rs.getInt("id"));
            genere.setNome(rs.getString("nome"));
        } catch (SQLException ex) {
            throw new DataException("Impossibile creare l'oggetto Canzone dal ResultSet", ex);
        }
        return genere;
    }


    @Override
    public Genere getGenere(int genere_key) throws DataException {

        Genere genere = null;

        // controllo se l'oggetto è presente nella cache
        if (dataLayer.getCache().has(Genere.class, genere_key)) {
            genere = dataLayer.getCache().get(Genere.class, genere_key);
        } else {
            // altrimenti caricamento dal database
            try {
                sGenereByID.setInt(1, genere_key);
                try (ResultSet rs = sGenereByID.executeQuery()) {
                    if (rs.next()) {
                        genere = createGenere(rs);
                        // aggiunta nella cache
                        dataLayer.getCache().add(Genere.class, genere);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Impossibile caricare il genere  dall'ID", ex);
            }
        }
        return genere;
    }


    @Override
    public List<Genere> getListaGeneri() throws DataException {

        List<Genere> result = new ArrayList<>();

        try (ResultSet rs = sGeneri.executeQuery()) {
            while (rs.next()) {
                result.add((Genere) getGenere(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare i generi", ex);
        }
        return result;
    }


    @Override
    public void storeGenere(Genere genere) throws DataException {

        try {
            if (genere.getKey() != null && genere.getKey() > 0) {

                // non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                if (genere instanceof DataItemProxy && !((DataItemProxy) genere).isModified()) {
                    return;
                }

                // update
                uGenere.setString(1, genere.getNome());


                if (uGenere.executeUpdate() == 0) {
                    throw new OptimisticLockException(genere);
                }
            } else {
                // insert
                iGenere.setString(1, genere.getNome());


                if (iGenere.executeUpdate() == 1) {
                    // get della chiave generata
                    try (ResultSet keys = iGenere.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            // update chiave
                            genere.setKey(key);
                            // inserimento nella cache
                            dataLayer.getCache().add(Genere.class, genere);
                        }
                    }
                }
            }
            // reset attributo dirty
            if (genere instanceof DataItemProxy) {
                ((DataItemProxy) genere).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Impossibile salvare il genere", ex);
        }
    }


}
