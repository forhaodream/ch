package ch.chtool.okhttp;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created by CH
 * at 2019-09-16  16:17
 */
public class HttpUtils {
    private OkHttpClient okHttpClient;


    private HttpUtils() {//构造方法只会走一次
        //拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("xxx", "xxxx " + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //token拦截器
        final Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("token", "werfdwsadfdsaxdfbg").build();
                return chain.proceed(request);
            }
        };
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    private static HttpUtils httpUtils = null;

    public static HttpUtils getInstance() {
        //双重锁
        if (httpUtils == null) {
            synchronized (Object.class) {//class对象在内存中只有一个
                if (httpUtils == null) {
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }

    //get获取数据
    public void doget(String url, final RequestListiner listiner) {
        Request request = new Request.Builder().url(url).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listiner.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listiner.onOK(response.body().string());
            }
        });
    }

    //post获取数据
    public void dopost(String url, HashMap<String, String> map, final RequestListiner listiner) {
        FormBody.Builder builder = new FormBody.Builder();

        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.add(key, value);
        }

        FormBody formBody = builder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listiner.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listiner.onOK(response.body().string());
            }
        });

    }

    //下载
    public void download(String url, final String path, final Myprogress myprogress) {
        Request request = new Request.Builder().url(url).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                myprogress.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                long max = body.contentLength();

                InputStream inputStream = body.byteStream();
                FileOutputStream outputStream = new FileOutputStream(path);
                byte[] b = new byte[1024];
                int len = 0;
                int count = 0;
                while ((len = inputStream.read(b)) != -1) {
                    count += len;
                    outputStream.write(b, 0, len);
                    myprogress.onProgress((int) ((count * 100) / max));
                }
                if (count >= max) {
                    myprogress.onFinish();
                }
            }
        });
    }

    //上传
    public void upload(String url, String path, String filename, String type, final RequestListiner listiner) {
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", filename, RequestBody.create(MediaType.parse(type), new File(path)))
                .build();

        Request request = new Request.Builder().url(url).post(body).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listiner.onError(e.getMessage()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listiner.onOK(response.body().string());
            }
        });
    }
}
