package collectworker.module;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import collectworker.bean.CollectWorkerBean;
import collectworker.listener.CancelCollectListener;
import collectworker.listener.OnLoadCollectWorkerListener;
import config.NetConfig;
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
                                            collectWorkerBean.setuId(o.optString("u_id"));
                                            collectWorkerBean.setuImg(o.optString("u_img"));
                                            collectWorkerBean.setuName(o.optString("u_name"));
                                            collectWorkerBean.setuTaskStatus(o.optString("u_task_status"));
                                            collectWorkerBean.setuSex(o.optString("u_sex"));
                                            collectWorkerBean.setUeiInfo(o.optString("uei_info"));
                                            collectWorkerBean.setUcpPositX(o.optString("ucp_posit_x"));
                                            collectWorkerBean.setUcpPositY(o.optString("ucp_posit_y"));
                                            collectWorkerBean.setfId(o.optString("f_id"));
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
    public void cancelCollect(String url, final CancelCollectListener cancelCollectListener) {
        Request cancelCollectRequest = new Request.Builder().url(url).get().build();
        cancelCollectCall = okHttpClient.newCall(cancelCollectRequest);
        cancelCollectCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    try {
                        JSONObject beanObj = new JSONObject(json);
                        int code = beanObj.optInt("code");
                        JSONObject dataObj = beanObj.optJSONObject("data");
                        String msg = dataObj.optString("msg");
                        switch (code) {
                            case 0:
                                cancelCollectListener.failure(msg);
                                break;
                            case 1:
                                cancelCollectListener.success(msg);
                                break;
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
        if (cancelCollectCall != null) {
            cancelCollectCall.cancel();
            cancelCollectCall = null;
        }
        if (okHttpClient != null) {
            okHttpClient = null;
        }
    }
}
