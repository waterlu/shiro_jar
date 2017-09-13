package cn.lu.learn.shiro.constant;

/**
 * Created by lutiehua on 2017/8/29.
 */
public interface ErrorMessage {

    int UNAUTHORIZED_USER = 401;

    String UNAUTHORIZED_USER_TXT = "用户未登录";

    int UNKNOWN_USER = 33901;

    String UNKNOWN_USER_TXT = "用户不存在";

    int INCORRECT_CREDENTIAL = 33902;

    String INCORRECT_CREDENTIAL_TXT = "密码不正确";

    int LOCKED_ACCOUNT = 33903;

    String LOCKED_ACCOUNT_TXT = "用户已被锁定";

    int EXCESSIVE_ATTEMPT = 33904;

    String EXCESSIVE_ATTEMPT_TXT = "用户名或密码错误次数过多";

    int AUTHENTICATE_EXCEPTION = 33905;

    String AUTHENTICATE_EXCEPTION_TXT = "用户名或密码不正确";
}