package cn.lu.learn.shiro.api;

import cn.lu.learn.shiro.constant.ErrorMessage;
import cn.zjhf.kingold.common.result.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lutiehua on 2017/9/13.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 用户未登录的统一错误返回
     *
     * @return
     */
    @RequestMapping(value="/login", method= RequestMethod.GET)
    public ResponseResult loginPrompt(){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ErrorMessage.UNAUTHORIZED_USER);
        responseResult.setMsg(ErrorMessage.UNAUTHORIZED_USER_TXT);
        return responseResult;
    }

}

