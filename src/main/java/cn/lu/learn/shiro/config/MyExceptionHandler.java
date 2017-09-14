package cn.lu.learn.shiro.config;

import cn.zjhf.kingold.common.advice.GlobalExceptionHandler;
import cn.zjhf.kingold.common.exception.BusinessException;
import cn.zjhf.kingold.common.result.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 * Created by lutiehua on 2017/9/14.
 */
@ControllerAdvice
public class MyExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public MyExceptionHandler() {
    }

    @ExceptionHandler({BusinessException.class})
    @ResponseBody
    public ResponseResult handleBusinessException(BusinessException be, HttpServletResponse response) {
        ResponseResult responseResult = new ResponseResult();
        int errorCode = be.getErrorCode();
        String errorMessage = be.getMessage();
        Object data = be.getData();
        responseResult.setCode(errorCode);
        responseResult.setMsg(errorMessage);
        responseResult.setData(data);
        LOGGER.error("BusinessException: code=[{}] msg=[{}] data[{}]", new Object[]{Integer.valueOf(errorCode), errorMessage, data});
        return responseResult;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseResult handleValidException(MethodArgumentNotValidException ex, HttpServletResponse response) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuffer buffer = new StringBuffer("");
        if(bindingResult.hasErrors()) {
            Iterator var5 = bindingResult.getFieldErrors().iterator();

            while(var5.hasNext()) {
                FieldError error = (FieldError)var5.next();
                if(buffer.length() > 0) {
                    buffer.append(",");
                }

                buffer.append(error.getField());
                buffer.append(error.getDefaultMessage());
            }
        }

        String errorMessage = buffer.toString();
        LOGGER.error("ValidException: msg=[{}]", errorMessage);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(-1);
        responseResult.setMsg(errorMessage);
        return responseResult;
    }

    @ResponseBody
//    @ExceptionHandler({Exception.class})
    public ResponseResult handleUnknownException(Exception ex, HttpServletResponse response) {
        ResponseResult responseResult = new ResponseResult();
        int errorCode = 500;
        String errorMessage = ex.toString();
        String errorData = "";
        if(null != ex.getStackTrace() && ex.getStackTrace().length > 0) {
            errorData = ex.getStackTrace()[0].toString();
        }

        responseResult.setData(errorData);
        responseResult.setCode(errorCode);
        responseResult.setMsg(errorMessage);
        LOGGER.error("UnknownException: code=[{}] msg=[{}] data=[{}]", new Object[]{Integer.valueOf(errorCode), errorMessage, errorData});
        return responseResult;
    }

}
