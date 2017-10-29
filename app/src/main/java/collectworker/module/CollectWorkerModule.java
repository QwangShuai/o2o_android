package collectworker.module;

import java.io.IOException;

import listener.JsonListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CollectWorkerModule implements ICollectWorkerModule {

    private OkHttpClient okHttpClient;
    private Call call, cancelCollectCall;

    public CollectWorkerModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void load(String url, final JsonListener jsonListener) {
        Request request = new Request
                .Builder()
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
    public void cancelCollect(String url, JsonListener jsonListener) {

    }

    @Override
    public void cancelTask() {
        if (call != null) {
            call.cancel();
            call = null;
        }
        if (cancelCollectCall != null) {
            cancelCollectCall.cancel();
            cancelCollectCall = null;
        }
        if (okHttpClient != null) {
            okHttpClient = null;
        }
    }
}
