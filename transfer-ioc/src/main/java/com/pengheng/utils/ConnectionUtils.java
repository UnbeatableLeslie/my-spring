package com.pengheng.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author pengheng
 */
public class ConnectionUtils {

//    private ConnectionUtils() {
//    }
//
//    public static ConnectionUtils connectionUtils = new ConnectionUtils();
//
//    public static ConnectionUtils getInstance() {
//        return connectionUtils;
//    }


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
            conn = DruidUtils.getInstance().getConnection();
            local.set(conn);
        }
        return conn;
    }
}
