package ch.chtool.retrofit2;

import java.io.Serializable;

/**
 * Created by CH
 * at 2019-09-16  17:04
 */
public class BaseResp <T> implements Serializable {

    /// <summary>
    /// 响应代码
    /// </summary>
    private int code;

    /// <summary>
    /// 反馈信息
    /// </summary>
    private String message;

    /// <summary>
    /// 返回信息体
    /// </summary>
    private T data;

    public int getResCode() {
        return code;
    }

    public void setResCode(int resCode) {
        this.code = resCode;
    }

    public String getMsg() {
        return message == null ? "" : message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
