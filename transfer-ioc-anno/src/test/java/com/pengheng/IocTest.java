package com.pengheng;

import com.pengheng.dao.AccountDao;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IocTest {

    @Test
    public void testIOC(){
        //通过配置类加载Spring IOC容器信息
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        AccountDao accountDao = (AccountDao) applicationContext.getBean("accountDao");
        AccountDao accountDao1 = (AccountDao) applicationContext.getBean("accountDao");
        System.out.println(accountDao.toString());
//        SpringBean默认是singleton 所以会相等
        assert accountDao == accountDao1;
        Object connectionUtils1 = applicationContext.getBean("connectionUtils");
        System.out.println(connectionUtils1);
        applicationContext.close();
    }
}
