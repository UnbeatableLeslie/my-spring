package com.pengheng.service.impl;

import com.pengheng.dao.AccountDao;
import com.pengheng.dao.impl.JdbcAccountDaoImpl;
import com.pengheng.pojo.Account;
import com.pengheng.service.TransferService;

/**
 * @author 应癫
 */
public class TransferServiceImpl implements TransferService {

    private AccountDao accountDao = new JdbcAccountDaoImpl();

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
