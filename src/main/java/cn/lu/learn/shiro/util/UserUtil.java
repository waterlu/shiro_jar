package cn.lu.learn.shiro.util;

import cn.lu.learn.shiro.vo.UserVO;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lutiehua on 2017/9/13.
 */
@Component
public class UserUtil {

    private static String prefix = "shiro-session:";

    @Resource(name = "redisStringTemplate")
    private RedisTemplate<String, String> redisStringTemplate;

    public UserVO getUser() {
        Subject subject = SecurityUtils.getSubject();
        if (null == subject) {
            return null;
        }

        Session session = subject.getSession(false);
        if (null == session) {
            return null;
        }

        HashOperations<String, String, String> hashOperations = redisStringTemplate.opsForHash();
        String jsonString = hashOperations.get(prefix, session.getId().toString());
        if (null == jsonString) {
            return null;
        }

        UserVO user = JSON.parseObject(jsonString, UserVO.class);
        return user;
    }

}
