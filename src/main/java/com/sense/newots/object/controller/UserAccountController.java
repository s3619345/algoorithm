package com.sense.newots.object.controller;

import com.sense.newots.commonentity.BaseResource;
import com.sense.newots.object.entity.UserAccount;
import com.sense.newots.object.service.UserAccountService;
import com.sense.newots.object.util.ResponseUtil;
import com.sense.newots.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 @desc ...
 @date 2021-06-04 10:21:39
 @author szz
 */
@Slf4j
@RestController
@RequestMapping("/userAccount")
public class UserAccountController extends BaseResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @Autowired
    private UserAccountService userAccountDAO;

    @RequestMapping("queryUserAccount")
    @Produces({"application/json"})
    public ResponseUtil queryUserAccount(@RequestBody UserAccount user) {
        new ResponseUtil();
        ResponseUtil responseUtil;
        try {
            String userName = user.getUsername();
            String passWord = user.getPassword();

            if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(passWord)) {
                log.info("userName passWord is null:{},{}", userName, passWord);
                responseUtil = this.setResponseUtil(0, " userName passWord is null", (Object) null);
                return responseUtil;
            }
            passWord = StringUtil.LowercaseMd5Encryption(passWord);
            List data = this.userAccountDAO.queryUserAccount(userName, passWord);
            if (StringUtil.isNotEmpty(data)) {
                responseUtil = this.setResponseUtil(1, " queryUserAccount Suc", (Object) null);
            } else {
                responseUtil = this.setResponseUtil(0, " queryUserAccount passWord fail", (Object) null);
            }
        } catch (Exception var8) {
            responseUtil = this.setResponseUtil(0, var8.getMessage(), (Object) null);
            log.error("queryUserAccount fail!:{}", var8);
        }
        return responseUtil;
    }
}
