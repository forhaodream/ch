package ch.chtool.retrofit2;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by CH
 * at 2019-09-16  16:45
 */
public class HeaderInterceptor implements Interceptor {
    private String token = null;

    public HeaderInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Accept", "application/json");
        if (!StringUtils.isEmpty(token)) {
            builder.addHeader("access_token", token);
        }
        return chain.proceed(builder.build());
    }
}
