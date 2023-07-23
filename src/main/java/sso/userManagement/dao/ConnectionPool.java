/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sso.userManagement.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.tomcat.jdbc.pool.DataSource;

/**
 *
 * @author ANJU
 */
public class ConnectionPool {

    private static ConnectionPool connPool = new ConnectionPool();
    static Map<Integer, DataSource> datasources = new ConcurrentHashMap<Integer, DataSource>();

    private ConnectionPool() {
    }

    public static ConnectionPool setDataSource(String ip, String userID, String passwd, String db, int id) {
        if (!datasources.containsKey(id)) {
            datasources.put(id, new DBPoolTomcat().getDs(ip, userID, passwd, db));
        }
        return connPool;
    }

    public static ConnectionPool getInstance() {
        return connPool;
    }

    public Connection getConn(int id) throws SQLException {
        if (datasources.get(id) != null) {
            return datasources.get(id).getConnection();
        } else {
            return null;
        }
    }
}
