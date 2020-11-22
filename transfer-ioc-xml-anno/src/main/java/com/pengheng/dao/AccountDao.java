package com.pengheng.dao;

import com.pengheng.pojo.Account;

/**
 * @author pengheng
 */
public interface AccountDao {

    Account queryAccountByCardNo(String cardNo) throws Exception;

    int updateAccountByCardNo(Account account) throws Exception;
}
