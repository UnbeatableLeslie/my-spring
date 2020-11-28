package com.pengheng.service.impl;

import com.pengheng.annotation.Autowired;
import com.pengheng.annotation.Service;
import com.pengheng.annotation.Transactional;
import com.pengheng.dao.AccountDao;
import com.pengheng.pojo.Account;
import com.pengheng.service.TransferService;

/**
 * @author pengheng
 */
@Service("transferService")
public class TransferServiceImpl implements TransferService {

    //1.通过实例化创建对象
//    private AccountDao accountDao = new JdbcAccountDaoImpl();
    //2.通过工厂实例化对象
//    private AccountDao accountDao = (AccountDao) BeanFactory.getBean("accountDao");
    //3.通过set方法实例化对象
//    private AccountDao accountDao;
//
//    public void setAccountDao(AccountDao accountDao) {
//        this.accountDao = accountDao;
//    }
    //4.通过注解注入对象
    @Autowired("accountDao")
    private AccountDao accountDao;

    @Override
    @Transactional
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
        Account from = accountDao.queryAccountByCardNo(fromCardNo);
        Account to = accountDao.queryAccountByCardNo(toCardNo);
        from.setMoney(from.getMoney() - money);
        to.setMoney(to.getMoney() + money);
        accountDao.updateAccountByCardNo(to);
        int i = 1 / 0;
        accountDao.updateAccountByCardNo(from);
    }
}
