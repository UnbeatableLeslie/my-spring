package com.pengheng;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IocTest {

    @Test
    public void testIOC(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//        AccountDao accountDao = (AccountDao) applicationContext.getBean("accountDao");
        //测试bean对象的延迟加载
//        Object lazyBean = applicationContext.getBean("lazyResult");
//        Object companyFactoryBean = applicationContext.getBean("companyFactoryBean");

        //Result
        Object result = applicationContext.getBean("lazyResult");
        applicationContext.close();
    }
}
