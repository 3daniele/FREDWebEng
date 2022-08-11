package it.fdd.sharedcollection.data.dao;

import it.fdd.framework.data.*;
import it.fdd.sharedcollection.data.model.ListaDischi;
import it.fdd.sharedcollection.data.proxy.ListaDischiProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListaDischiDAO_MySQL extends DAO implements ListaDischiDAO {
    private PreparedStatement sListaDischiByID;
    private PreparedStatement sListeDischi;
    private PreparedStatement iListaDischi, uListaDischi, dListaDischi;

    public ListaDischiDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            // precompilazione di tutte le query utilizzate nella classe
            sListaDischiByID = connection.prepareStatement("SELECT * FROM listaDischi WHERE id = ?");
            sListeDischi = connection.prepareStatement("SELECT id AS listaDischiID FROM listaDischi");
            iListaDischi = connection.prepareStatement("INSERT INTO listaDischi (collezione, disco, numeroCopie,stato,formato,barcode,imgCopertina,imgFronte,imgRetro,imgLibretto) VALUES(?, ?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uListaDischi = connection.prepareStatement("UPDATE listaDischi SET collezione=?,disco = ?, numeroCopie = ?, stato=?,formato=?,barcode=?,imgCopertina=?,imgFronte=?,imgRetro=?,imgLibretto=? WHERE id = ?");
            dListaDischi = connection.prepareStatement("DELETE FROM listaDischi WHERE id = ?");
        } catch (SQLException ex) {
            throw new DataException("Errore nell'inizializzazione del DataLayer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {
            sListaDischiByID.close();
            sListeDischi.close();
            iListaDischi.close();
            uListaDischi.close();
            dListaDischi.close();
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    @Override
    public ListaDischiProxy createListaDischi() {
        return new ListaDischiProxy(getDataLayer());
    }

    private ListaDischiProxy createListaDischi(ResultSet rs) throws DataException {
        ListaDischiProxy listaDischi = createListaDischi();
        try {
            listaDischi.setKey(rs.getInt("id"));
            //listaDischi.setCollezione(rs.getInt("collezione"));
            //listaDischi.setDisco(rs.getInt("disco"));
            listaDischi.setNumeroCopie(rs.getInt("numeroCopie"));
            listaDischi.setStato(rs.getString("stato"));
            listaDischi.setFormato(rs.getString("formato"));
            listaDischi.setBarcode(rs.getString("barCode"));
            listaDischi.setImgCopertina(rs.getString("imgCopertina"));
            listaDischi.setImgFronte(rs.getString("imgFronte"));
            listaDischi.setImgRetro(rs.getString("imgRetro"));
            listaDischi.setImgLibretto(rs.getString("imglibretto"));
        } catch (SQLException ex) {
            throw new DataException("Impossibile creare l'oggetto ListaDischi dal ResultSet", ex);
        }
        return listaDischi;
    }

    @Override
    public ListaDischi getListaDischi(int listaDischi_key) throws DataException {

        ListaDischi listaDischi = null;

        // controllo se l'oggetto è presente nella cache
        if (dataLayer.getCache().has(ListaDischi.class, listaDischi_key)) {
            listaDischi = dataLayer.getCache().get(ListaDischi.class, listaDischi_key);
        } else {
            // altrimenti caricamento dal database
            try {
                sListaDischiByID.setInt(1, listaDischi_key);
                try (ResultSet rs = sListaDischiByID.executeQuery()) {
                    if (rs.next()) {
                        listaDischi = createListaDischi(rs);
                        // aggiunta nella cache
                        dataLayer.getCache().add(ListaDischi.class, listaDischi);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Impossibile caricare la lista dei brani dall'ID", ex);
            }
        }
        return listaDischi;
    }

    @Override
    public List<ListaDischi> getListeDischi() throws DataException {

        List<ListaDischi> result = new ArrayList<>();

        try (ResultSet rs = sListeDischi.executeQuery()) {
            while (rs.next()) {
                result.add((ListaDischi) getListaDischi(rs.getInt("listaDischiID")));
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare la lista dei dischi", ex);
        }
        return result;
    }

    @Override
    public void storeListaDischi(ListaDischi listaDischi) throws DataException {

        try {
            if (listaDischi.getKey() != null && listaDischi.getKey() > 0) {

                // non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                if (listaDischi instanceof DataItemProxy && !((DataItemProxy) listaDischi).isModified()) {
                    return;
                }

                // update
                uListaDischi.setInt(1, listaDischi.getCollezione().getKey());
                uListaDischi.setInt(2, listaDischi.getDisco().getKey());
                uListaDischi.setInt(3, listaDischi.getNumeroCopie());
                uListaDischi.setString(4, listaDischi.getStato());
                uListaDischi.setString(5, listaDischi.getFormato());
                uListaDischi.setString(6, listaDischi.getBarcode());
                uListaDischi.setString(7, listaDischi.getImgCopertina());
                uListaDischi.setString(8, listaDischi.getImgFronte());
                uListaDischi.setString(9, listaDischi.getImgRetro());
                uListaDischi.setString(10, listaDischi.getImgLibretto());

                if (uListaDischi.executeUpdate() == 0) {
                    throw new OptimisticLockException(listaDischi);
                }
            } else {
                // insert
                iListaDischi.setInt(1, listaDischi.getCollezione().getKey());
                iListaDischi.setInt(2, listaDischi.getDisco().getKey());
                iListaDischi.setInt(3, listaDischi.getNumeroCopie());
                iListaDischi.setString(4, listaDischi.getStato());
                iListaDischi.setString(5, listaDischi.getFormato());
                iListaDischi.setString(6, listaDischi.getBarcode());
                iListaDischi.setString(7, listaDischi.getImgCopertina());
                iListaDischi.setString(8, listaDischi.getImgFronte());
                iListaDischi.setString(9, listaDischi.getImgRetro());
                iListaDischi.setString(10, listaDischi.getImgLibretto());

                if (iListaDischi.executeUpdate() == 1) {
                    // get della chiave generata
                    try (ResultSet keys = iListaDischi.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            // update chiave
                            listaDischi.setKey(key);
                            // inserimento nella cache
                            dataLayer.getCache().add(ListaDischi.class, listaDischi);
                        }
                    }
                }
            }
            // reset attributo dirty
            if (listaDischi instanceof DataItemProxy) {
                ((DataItemProxy) listaDischi).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Impossibile salvare la listaDischi", ex);
        }
    }


}