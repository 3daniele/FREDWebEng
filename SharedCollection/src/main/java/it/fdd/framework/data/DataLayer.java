package it.fdd.framework.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

public class DataLayer implements AutoCloseable {

    private final DataSource datasource;
    private Connection connection;
    private final Map<Class, DAO> daos;
    private final DataCache cache;

    public DataLayer(DataSource datasource) throws SQLException {
        super();
        this.datasource = datasource;
        this.connection = datasource.getConnection();
        this.daos = new HashMap<>();
        this.cache = new DataCache();
    }

    public void registerDAO(Class entityClass, DAO dao) throws DataException {
        daos.put(entityClass, dao);
        dao.init();
    }

    public DAO getDAO(Class entityClass) {
        return daos.get(entityClass);
    }

    public void init() throws DataException {
        //call registerDAO for your own DAOs
    }

    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException ex) {
            //
        }
    }

    public DataSource getDatasource() {
        return datasource;
    }

    public Connection getConnection() {
        return connection;
    }

    public DataCache getCache() {
        return cache;
    }

    //metodo dell'interfaccia AutoCloseable (permette di usare questa classe nei try-with-resources)
    //method from the Autocloseable interface (allows this class to be used in try-with-resources)
    @Override
    public void close() throws Exception {
        destroy();
    }

}
