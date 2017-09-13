package cn.lu.learn.shiro.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by lutiehua on 2017/9/13.
 */
public class UserVO implements Serializable {


    public UserVO() {

    }

    /**
     * 编号
     */
    private String uuid;

    /**
     * 归属机构编码
     */
    private String officeCode;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码
     */
    @JSONField(serialize=false)
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 归属机构名称
     */
    private String officeName;

    /**
     * 用户角色
     */
    private String roleName;

    /**
     * 用户状态
     */
    @JSONField(serialize=false)
    private Integer state;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}