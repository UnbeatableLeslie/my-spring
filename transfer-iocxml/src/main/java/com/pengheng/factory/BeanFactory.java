package com.pengheng.factory;

import com.pengheng.utils.ConnectionUtils;

/**
 * 工厂类，生产对象（使用反射技术类）
 */
public class BeanFactory {

    public static ConnectionUtils getConnectionUtils(){
        return new ConnectionUtils();
    }

    public ConnectionUtils getConnection(){
        return new ConnectionUtils();
    }
}
