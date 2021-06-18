package com.sense.newots.object.dao;

import com.sense.newots.object.entity.UserAccount;

import java.util.List;

/**
 @desc ...
 @date 2021-06-04 10:20:01
 @author szz
 */
public interface UserAccountMapper {
    List<UserAccount> queryUserAccount(String userName, String passWord);
}
