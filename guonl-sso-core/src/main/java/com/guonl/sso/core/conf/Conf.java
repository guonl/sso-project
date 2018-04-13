package com.guonl.sso.core.conf;


import com.guonl.sso.core.entry.ReturnT;

/**
 * Created by guonl
 * Date 2018/4/12 下午2:02
 * Description: 常量类
 */
public class Conf {

    /**
     * redirect url, for client
     */
    public static final String REDIRECT_URL = "redirect_url";

    /**
     * sso sessionid, between browser and sso-server
     */
    public static final String SSO_SESSIONID = "sso_sessionid";

    public static final String SSO_USER = "sso_user";



    /**
     * sso server address
     */
    public static final String SSO_SERVER = "sso_server";

    /**
     * login url
     */
    public static final String SSO_LOGIN = "/login";
    /**
     * logout url
     */
    public static final String SSO_LOGOUT = "/logout";



    /**
     * filter logout path
     */
    public static final String SSO_LOGOUT_PATH = "logoutPath";

    public static final ReturnT<String> SSO_LOGIN_FAIL_RESULT = new ReturnT(501, "sso not login.");


}
