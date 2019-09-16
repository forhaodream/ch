package ch.chtool.okhttp;

/**
 * Created by CH
 * at 2019-09-16  16:18
 * 返回成功或失败的接口
 */
public interface RequestListiner {
    void onOK(String json);//成功

    void onError(String message);//错误
}
