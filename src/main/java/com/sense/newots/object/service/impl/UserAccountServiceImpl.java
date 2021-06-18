package com.sense.newots.object.service.impl;

import com.sense.newots.object.dao.UserAccountMapper;
import com.sense.newots.object.entity.UserAccount;
import com.sense.newots.object.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 @desc ...
 @date 2021-06-04 10:20:59
 @author szz
 */
@Service
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {
    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public List<UserAccount> queryUserAccount(String userName, String passWord) {
        List<UserAccount> list = userAccountMapper.queryUserAccount(userName, passWord);
        if(list!=null || list.isEmpty()){
            return null;
        }
        return list;
    }
}
