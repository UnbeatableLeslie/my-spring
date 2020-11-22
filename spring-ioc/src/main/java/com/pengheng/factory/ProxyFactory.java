package com.pengheng.factory;

import com.pengheng.utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    public static Object getJDKProxy(Object obj) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object invoke = null;
                try {
                    //开启事务
                    TransactionManager.getInstance().beginTransaction();

                    invoke = method.invoke(obj, args);
                    //提交事务
                    TransactionManager.getInstance().commit();
                } catch (Exception e) {
                    //回滚事务
                    TransactionManager.getInstance().rollback();
                    throw e;
                }

                return invoke;
            }
        });
    }
}
