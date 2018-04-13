package com.guonl.sso.core.filter;

import com.guonl.sso.core.conf.Conf;
import com.guonl.sso.core.entry.ReturnT;
import com.guonl.sso.core.user.SsoUser;
import com.guonl.sso.core.util.CookieUtil;
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
 * Date 2018/4/12 下午3:16
 * Description:
 */
public class SsoTokenFilter extends HttpServlet implements Filter{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String ssoServer;

    private String logoutPath;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ssoServer = filterConfig.getInitParameter(Conf.SSO_SERVER);
        if(StringUtils.isNotBlank(ssoServer)){
            logoutPath = filterConfig.getInitParameter(Conf.SSO_LOGOUT_PATH);
        }
        logger.info("SsoTokenFilter init ...");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String servletPath = req.getServletPath();
        String link = req.getRequestURL().toString();

        String sessionId = SsoLoginHelper.cookieSessionIdGetByHeader(req);
        SsoUser user = SsoLoginHelper.loginCheck(sessionId);

        // logout filter
        if(StringUtils.isNotBlank(logoutPath) && logoutPath.equals(servletPath)){
            if(user != null){
                SsoLoginHelper.logout(sessionId);
            }
            // response
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().println(JacksonUtil.writeValueAsString(new ReturnT(ReturnT.SUCCESS_CODE, null)));
            return;
        }

        // login filter
        if (user == null) {
            // response
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().println(JacksonUtil.writeValueAsString(Conf.SSO_LOGIN_FAIL_RESULT));
            return;
        }

        // set sso user
        servletRequest.setAttribute(Conf.SSO_USER, user);


        // already login, allow
        filterChain.doFilter(servletRequest, servletResponse);
        return;


    }
}
