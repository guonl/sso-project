package com.guonl.sso.server.controller;

import com.guonl.sso.core.user.SsoUser;
import com.guonl.sso.core.util.SsoLoginHelper;
import com.guonl.sso.server.core.model.UserInfo;
import com.guonl.sso.server.core.result.ReturnT;
import com.guonl.sso.server.dao.UserInfoDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * Created by guonl
 * Date 2018/4/17 下午2:50
 * Description: sso server (for app)
 */
@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private UserInfoDao userInfoDao;


    /**
     * Login
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public ReturnT<String> login(String username, String password) {

        if (StringUtils.isBlank(username)) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "Please input username.");
        }
        if (StringUtils.isBlank(password)) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "Please input password.");
        }
        UserInfo existUser = userInfoDao.findByUsername(username);
        if (existUser == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "username is invalid.");
        }
        if (!existUser.getPassword().equals(password)) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "password is invalid.");
        }

        // login success
        SsoUser user = new SsoUser();
        user.setUserid(existUser.getId());
        user.setUsername(existUser.getUsername());

        String sessionId = UUID.randomUUID().toString();

        SsoLoginHelper.login(sessionId, user);

        // result
        return new ReturnT<String>(sessionId);
    }


    /**
     * Logout
     *
     * @param sessionId
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public ReturnT<String> logout(String sessionId) {

        // logout
        SsoLoginHelper.logout(sessionId);
        return ReturnT.SUCCESS;
    }

    /**
     * logincheck
     *
     * @param sessionId
     * @return
     */
    @RequestMapping("/logincheck")
    @ResponseBody
    public ReturnT<SsoUser> logincheck(String sessionId) {

        // logout
        SsoUser user = SsoLoginHelper.loginCheck(sessionId);
        if (user == null) {
            return new ReturnT<SsoUser>(ReturnT.FAIL_CODE, "sso not login.");
        }
        return new ReturnT<SsoUser>(user);
    }

}