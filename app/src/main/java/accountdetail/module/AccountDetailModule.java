package accountdetail.module;

import android.text.TextUtils;

import java.io.IOException;

import accountdetail.listener.AccountDetailListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.DataUtils;
import utils.Utils;

/**
 * Created by Administrator on 2017/10/24.
 */

public class AccountDetailModule implements IAccountDetailModule {

    private OkHttpClient okHttpClient;
    private Call call;

    public AccountDetailModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void load(String url, final AccountDetailListener accountDetailListener) {
        Request request = new Request.Builder().url(url).get().build();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                accountDetailListener.failure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    if (!TextUtils.isEmpty(json)) {
                        json = Utils.cutJson(json);
                        accountDetailListener.success(DataUtils.getAccountDetailBeanList(json));
                    }
                }
            }
        });
    }

    @Override
    public void cancelTask() {
        if (call != null) {
            call.cancel();
            call = null;
        }
        if (okHttpClient != null) {
            okHttpClient = null;
        }
    }
}
