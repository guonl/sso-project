package com.guonl.sso.core.exception;

/**
 * Created by guonl
 * Date 2018/4/12 下午3:12
 * Description:
 */
public class SsoException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public SsoException(String msg) {
        super(msg);
    }

    public SsoException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SsoException(Throwable cause) {
        super(cause);
    }

}
