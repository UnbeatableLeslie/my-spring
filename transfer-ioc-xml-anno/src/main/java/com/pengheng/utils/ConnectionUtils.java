package com.pengheng.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author pengheng
 */
@Component("connectionUtils")
public class ConnectionUtils {

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //通过ThreadLocal做到线程数据隔离，每个线程保有一份数据库连接信息
    static ThreadLocal<Connection> local = new ThreadLocal<>();

    /**
     * 从当前线程获取数据连接
     *
     * @return
     * @throws SQLException
     */
    public Connection getCurrentThreadConn() throws SQLException {
        Connection conn = local.get();
        if (conn == null) {
            conn = dataSource.getConnection();
            local.set(conn);
        }
        return conn;
    }
}
