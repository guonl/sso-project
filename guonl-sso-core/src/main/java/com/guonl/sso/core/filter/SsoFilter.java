package com.guonl.sso.core.filter;

import com.guonl.sso.core.conf.Conf;
import com.guonl.sso.core.user.SsoUser;
import com.guonl.sso.core.util.JacksonUtil;
import com.guonl.sso.core.util.SsoLoginHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by guonl
 * Date 2018/4/12 下午3:15
 * Description: web sso filter
 */
public class SsoFilter extends HttpServlet implements Filter{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String ssoServer;

    private String logoutPath;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ssoServer = filterConfig.getInitParameter(Conf.SSO_SERVER);
        if(StringUtils.isNotBlank(ssoServer)){
            logoutPath = filterConfig.getInitParameter(Conf.SSO_LOGOUT_PATH);
        }
        logger.info("SsoFilter init ...");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String servletPath = req.getServletPath();
        String link = req.getRequestURL().toString();

        // logout filter
        if(StringUtils.isNotBlank(logoutPath) && logoutPath.equals(servletPath)){
            // remove cookie
            SsoLoginHelper.removeSessionIdInCookie(req,res);

            // redirect logout
            String logoutPageUrl = ssoServer.concat(Conf.SSO_LOGOUT);
            res.sendRedirect(logoutPageUrl);

            return;
        }

        // login filter
        SsoUser user = null;

        // valid cookie user
        String cookieSessionId = SsoLoginHelper.getSessionIdByCookie(req);
        user = SsoLoginHelper.loginCheck(cookieSessionId);

        // valid param user, client login
        if (user == null) {

            // remove old cookie
            SsoLoginHelper.removeSessionIdInCookie(req, res);

            // set new cookie
            String paramSessionId = servletRequest.getParameter(Conf.SSO_SESSIONID);
            if (paramSessionId != null) {
                user = SsoLoginHelper.loginCheck(paramSessionId);
                if (user != null) {
                    SsoLoginHelper.setSessionIdInCookie(res, paramSessionId);
                }
            }
        }

        // valid login fail
        if (user == null) {

            String header = req.getHeader("content-type");
            boolean isJson=  header!=null && header.contains("json");
            if (isJson) {

                // json msg
                res.setContentType("application/json;charset=utf-8");
                res.getWriter().println(JacksonUtil.writeValueAsString(Conf.SSO_LOGIN_FAIL_RESULT));
                return;
            } else {

                // redirect logout
                String loginPageUrl = ssoServer.concat(Conf.SSO_LOGIN)
                        + "?" + Conf.REDIRECT_URL + "=" + link;

                res.sendRedirect(loginPageUrl);
                return;
            }

        }

        // set sso user
        servletRequest.setAttribute(Conf.SSO_USER, user);

        // already login, allow
        filterChain.doFilter(servletRequest, servletResponse);
        return;

    }



}
