package cn.lu.learn.shiro.security;

import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * Created by lutiehua on 2017/9/13.
 */
public class MyWebSessionManager extends DefaultWebSessionManager {

    private static final Logger log = LoggerFactory.getLogger(MyWebSessionManager.class);

    @Override
    protected void onStart(Session session, SessionContext context) {
        if(!WebUtils.isHttp(context)) {
            log.debug("SessionContext argument is not HTTP compatible or does not have an HTTP request/response pair. No session ID cookie will be set.");
        } else {
            HttpServletRequest request = WebUtils.getHttpRequest(context);
            HttpServletResponse response = WebUtils.getHttpResponse(context);
            if(this.isSessionIdCookieEnabled()) {
                Serializable sessionId = session.getId();
                log.info("sessionId={}", sessionId.toString());
            } else {
                log.debug("Session ID cookie is disabled.  No cookie has been set for new session with id {}", session.getId());
            }

            request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_IS_NEW, Boolean.TRUE);
        }
    }

    @Override
    protected void onExpiration(Session s, ExpiredSessionException ese, SessionKey key) {
        super.onExpiration(s, ese, key);
    }

    @Override
    protected void onStop(Session session, SessionKey key) {
        super.onStop(session, key);
    }

    @Override
    public void touch(SessionKey key) throws InvalidSessionException {
        Session s = super.doGetSession(key);
        s.touch();
        this.onChange(s);
    }
}
