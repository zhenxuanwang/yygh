package com.wzx.yygh.common.exception;

import com.wzx.yygh.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    //统一异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    //自定义医院异常处理
    @ExceptionHandler(HospitalException.class)
    @ResponseBody
    public Result error(HospitalException e) {
        e.printStackTrace();
        return Result.fail();
    }
}
