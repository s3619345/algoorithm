package com.sense.newots.object.service;

import com.sense.newots.object.entity.UserAccount;

import java.util.List;

/**
 @desc ...
 @date 2021-06-04 10:20:40
 @author szz
 */
public interface UserAccountService {
    List<UserAccount> queryUserAccount(String userName, String passWord);
}
