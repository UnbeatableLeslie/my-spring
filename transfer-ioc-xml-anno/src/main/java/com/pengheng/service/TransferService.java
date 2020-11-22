package com.pengheng.service;

/**
 * @author pengheng
 */
public interface TransferService {

    void transfer(String fromCardNo, String toCardNo, int money) throws Exception;
}
