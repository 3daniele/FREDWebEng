package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DAO;
import it.fdd.framework.data.DataException;
import it.fdd.framework.data.DataLayer;
import it.fdd.sharedcollection.data.model.Utente;
import it.fdd.sharedcollection.data.proxy.UtenteProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO_MySQL extends DAO implements UtenteDAO {
    private PreparedStatement sUtenti, sUtenteByID;
    private PreparedStatement login;

    public UtenteDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            //precompiliamo tutte le query utilizzate nella classe
            //precompile all the queries uses in this class
            sUtenteByID = connection.prepareStatement("SELECT * FROM Utente WHERE ID=?");
            sUtenti = connection.prepareStatement("SELECT id FROM Utente");
            login = connection.prepareStatement("SELECT * FROM Utente WHERE email = ? AND password = ?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        //anche chiudere i PreparedStamenent � una buona pratica...
        //also closing PreparedStamenents is a good practice...
        try {
            sUtenteByID.close();
            sUtenti.close();
            login.close();
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    //metodi "factory" che permettono di creare
//e inizializzare opportune implementazioni
//delle interfacce del modello dati, nascondendo
//all'utente tutti i particolari
//factory methods to create and initialize
//suitable implementations of the data model interfaces,
//hiding all the implementation details
    @Override
    public UtenteProxy createUtente() {
        return new UtenteProxy(getDataLayer());
    }

    //helper
    private UtenteProxy createUtente(ResultSet rs) throws DataException {
        try {
            UtenteProxy a = createUtente();
            a.setKey(rs.getInt("id"));
            a.setNome(rs.getString("nome"));
            a.setCognome(rs.getString("cognome"));
            a.setNickname(rs.getString("nickname"));
            a.setPassword(rs.getString("password"));
            a.setEmail(rs.getString("email"));
            return a;
        } catch (SQLException ex) {
            throw new DataException("Unable to create Utente object form ResultSet", ex);
        }
    }

    @Override
    public Utente getUtente(int utente_key) throws DataException {
        Utente a = null;
        //prima vediamo se l'oggetto è già stato caricato
        //first look for this object in the cache
        if (dataLayer.getCache().has(Utente.class, utente_key)) {
            a = dataLayer.getCache().get(Utente.class, utente_key);
        } else {
            //altrimenti lo carichiamo dal database
            //otherwise load it from database
            try {
                sUtenteByID.setInt(1, utente_key);
                try (ResultSet rs = sUtenteByID.executeQuery()) {
                    if (rs.next()) {
                        //notare come utilizziamo il costrutture
                        //"helper" della classe UtenteImpl
                        //per creare rapidamente un'istanza a
                        //partire dal record corrente
                        //note how we use here the helper constructor
                        //of the UtenteImpl class to quickly
                        //create an instance from the current record

                        a = createUtente(rs);
                        //e lo mettiamo anche nella cache
                        //and put it also in the cache
                        dataLayer.getCache().add(Utente.class, a);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Utente by ID", ex);
            }
        }
        return a;
    }

    @Override
    public List<Utente> getUtenti() throws DataException {
        List<Utente> result = new ArrayList();

        try (ResultSet rs = sUtenti.executeQuery()) {
            while (rs.next()) {
                result.add(getUtente(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Utentes", ex);
        }
        return result;
    }

    @Override
    public Utente login(String email, String password) throws DataException {

        Utente utente = null;

        try {
            login.setString(1, email);
            login.setString(2, password);

            try (ResultSet rs = login.executeQuery()) {
                if (rs.next()) {
                    utente = getUtente(rs.getInt("id"));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("User not found", ex);
        }
        return utente;
    }
}
