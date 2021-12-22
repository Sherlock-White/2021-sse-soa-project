package org.example.distributionservice.result;

public class Result {
    /**
     *  响应状态码
     */
    private int code;
    /**
     *  响应状态消息
     */
    private String msg;
    /**
     *  响应结果对象
     */
    private Object object;

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


    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    Result (int code,String msg,Object data) {
        this.code = code;
        this.msg = msg;
        this.object = data;
    }
}

