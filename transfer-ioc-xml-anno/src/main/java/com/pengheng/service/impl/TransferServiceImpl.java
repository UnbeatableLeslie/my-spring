package com.pengheng.service.impl;

import com.pengheng.dao.AccountDao;
import com.pengheng.pojo.Account;
import com.pengheng.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author pengheng
 */
@Service("transferService")
public class TransferServiceImpl implements TransferService {

    @Autowired
    @Qualifier("accountDao")
    private AccountDao accountDao;

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
        Account from = accountDao.queryAccountByCardNo(fromCardNo);
        Account to = accountDao.queryAccountByCardNo(toCardNo);
        from.setMoney(from.getMoney() - money);
        to.setMoney(to.getMoney() + money);
        accountDao.updateAccountByCardNo(to);
        accountDao.updateAccountByCardNo(from);
//        }
    }
}
