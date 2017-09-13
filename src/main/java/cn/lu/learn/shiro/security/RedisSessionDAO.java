package cn.lu.learn.shiro.security;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lutiehua on 2017/9/13.
 */
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // session 在redis过期时间是30分钟30*60
    private static int expireTime = 30*60*1000;

    private static String prefix = "shiro-session:";

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    @Resource(name = "redisStringTemplate")
    private RedisTemplate<String, String> redisStringTemplate;

    // 创建session
    @Override
    protected Serializable doCreate(Session session) {
        return super.doCreate(session);
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.debug("获取session:{}", sessionId);
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = super.doReadSession(sessionId);
        if (session == null) {
            ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
            session = (Session) valueOperations.get(prefix + sessionId.toString());
        }
        return session;
    }

    // 更新session的最后一次访问时间
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
//        HashOperations<String, String, String> hashOperations = redisStringTemplate.opsForHash();
//        String jsonString = hashOperations.get(prefix, session.getId().toString());
//        UserVO user = JSON.parseObject(jsonString, UserVO.class);
//        SimplePrincipalCollection pc = new SimplePrincipalCollection();
//        pc.add(user, "RedisSessionDAO");

        SimpleSession simpleSession = (SimpleSession)session;
        simpleSession.setLastAccessTime(new Date());
//        simpleSession.setStopTimestamp(null);
//        simpleSession.setExpired(false);
//        session.setTimeout(30 * 60 * 1000);
    }

    // 删除session
    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
    }
}