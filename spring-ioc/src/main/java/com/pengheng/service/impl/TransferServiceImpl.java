package com.pengheng.service.impl;

import com.pengheng.dao.AccountDao;
import com.pengheng.pojo.Account;
import com.pengheng.service.TransferService;

/**
 * @author pengheng
 */
public class TransferServiceImpl implements TransferService {

    //1.通过实例化创建对象
//    private AccountDao accountDao = new JdbcAccountDaoImpl();
    //2.通过工厂实例化对象
//    private AccountDao accountDao = (AccountDao) BeanFactory.getBean("accountDao");
    //3.通过set方法实例化对象
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
//        try {
//            //开启事务
//            TransactionManager.getInstance().beginTransaction();
        Account from = accountDao.queryAccountByCardNo(fromCardNo);
        Account to = accountDao.queryAccountByCardNo(toCardNo);
        from.setMoney(from.getMoney() - money);
        to.setMoney(to.getMoney() + money);
        accountDao.updateAccountByCardNo(to);
        int i = 1 / 0;
        accountDao.updateAccountByCardNo(from);
//            //提交事务
//            TransactionManager.getInstance().commit();
//        } catch (Exception e) {
//            //回滚事务
//            TransactionManager.getInstance().rollback();
//            throw e;
//        }
    }
}
