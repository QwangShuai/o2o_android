package collectjob.module;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import collectjob.bean.CollectJobBean;
import collectjob.listener.OnLoadCollectJobListener;
import config.NetConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CollectJobModule implements ICollectJobModule {

    private OkHttpClient okHttpClient;
    private Call call;

    public CollectJobModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void load(String id, final OnLoadCollectJobListener onLoadCollectJobListener) {
        String url = NetConfig.collectJobUrl + id;
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
                                    List<CollectJobBean> collectJobBeanList = new ArrayList<CollectJobBean>();
                                    for (int i = 0; i < arrData.length(); i++) {
                                        JSONObject o = arrData.optJSONObject(i);
                                        if (o != null) {
                                            CollectJobBean collectJobBean = new CollectJobBean();
                                            collectJobBean.setTitle(o.optString("t_title"));
                                            collectJobBean.setAmount(o.optString("t_amount"));
                                            collectJobBean.setDuration(o.optString("t_duration"));
                                            collectJobBean.setAuthor(o.optString("t_author"));
                                            collectJobBean.setStatus(o.optString("t_status"));
                                            collectJobBeanList.add(collectJobBean);
                                        }
                                    }
                                    onLoadCollectJobListener.onLoadSuccess(collectJobBeanList);
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
