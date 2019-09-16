package ch.chtool.retrofit2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by CH
 * at 2019-09-16  17:06
 */
public interface TestService {
    @POST("url")
    Call<BaseResp<RequestBean>> getToken(@Body RequestBean beanReqUserLogin);
}
