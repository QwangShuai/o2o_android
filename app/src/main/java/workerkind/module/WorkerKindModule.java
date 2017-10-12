package workerkind.module;


import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import config.NetConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import workerkind.bean.WorkerKindBean;
import workerkind.listener.LoadWorkerKindListener;

public class WorkerKindModule implements IWorkerKindModule {

    private OkHttpClient okHttpClient;
    private Call call;

    public WorkerKindModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void load(final LoadWorkerKindListener loadWorkerKindListener) {
        Request request = new Request.Builder().url(NetConfig.workerKindUrl).get().build();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadWorkerKindListener.loadFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    if (!TextUtils.isEmpty(json)) {
                        try {
                            JSONObject objBean = new JSONObject(json);
                            if (objBean.optInt("code") == 200) {
                                JSONArray arrData = objBean.optJSONArray("data");
                                if (arrData != null) {
                                    List<WorkerKindBean> workerKindBeanList = new ArrayList<WorkerKindBean>();
                                    for (int i = 0; i < arrData.length(); i++) {
                                        JSONObject o = arrData.optJSONObject(i);
                                        if (o != null) {
                                            WorkerKindBean wkb = new WorkerKindBean();
                                            wkb.setId(o.optString("s_id"));
                                            wkb.setName(o.optString("s_name"));
                                            workerKindBeanList.add(wkb);
                                        }
                                    }
                                    loadWorkerKindListener.loadSuccess(workerKindBeanList);
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
        if (call != null) {
            call.cancel();
            call = null;
        }
        if (okHttpClient == null)
            okHttpClient = null;
    }

}
