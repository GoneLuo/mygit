package com.luoy.library.common.util;

/**
 * response返回json封装
 *
 * @param <T> 泛型
 * @author : ChenCong
 * @date : Created in 13:26 2018/1/22
 */
public class Result<T> {

    private boolean status;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(boolean status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }



	
    
    

    
}
