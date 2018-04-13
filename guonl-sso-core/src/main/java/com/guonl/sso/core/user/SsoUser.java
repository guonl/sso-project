package com.guonl.sso.core.user;

import java.io.Serializable;

/**
 * Created by guonl
 * Date 2018/4/12 下午2:08
 * Description: 登录用户的实体类
 */
public class SsoUser implements Serializable {

    private static final long serialVersionUID = 42L;

    private int userid;
    private String username;

    // TODO，custome

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
