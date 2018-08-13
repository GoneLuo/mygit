package com.luoy.library.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 配合前端使用layui表格模块的工具类
 * @author ying luo
 * @createDate 2018年3月29日
 * @param <T>
 */
public class TableVo<T> implements Serializable {

    private static final long serialVersionUID = 5294182519474035198L;
    private int code;
    private String msg;
    private int count;
    private List<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public TableVo(){

    }

    public TableVo(int count, List<T> data) {
        this.count = count;
        this.data = data;
    }

    public TableVo(int code, String msg, int count, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }
}
