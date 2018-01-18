package com.example.demo.Exception;

import com.example.demo.basemodel.BaseErrorResponse;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public BaseErrorResponse<String> defaultHandler(HttpRequest request,Exception e){

        BaseErrorResponse<String> resp=new BaseErrorResponse<String>();
        resp.setCode(-100);
        resp.setErrMsg(e.getMessage());
        resp.setUrl(request.getURI().getPath());

        return resp;
    }
    @ExceptionHandler(value=RuntimeException.class)
    public BaseErrorResponse<String> runTimeExceptionHandler(HttpRequest request,RuntimeException e){

        BaseErrorResponse<String> resp=new BaseErrorResponse<String>();
        resp.setCode(500);
        resp.setErrMsg("服务器内部错误。");
        resp.setUrl(request.getURI().getPath());
        resp.setData(e.getMessage());

        return resp;
    }
}
