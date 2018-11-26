package com.wt.test.rocketmq.service.account.impl;

import com.wt.test.rocketmq.dao.account.AccountDao;
import com.wt.test.rocketmq.domain.account.Account;
import com.wt.test.rocketmq.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Xljnc
 * @date 2018/11/26 23:10
 * @description
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public Integer addAccount(Account account) {
        return accountDao.insert(account);
    }

    @Override
    public int updateAccount(Account account) {
        return accountDao.update(account);
    }

}
