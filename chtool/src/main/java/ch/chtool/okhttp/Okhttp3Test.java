package ch.chtool.okhttp;

import android.app.Activity;
import android.widget.ProgressBar;

import java.util.HashMap;

/**
 * Created by CH
 * at 2019-09-16  16:20
 */
public class Okhttp3Test extends Activity {
    ProgressBar bar;

    private void get() {
        HttpUtils.getInstance().doget("", new RequestListiner() {
            @Override
            public void onOK(String json) {

            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void post() {
        HashMap<String, String> map = new HashMap<>();
        map.put("", "");
        HttpUtils.getInstance().dopost("", map, new RequestListiner() {
            @Override
            public void onOK(String json) {

            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void download() {
        HttpUtils.getInstance().download("", "", new Myprogress() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onProgress(int progress) {
                bar.setProgress(progress);
            }
        });
    }

    private void upload() {
        HttpUtils.getInstance().upload("", "", "", "", new RequestListiner() {
            @Override
            public void onOK(String json) {

            }

            @Override
            public void onError(String message) {

            }
        });
    }

}
