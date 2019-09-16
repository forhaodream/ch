package ch.chtool.okhttp;

/**
 * Created by CH
 * at 2019-09-16  16:18
 * 下载或上传文件时实现带有进度条的接口
 */
public interface Myprogress {
    void onError(String message);//失败

    void onFinish();//成功

    void onProgress(int progress);//进度
}

