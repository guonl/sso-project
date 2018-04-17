package com.guonl.sso.sample.controller;

import com.guonl.sso.core.conf.Conf;
import com.guonl.sso.core.entry.ReturnT;
import com.guonl.sso.core.user.SsoUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by guonl
 * Date 2018/4/17 上午10:58
 * Description:
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public ReturnT<SsoUser> index(HttpServletRequest request) {
        SsoUser user = (SsoUser) request.getAttribute(Conf.SSO_USER);
        return new ReturnT<SsoUser>(user);
    }

}