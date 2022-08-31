package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.*;
import it.fdd.sharedcollection.data.model.Artista;
import it.fdd.sharedcollection.data.model.Utente;
import it.fdd.sharedcollection.data.proxy.UtenteProxy;
import it.fdd.sharedcollection.utility.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO_MySQL extends DAO implements UtenteDAO {
    private PreparedStatement sUtenti, sUtenteByID;
    private PreparedStatement iUtente;

    private PreparedStatement uUtente;
    private PreparedStatement login;
    private PreparedStatement getUtenteByUsername;
    private PreparedStatement loginUtente;

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
            iUtente = connection.prepareStatement("INSERT INTO utente (nickname,email,password,nome,cognome) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uUtente = connection.prepareStatement("UPDATE Utente SET nome = ?, cognome = ?, password = ? WHERE id = ?");
            getUtenteByUsername = connection.prepareStatement("SELECT id FROM Utente WHERE nickname = ?");
            loginUtente = connection.prepareStatement("SELECT id, password FROM Utente WHERE email = ?");
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
            iUtente.close();
            uUtente.close();
            loginUtente.close();
            getUtenteByUsername.close();


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




       System.out.println("PROVA");

       System.out.println(password);
        System.out.println(email);
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
    public String getPassword(String email) throws DataException {
        String pass = null;

        try {
            loginUtente.setString(1, email);
            try (ResultSet rs = loginUtente.executeQuery()) {
                if (rs.next()) {
                    pass = rs.getString("password");
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load password utente by email", ex);
        }
        return pass;
    }


    public Utente storeUtente(Utente utente) throws DataException {
        try {
            if (utente.getKey() != null && utente.getKey() > 0) {

                // non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                if (utente instanceof DataItemProxy && !((DataItemProxy) utente).isModified()) {
                    return null;
                }

                // update
                uUtente.setString(1, utente.getNome());
                uUtente.setString(2, utente.getCognome());
                uUtente.setString(3, utente.getPassword());
                //uUtente.setInt(4, utente.getKey());

                if (uUtente.executeUpdate() == 0) {
                    throw new OptimisticLockException(utente);
                }

            } else {
                // insert
                iUtente.setString(1, utente.getNickname());
                iUtente.setString(2, utente.getEmail());
                iUtente.setString(3, utente.getPassword());
                iUtente.setString(4, utente.getNome());
                iUtente.setString(5, utente.getCognome());


                if (iUtente.executeUpdate() == 1) {
                    // get della chiave generata
                    try (ResultSet keys = iUtente.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            // update chiave
                            utente.setKey(key);
                            // inserimento nella cache
                            dataLayer.getCache().add(Utente.class, utente);
                        }
                    }
                }
            }
            // reset attributo dirty
            if (utente instanceof DataItemProxy) {
                ((DataItemProxy) utente).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Impossibile salvare l'utente", ex);
        }
        return utente;
    }

    @Override
    public Utente getUtente(String email) throws DataException {
        Utente utente = null;

        try {
            loginUtente.setString(1, email);
            try (ResultSet rs = loginUtente.executeQuery()) {
                if (rs.next()) {
                    utente = getUtente(rs.getInt("id"));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load utente by email", ex);
        }
        return utente;
    }

    @Override
    public Utente getUtenteByUsername(String username) throws DataException {
        Utente utente = null;

        try {
            getUtenteByUsername.setString(1, username);
            try (ResultSet rs = getUtenteByUsername.executeQuery()) {
                if (rs.next()) {
                    utente = getUtente(rs.getInt("id"));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load utente by usernme", ex);
        }
        return utente;
    }

}
