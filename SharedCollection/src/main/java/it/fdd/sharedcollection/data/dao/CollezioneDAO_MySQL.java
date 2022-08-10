package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.DAO;
import it.fdd.framework.data.DataException;
import it.fdd.framework.data.DataLayer;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.Utente;
import it.fdd.sharedcollection.data.proxy.CollezioneProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CollezioneDAO_MySQL extends DAO implements CollezioneDAO {
    private PreparedStatement sCollezioneByID;
    private PreparedStatement sCollezioni, sCollezioniByUtente;
    private PreparedStatement iCollezione, uCollezione, dCollezione;

    public CollezioneDAO_MySQL(DataLayer d){
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();

            sCollezioneByID = connection.prepareStatement("SELECT * FROM collezione WHERE id=?");
            sCollezioni = connection.prepareStatement("SELECT * FROM collezione");
            sCollezioniByUtente = connection.prepareStatement("SELECT * FROM collezione WHERE utente=?");

            iCollezione = connection.prepareStatement("INSERT INTO collezione (nome,condivisione,dataCreazione,utente) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uCollezione = connection.prepareStatement("UPDATE collezione SET nome=?,condivisione=?,dataCreazione=?,utente=? WHERE ID=?");
            dCollezione = connection.prepareStatement("DELETE FROM collezione WHERE ID=?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing SharedCollection data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        //anche chiudere i PreparedStamenent � una buona pratica...
        //also closing PreparedStamenents is a good practice...
        try {

            sCollezioneByID.close();
            sCollezioni.close();
            sCollezioniByUtente.close();

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

    private CollezioneProxy createCollezione(ResultSet rs) throws DataException{
        CollezioneProxy a = createCollezione();
        try{
            a.setKey(rs.getInt("id"));
            a.setNome(rs.getString("nome"));
            //bisogna prendere direttamente l'utente dall'id
            //a.setUtente(rs.getInt("utente"));
            a.setCondivisione(rs.getString("condivisone"));
            a.setDataCreazione(rs.getDate("dataCreazione"));
        }catch (SQLException ex){
            throw new DataException("Unable to create Collezione object form ResultSet", ex);
        }
        return a;
    }
}
