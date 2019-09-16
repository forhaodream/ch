package ch.chtool.retrofit2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CH
 * at 2019-09-16  16:36
 */
public class HttpUtils {
    private static HttpUtils retrofitUtils;
    private static Retrofit retrofit;
    private static Boolean debug = false;
    private static String hostName = "";
    private static String token = null;
    private static TokenExpiredListener tokenExpiredListener = null;

    private HttpUtils() {

    }

    public TokenExpiredListener getTokenExpiredListener() {
        return tokenExpiredListener;
    }

    public void setTokenExpiredListener(TokenExpiredListener tokenExpiredListener) {
        HttpUtils.tokenExpiredListener = tokenExpiredListener;
    }

    /**
     * 建议在MyApplication中 初始化
     *
     * @param _debug
     */
    public void init(String _hostName, Boolean _debug) {
        retrofit = null;
        debug = _debug;
        hostName = _hostName;
    }

    /**
     * 设置 token 访问时可以携带此token
     *
     * @param username
     * @param password
     * @param key
     */
    public void setAuthToken(String username, String password, String key) {
        reset();
        key = AESUtils.initKey(key);
        String _token = AESUtils.encrypt(username + "@,@" + password, key);
        token = _token;
    }

    /**
     * 设置 token 访问时可以携带此token
     *
     * @param _token token
     */
    public void setAuthToken(String _token) {
        reset();
        token = _token;
    }

    /**
     * 重新设置 token 和 核心工具
     */
    public void reset() {
        retrofit = null;
        token = null;
    }

    public static HttpUtils getInstance() {

        if (retrofitUtils == null) {
            synchronized (HttpUtils.class) {
                if (retrofitUtils == null) {
                    retrofitUtils = new HttpUtils();
                }
            }
        }
        return retrofitUtils;
    }

    public String getToken() {
        return token;
    }


    public static synchronized Retrofit getRetrofit() {
        if (retrofit == null) {

            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            if (debug) {
                //显示日志
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }

            HeaderInterceptor headerInterceptor = new HeaderInterceptor(token);
            TokenInterceptor tokenInterceptor = new TokenInterceptor() {
                @Override
                public String getNewToken() throws IOException {
                    if (tokenExpiredListener != null) {
                        return tokenExpiredListener.getNewToken();
                    }
                    return null;
                }
            };
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(logInterceptor)
                    .addInterceptor(headerInterceptor)
                    .addInterceptor(tokenInterceptor)
                    .connectTimeout(5000, TimeUnit.SECONDS).build();
            Gson gson = new GsonBuilder()
                    //解决map Double 问题
                    .registerTypeAdapter(Map.class,
                            new JsonDeserializer<Map<String, Object>>() {
                                @Override
                                public Map<String, Object> deserialize(JsonElement json, Type typeOfT,
                                                                       JsonDeserializationContext context) throws JsonParseException {
                                    Map treeMap = new HashMap<>();
                                    JsonObject jsonObject = json.getAsJsonObject();
                                    Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                                    for (Map.Entry<String, JsonElement> entry : entrySet) {
                                        if (entry.getValue().isJsonArray()) {
                                            treeMap.put(entry.getKey(), entry.getValue());
                                        } else {
                                            treeMap.put(entry.getKey(), entry.getValue().getAsString());
                                        }
                                    }
                                    return treeMap;
                                }
                            })
                    //解决 日期格式问题
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            retrofit = new Retrofit.Builder().baseUrl(hostName).client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

//            retrofit = new Retrofit.Builder().baseUrl(hostName).client(httpClient).addConverterFactory(JacksonConverterFactory.create())
//                    .build();

        }
        return retrofit;
    }

    public <T> T getApiService(Class<T> clazz) {
        Retrofit retrofit = getRetrofit();
        return retrofit.create(clazz);
    }


    public interface TokenExpiredListener {

        String getNewToken() throws IOException;
    }
}
