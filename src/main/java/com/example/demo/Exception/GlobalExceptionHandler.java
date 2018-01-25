package com.example.demo.Exception;

import com.example.demo.basemodel.BaseErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public BaseErrorResponse<String> defaultHandler(HttpServletRequest request,Exception e){

        BaseErrorResponse<String> resp=new BaseErrorResponse<String>();
        resp.setCode(-100);
        resp.setErrMsg(e.getMessage());
        resp.setUrl(request.getRequestURI());

        return resp;
    }
    @ExceptionHandler(value=RuntimeException.class)
    public BaseErrorResponse<String> runTimeExceptionHandler(HttpServletRequest request, RuntimeException e){

        BaseErrorResponse<String> resp=new BaseErrorResponse<String>();
        resp.setCode(500);
        resp.setErrMsg("服务器内部错误。");
        resp.setUrl(request.getRequestURI());
        resp.setData(e.getMessage());

        return resp;
    }
}
