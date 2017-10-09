package jobinfo.module;


import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jobinfo.bean.JobInfoBean;
import jobinfo.listener.LoadJobInfoListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JobInfoModule implements IJobInfoModule {

    private OkHttpClient okHttpClient;
    private Call call;

    public JobInfoModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void load(String url, final LoadJobInfoListener loadJobInfoListener) {
        Request request = new Request.Builder().url(url).get().build();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadJobInfoListener.loadFailure("JobInfoModule:" + "IOException=" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    if (TextUtils.isEmpty(json)) {
                        loadJobInfoListener.loadFailure("JobInfoModule:" + "json == null");
                    } else {
                        try {
                            JSONObject objBean = new JSONObject(json);
                            if (objBean.optInt("code") == 200) {
                                JSONArray arrData = objBean.optJSONArray("data");
                                if (arrData == null) {
                                    loadJobInfoListener.loadFailure("JobInfoModule:" + "arrData == null");
                                } else {
                                    List<JobInfoBean> jobInfoBeanList = new ArrayList<JobInfoBean>();
                                    for (int i = 0; i < arrData.length(); i++) {
                                        JSONObject o = arrData.optJSONObject(i);
                                        if (o == null) {
                                            loadJobInfoListener.loadFailure("JobInfoModule:" + "o == null");
                                        } else {
                                            JobInfoBean jib = new JobInfoBean();
                                            jib.setT_id(o.optString("t_id"));
                                            jib.setT_title(o.optString("t_title"));
                                            jib.setT_info(o.optString("t_info"));
                                            jib.setT_status(o.optString("t_status"));
                                            jobInfoBeanList.add(jib);
                                        }
                                    }
                                    loadJobInfoListener.loadSuccess(jobInfoBeanList);
                                }
                            } else {
                                loadJobInfoListener.loadFailure("JobInfoModule:" + "code != 200");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    loadJobInfoListener.loadFailure("JobInfoModule:" + "!response.isSuccessful");
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
        if (okHttpClient != null)
            okHttpClient = null;
    }
}
