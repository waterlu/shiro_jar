package cn.lu.learn.shiro.api;

import cn.lu.learn.shiro.util.UserUtil;
import cn.zjhf.kingold.common.constant.ResponseCode;
import cn.zjhf.kingold.common.exception.BusinessException;
import cn.zjhf.kingold.common.result.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lutiehua on 2017/9/13.
 */
@RestController
@RequestMapping("/advisor")
@CrossOrigin
public class AdvisorController {

    @Autowired
    private UserUtil userUtil;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(String traceID) throws BusinessException {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResponseCode.OK);
        responseResult.setMsg(ResponseCode.OK_TEXT);
        responseResult.setData(userUtil.getUser());
        if (StringUtils.isNotBlank(traceID)) {
            responseResult.setTraceID(traceID);
        }
        return responseResult;
    }

}
