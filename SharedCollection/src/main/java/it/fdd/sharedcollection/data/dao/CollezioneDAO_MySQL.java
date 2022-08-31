package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.*;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.Utente;
import it.fdd.sharedcollection.data.model.UtentiAutorizzati;
import it.fdd.sharedcollection.data.proxy.CollezioneProxy;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CollezioneDAO_MySQL extends DAO implements CollezioneDAO {
    private PreparedStatement sCollezioneByID;
    private PreparedStatement sCollezioni, sCollezioniByUtente, sCollezioniPubbliche;
    private PreparedStatement iCollezione, uCollezione, dCollezione;

    public CollezioneDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            sCollezioneByID = connection.prepareStatement("SELECT * FROM collezione WHERE id=?");
            sCollezioni = connection.prepareStatement("SELECT * FROM collezione");
            sCollezioniByUtente = connection.prepareStatement("SELECT * FROM collezione WHERE utente=?");
            sCollezioniPubbliche = connection.prepareStatement("SELECT * FROM collezione WHERE condivisione='pubblica' LIMIT 6");

            iCollezione = connection.prepareStatement("INSERT INTO collezione (nome,condivisione,dataDiCreazione,utente) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uCollezione = connection.prepareStatement("UPDATE collezione SET nome=?,condivisione=?,dataDiCreazione=?,utente=? WHERE ID=?");
            dCollezione = connection.prepareStatement("DELETE FROM collezione WHERE ID=?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing SharedCollection data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {

            sCollezioneByID.close();
            sCollezioni.close();
            sCollezioniByUtente.close();
            sCollezioniPubbliche.close();

            iCollezione.close();
            uCollezione.close();
            dCollezione.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public CollezioneProxy createCollezione() {
        return new CollezioneProxy(getDataLayer());
    }

    private CollezioneProxy createCollezione(ResultSet rs) throws DataException {
        CollezioneProxy a = createCollezione();
        try {
            a.setKey(rs.getInt("id"));
            a.setNome(rs.getString("nome"));
            a.setUtenteKey(rs.getInt("utente"));
            a.setCondivisione(rs.getString("condivisione"));
            a.setDataCreazione(LocalDate.parse(rs.getDate("dataDiCreazione").toString()));
        } catch (SQLException ex) {
            throw new DataException("Unable to create Collezione object form ResultSet", ex);
        }
        return a;
    }

    @Override
    public Collezione getCollezione(int collezione_key) throws DataException {
        Collezione a = null;
        if (dataLayer.getCache().has(Collezione.class, collezione_key)) {
            a = dataLayer.getCache().get(Collezione.class, collezione_key);
        } else {
            try {
                sCollezioneByID.setInt(1, collezione_key);
                try (ResultSet rs = sCollezioneByID.executeQuery()) {
                    if (rs.next()) {
                        a = createCollezione(rs);
                        dataLayer.getCache().add(Collezione.class, a);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load article by ID", ex);
            }
        }
        return a;
    }

    @Override
    public List<Collezione> getCollezioni() throws DataException {
        List<Collezione> result = new ArrayList<>();

        try (ResultSet rs = sCollezioni.executeQuery()) {
            while (rs.next()) {
                result.add((Collezione) getCollezione(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Collezioni", ex);
        }
        return result;
    }

    @Override
    public List<Collezione> getCollezioniByUtente(int utente_key) throws DataException {

        List<Collezione> result = new ArrayList<>();

        try {
            sCollezioniByUtente.setInt(1, utente_key);
            try (ResultSet rs = sCollezioniByUtente.executeQuery()) {
                while (rs.next()) {
                    result.add((Collezione) getCollezione(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Collezione by Utente", ex);
        }
        return result;
    }

    @Override
    public List<Collezione> getCollezioniCondivise(List<UtentiAutorizzati> utenti) throws DataException {

        List<Collezione> result = new ArrayList<>();

        try {
            for (UtentiAutorizzati utente : utenti) {
                sCollezioneByID.setInt(1, utente.getCollezione().getKey());
                try (ResultSet rs = sCollezioneByID.executeQuery()) {
                    while (rs.next()) {
                        result.add((Collezione) getCollezione(rs.getInt("id")));
                    }
            }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Collezione by Utente", ex);
        }
        return result;
    }

    @Override
    public List<Collezione> getCollezioniPubbliche() throws DataException {
        List<Collezione> result = new ArrayList<>();

        try (ResultSet rs = sCollezioniPubbliche.executeQuery()) {
            while (rs.next()) {
                result.add((Collezione) getCollezione(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Collezioni", ex);
        }
        return result;
    }

    @Override
    public void storeCollezione(Collezione collezione) throws DataException {
        try {
            if (collezione.getKey() != null && collezione.getKey() > 0) {
                if (collezione instanceof DataItemProxy && !((DataItemProxy) collezione).isModified()) {
                    return;
                }
                uCollezione.setString(1, collezione.getNome());
                uCollezione.setString(2, collezione.getCondivisione());
                uCollezione.setDate(3, java.sql.Date.valueOf(collezione.getDataCreazione()));
                uCollezione.setInt(4, collezione.getUtente().getKey());
                uCollezione.setInt(5, collezione.getKey());

                if (uCollezione.executeUpdate() == 0) {
                    throw new OptimisticLockException(collezione);
                }
            } else {
                iCollezione.setString(1, collezione.getNome());
                iCollezione.setString(2, collezione.getCondivisione());
                iCollezione.setDate(3, java.sql.Date.valueOf(collezione.getDataCreazione()));
                iCollezione.setInt(4, collezione.getUtente().getKey());

                if (iCollezione.executeUpdate() == 1) {
                    try (ResultSet keys = iCollezione.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            collezione.setKey(key);
                            dataLayer.getCache().add(Collezione.class, collezione);
                        }
                    }
                }
            }
            if (collezione instanceof DataItemProxy) {
                ((DataItemProxy) collezione).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store collezione", ex);
        }
    }
}
