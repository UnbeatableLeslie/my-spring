package com.pengheng.factory;

import com.pengheng.utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    private TransactionManager transactionManager;

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

//    private ProxyFactory() {
//    }
//
//    private static ProxyFactory proxyFactory = new ProxyFactory();
//
//    public static ProxyFactory getInstance(){
//        return proxyFactory;
//    }

    public Object getJDKProxy(Object obj) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object invoke = null;
                try {
                    //开启事务
                    transactionManager.beginTransaction();

                    invoke = method.invoke(obj, args);
                    //提交事务
                    transactionManager.commit();
                } catch (Exception e) {
                    //回滚事务
                    transactionManager.rollback();
                    throw e;
                }

                return invoke;
            }
        });
    }
}
