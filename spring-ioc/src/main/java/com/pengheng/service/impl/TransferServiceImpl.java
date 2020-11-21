package com.pengheng.service.impl;

import com.pengheng.dao.AccountDao;
import com.pengheng.pojo.Account;
import com.pengheng.service.TransferService;

/**
 * @author 应癫
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

        Account from = accountDao.queryAccountByCardNo(fromCardNo);
        Account to = accountDao.queryAccountByCardNo(toCardNo);

        from.setMoney(from.getMoney() - money);
        to.setMoney(to.getMoney() + money);

        accountDao.updateAccountByCardNo(to);
        accountDao.updateAccountByCardNo(from);

    }
}
