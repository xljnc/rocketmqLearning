package com.wt.test.rocketmq.dao.account;

import com.wt.test.rocketmq.domain.account.Account;

/**
 * @author Xljnc
 * @date 2018/11/26 22:18
 * @description 账号类
 */
public interface AccountDao {
    void insert(Account account);

    void update(Account account);
}
