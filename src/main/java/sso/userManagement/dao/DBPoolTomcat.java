/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sso.userManagement.dao;

/**
 *
 * @author anju
 */
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DBPoolTomcat {

    DataSource datasource = null;
    PoolProperties p = new PoolProperties();

    public DataSource getDs(String ip, String userID, String passwd, String db) {
        p.setUrl("jdbc:mysql://" + ip + ":3306/" + db);
        p.setDriverClassName("com.mysql.cj.jdbc.Driver");
        p.setUsername(userID);
        p.setPassword(passwd);
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(20);
        p.setInitialSize(2);
        p.setMaxWait(60000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(5);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer;"
                + "org.apache.tomcat.jdbc.pool.interceptor.StatementCache(max=100);"
                + "org.apache.tomcat.jdbc.pool.interceptor.ResetAbandonedTimer");
        datasource = new DataSource(p);
        return datasource;
    }
}
