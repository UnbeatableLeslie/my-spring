package com.pengheng;

import com.pengheng.dao.AccountDao;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IocTest {

    @Test
    public void testIOC(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        AccountDao accountDao = (AccountDao) applicationContext.getBean("accountDao");
        AccountDao accountDao1 = (AccountDao) applicationContext.getBean("accountDao");
        AccountDao accountDao11 = (AccountDao) applicationContext.getBean("accountDao1");
        System.out.println(accountDao.toString());
        System.out.println(accountDao11.toString());
//        SpringBean默认是singleton 所以会相等
        assert accountDao == accountDao1;
        Object connectionUtils1 = applicationContext.getBean("connectionUtils");
        Object connectionUtils2 = applicationContext.getBean("connectionUtils2");
        Object connectionUtils3 = applicationContext.getBean("connectionUtils3");
        System.out.println(connectionUtils1);
        System.out.println(connectionUtils2);
        System.out.println(connectionUtils3);

        applicationContext.close();
    }
}
