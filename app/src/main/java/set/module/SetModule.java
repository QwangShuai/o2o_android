package set.module;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import config.NetConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import set.listener.QuitListener;

/**
 * Created by Administrator on 2017/10/24.
 */

public class SetModule implements ISetModule {

    private OkHttpClient okHttpClient;
    private Call quitCall;

    public SetModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void quit(String id, final QuitListener quitListener) {
        RequestBody requestBody = new FormBody.Builder()
                .add("u_id", id)
                .add("u_online", "0")
                .build();
        Request request = new Request.Builder()
                .url(NetConfig.userInfoEditUrl)
                .post(requestBody)
                .build();
        quitCall = okHttpClient.newCall(request);
        quitCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                quitListener.failure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    if (!TextUtils.isEmpty(json)) {
                        try {
                            JSONObject beanObj = new JSONObject(json);
                            int code = beanObj.optInt("code");
                            JSONObject dataObj = beanObj.optJSONObject("data");
                            if (dataObj != null) {
                                String msg = dataObj.optString("msg");
                                switch (code) {
                                    case 0:
                                        quitListener.failure(msg);
                                        break;
                                    case 1:
                                        quitListener.success(msg);
                                        break;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void cancelTask() {
        if (quitCall != null) {
            quitCall.cancel();
            quitCall = null;
        }
        if (okHttpClient != null) {
            okHttpClient = null;
        }
    }
}
