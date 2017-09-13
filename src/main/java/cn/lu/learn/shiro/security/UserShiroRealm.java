package cn.lu.learn.shiro.security;

import cn.lu.learn.shiro.vo.UserVO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lutiehua on 2017/9/13.
 */
public class UserShiroRealm extends AuthorizingRealm {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserVO userInfo = new UserVO();
        userInfo.setLoginName(username);
        userInfo.setName("中冀普银财务人员");
        userInfo.setMobile("13800138000");
        userInfo.setOfficeCode("ZJPY");
        userInfo.setOfficeName("中冀普银");
        userInfo.setUuid("2a8ea5c7fd034727883ca87f7ff70998");
        userInfo.setRoleName("finance");
        userInfo.setPassword("e10adc3949ba59abbe56e057f20f883e");
        userInfo.setState(1);

        if(userInfo == null){
            throw new UnknownAccountException();
        }

        if(userInfo.getState() != 1) {
            throw new LockedAccountException(); //帐号锁定
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
//                ByteSource.Util.bytes(userInfo.getLoginName()),//salt=username+salt
                userInfo.getLoginName()  //realm name
        );

        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws AuthenticationException{
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserVO userInfo  = (UserVO)principals.getPrimaryPrincipal();
        authorizationInfo.addRole("finance");
        authorizationInfo.addStringPermission("fop:trade:reward:allowance:audit");
        return authorizationInfo;
    }

}
