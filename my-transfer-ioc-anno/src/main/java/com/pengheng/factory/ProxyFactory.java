package com.pengheng.factory;

import com.pengheng.annotation.Autowired;
import com.pengheng.annotation.Component;
import com.pengheng.utils.TransactionManager;

import java.lang.reflect.Proxy;

@Component("proxyFactory")
public class ProxyFactory {

    @Autowired("transactionManager")
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
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), (proxy, method, args) -> {
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
        });
    }
}
