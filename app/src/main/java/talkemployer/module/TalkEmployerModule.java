package talkemployer.module;

import java.io.IOException;

import listener.JsonListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/10/26.
 */

public class TalkEmployerModule implements ITalkEmployerModule {

    private OkHttpClient okHttpClient;
    private Call call, skillCall;

    public TalkEmployerModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void load(String url, final JsonListener jsonListener) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                jsonListener.failure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    jsonListener.success(response.body().string());
                }
            }
        });
    }

    @Override
    public void loadSkill(String url, final JsonListener jsonListener) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        skillCall = okHttpClient.newCall(request);
        skillCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                jsonListener.failure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    jsonListener.success(response.body().string());
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
        if (skillCall != null) {
            skillCall.cancel();
            skillCall = null;
        }
        if (okHttpClient != null) {
            okHttpClient = null;
        }
    }
}
