package workerInfo.module;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bean.PositionBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.Utils;
import workerInfo.bean.WorkerInfoBean;
import workerInfo.listener.LoadWorkerInfoListener;

public class WorkerInfoModule implements IWorkerInfoModule {

    private OkHttpClient okHttpClient;
    private Call call;

    public WorkerInfoModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void load(String workerKindId, PositionBean positionBean, final LoadWorkerInfoListener loadWorkerInfoListener) {
        String workerInfoUrl = Utils.getWorkerInfoUrl(workerKindId, positionBean);
        if (TextUtils.isEmpty(workerInfoUrl)) {
            loadWorkerInfoListener.loadFailure("WorkerInfoModule:" + "workerInfo == null");
        } else {
            Request request = new Request.Builder().url(workerInfoUrl).get().build();
            call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    loadWorkerInfoListener.loadFailure("WorkerInfoModule:" + "IOExcption:" + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        if (TextUtils.isEmpty(json)) {
                            loadWorkerInfoListener.loadFailure("WorkerInfoModule:" + "json == null");
                        } else {
                            try {
                                JSONObject objBean = new JSONObject(json);
                                if (objBean.optInt("code") == 1) {
                                    JSONObject objData = objBean.optJSONObject("data");
                                    if (objData == null) {
                                        loadWorkerInfoListener.loadFailure("WorkerInfoModule:" + "objData == null");
                                    } else {
                                        JSONArray arrData = objData.optJSONArray("data");
                                        if (arrData == null) {
                                            loadWorkerInfoListener.loadFailure("WorkerInfoModule:" + "arrData == null");
                                        } else {
                                            List<WorkerInfoBean> workerInfoBeanList = new ArrayList<WorkerInfoBean>();
                                            for (int i = 0; i < arrData.length(); i++) {
                                                JSONObject o = arrData.optJSONObject(i);
                                                if (o == null) {
                                                    loadWorkerInfoListener.loadFailure("WorkerInfoModule:" + "o == null");
                                                } else {
                                                    WorkerInfoBean wib = new WorkerInfoBean();
                                                    wib.setId(o.optString("u_id"));
                                                    wib.setName(o.optString("u_true_name"));
                                                    wib.setBrief(o.optString("uei_info"));
                                                    wib.setStatus(o.optString("u_task_status"));
                                                    wib.setIcon(o.optString("u_img"));
                                                    wib.setPositionX(o.optString("ucp_posit_x"));
                                                    wib.setPositionY(o.optString("ucp_posit_y"));
                                                    wib.setDistance("距我" + o.optDouble("distance") + "公里");
                                                    workerInfoBeanList.add(wib);
                                                }
                                            }
                                            loadWorkerInfoListener.loadSuccess(workerInfoBeanList);
                                        }
                                    }
                                } else {
                                    loadWorkerInfoListener.loadFailure("WorkerInfoModule:" + "code != 1");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        loadWorkerInfoListener.loadFailure("WorkerInfoModule:" + "!response.isSuccessful");
                    }
                }
            });
        }
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
