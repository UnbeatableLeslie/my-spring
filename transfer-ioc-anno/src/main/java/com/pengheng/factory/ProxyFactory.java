package com.pengheng.factory;

import com.pengheng.utils.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component("proxyFactory")
public class ProxyFactory {

    @Autowired
    @Qualifier("transactionManager")
    private TransactionManager transactionManager;

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
