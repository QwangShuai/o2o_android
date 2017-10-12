package collectworker.module;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import collectworker.bean.CollectWorkerBean;
import collectworker.listener.OnLoadCollectWorkerListener;
import config.NetConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CollectWorkerModule implements ICollectWorkerModule {

    private OkHttpClient okHttpClient;
    private Call call;

    public CollectWorkerModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void load(String id, final OnLoadCollectWorkerListener onLoadCollectWorkerListener) {
        String url = NetConfig.collectWorkerUrl + id;
        Request request = new Request.Builder().url(url).get().build();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    try {
                        JSONObject objBean = new JSONObject(json);
                        if (objBean.optInt("code") == 1) {
                            JSONObject objData = objBean.optJSONObject("data");
                            if (objData != null) {
                                JSONArray arrData = objData.optJSONArray("data");
                                if (arrData != null) {
                                    List<CollectWorkerBean> collectWorkerBeanList = new ArrayList<CollectWorkerBean>();
                                    for (int i = 0; i < arrData.length(); i++) {
                                        JSONObject o = arrData.optJSONObject(i);
                                        if (o != null) {
                                            CollectWorkerBean collectWorkerBean = new CollectWorkerBean();
                                            collectWorkerBean.setU_id(o.optString("u_id"));
                                            collectWorkerBean.setU_sex(o.optString("u_sex"));
                                            collectWorkerBean.setU_online(o.optString("u_online"));
                                            collectWorkerBean.setU_start(o.optString("u_start"));
                                            collectWorkerBean.setU_worked_num(o.optString("u_worked_num"));
                                            collectWorkerBean.setF_id(o.optString("f_id"));
                                            collectWorkerBeanList.add(collectWorkerBean);
                                        }
                                    }
                                    onLoadCollectWorkerListener.onLoadSuccess(collectWorkerBeanList);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
