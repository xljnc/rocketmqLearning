package com.wt.test.rocketmq.service.account;

import com.wt.test.rocketmq.domain.account.Account;

/**
 * @author Xljnc
 * @date 2018/11/26 23:04
 * @description
 */
public interface AccountService {
    Integer addAccount(Account account);
    int updateAccount(Account account);
}
