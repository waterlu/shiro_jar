package cn.lu.learn.shiro.security;

import cn.lu.learn.shiro.vo.UserVO;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.subject.support.DefaultWebSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lutiehua on 2017/9/13.
 */
public class MyWebSecurityManager extends DefaultWebSecurityManager {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static String prefix = "shiro-session:";

    @Resource(name = "redisStringTemplate")
    private RedisTemplate<String, String> redisStringTemplate;

    @Override
    public Subject createSubject(SubjectContext subjectContext) {
        SubjectContext context = this.copy(subjectContext);
        context = this.ensureSecurityManager(context);
        context = this.resolveSession(context);
        context = this.resolvePrincipals(context);
        Subject subject = this.doCreateSubject(context);
        this.save(subject);
        return subject;

//        String sessionId = subjectContext.getSessionId().toString();
//        HashOperations<String, String, String> hashOperations = redisStringTemplate.opsForHash();
//        String jsonString = hashOperations.get(prefix, sessionId);
//        UserVO user = JSON.parseObject(jsonString, UserVO.class);
//        SimplePrincipalCollection pc = new SimplePrincipalCollection();
//        pc.add(user, "MyWebSecurityManager");
//        subject.runAs(pc);
//        return subject;
    }

    protected SubjectContext resolveSession(SubjectContext context) {
        if(context.resolveSession() != null) {
            log.debug("Context already contains a session.  Returning.");
            return context;
        } else {
            try {
                Session session = this.resolveContextSession(context);
                if(session != null) {
                    context.setSession(session);
                }
            } catch (InvalidSessionException var3) {
                log.debug("Resolved SubjectContext context session is invalid.  Ignoring and creating an anonymous (session-less) Subject instance.", var3);
            }

            return context;
        }
    }

    protected Session resolveContextSession(SubjectContext context) throws InvalidSessionException {
        SessionKey key = this.getSessionKey(context);
        return key != null?this.getSession(key):null;
    }

    protected SubjectContext resolvePrincipals(SubjectContext context) {
        String sessionId = null;
        DefaultWebSubjectContext webSubjectContext = (DefaultWebSubjectContext)context;
        HttpServletRequest request = (HttpServletRequest)webSubjectContext.getServletRequest();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for(Cookie cookie : cookies){
                if (cookie.getName().equalsIgnoreCase("WEBID")) {
                    sessionId = cookie.getValue();
                }
            }
        }

        if (null == sessionId) {
            return super.resolvePrincipals(context);
        }

        HashOperations<String, String, String> hashOperations = redisStringTemplate.opsForHash();
        String jsonString = hashOperations.get(prefix, sessionId);
        if (null == jsonString) {
            return super.resolvePrincipals(context);
        }

        UserVO user = JSON.parseObject(jsonString, UserVO.class);
        if (null == user) {
            return super.resolvePrincipals(context);
        }

        PrincipalCollection principals = new SimplePrincipalCollection();
        ((SimplePrincipalCollection)principals).add(user, "MyWebSecurityManager");

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
        authenticationInfo.setPrincipals(principals);
        authenticationInfo.setCredentials(user.getPassword());

        context.setAuthenticated(true);
        context.setAuthenticationInfo(authenticationInfo);
        context.setPrincipals(principals);

        return context;
    }
}
