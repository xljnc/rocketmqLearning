package com.wt.test.rocketmq.service.account.impl;

import com.wt.test.rocketmq.dao.account.AccountDao;
import com.wt.test.rocketmq.domain.account.Account;
import com.wt.test.rocketmq.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void addAccount(Account account) {
        accountDao.insert(account);
    }

    @Override
    @Transactional
    public void updateAccount(Account account) {
        accountDao.update(account);
    }

}
