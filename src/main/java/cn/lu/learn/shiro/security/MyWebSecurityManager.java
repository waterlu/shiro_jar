package cn.lu.learn.shiro.security;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

/**
 * Created by lutiehua on 2017/9/13.
 */
public class MyWebSecurityManager extends DefaultWebSecurityManager {

    @Override
    public Subject createSubject(SubjectContext subjectContext) {
        return super.createSubject(subjectContext);
    }
}
