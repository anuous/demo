package com.example.demo.basemodel;

public class BaseErrorResponse<T> {

    public static final Integer OK = 200;
    public static final Integer ERROR = -1;
    private int code;
    private String errMsg;
    private T data;
    private String url;


    public BaseErrorResponse() {
        this.code = ERROR;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
