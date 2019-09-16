package ch.chtool.retrofit2;

import android.app.Activity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CH
 * at 2019-09-16  17:02
 */
public class Retrofit2Test extends Activity {
    RequestBean mRequestBean = new RequestBean();

    private void getData() {
        Call<BaseResp<RequestBean>> call = HttpUtils.getInstance().getApiService(TestService.class).getToken(mRequestBean);
        call.enqueue(new Callback<BaseResp<RequestBean>>() {
            @Override
            public void onResponse(Call<BaseResp<RequestBean>> call, Response<BaseResp<RequestBean>> response) {

            }

            @Override
            public void onFailure(Call<BaseResp<RequestBean>> call, Throwable t) {

            }
        });

    }


}
