package complain.module;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import complain.bean.ComplainIssueBean;
import complain.listener.OnCplIsListener;
import config.NetConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.Utils;

public class ComplainModule implements IComplainModule {

    private OkHttpClient okHttpClient;
    private Call call;

    public ComplainModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void load(String typeId, final OnCplIsListener onCplIsListener) {
        Request request = new Request.Builder().url(Utils.getUserCplIsUrl(NetConfig.useCplIs, typeId)).get().build();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    String json = Utils.cutJson(result);
                    try {
                        JSONObject objBean = new JSONObject(json);
                        if (objBean.optInt("code") == 1) {
                            JSONArray arrData = objBean.optJSONArray("data");
                            if (arrData != null) {
                                List<ComplainIssueBean> cplIsList = new ArrayList<>();
                                for (int i = 0; i < arrData.length(); i++) {
                                    JSONObject obj = arrData.optJSONObject(i);
                                    if (obj != null) {
                                        JSONArray arr = obj.optJSONArray("data");
                                        if (arr != null) {
                                            for (int j = 0; j < arr.length(); j++) {
                                                JSONObject o = arr.optJSONObject(j);
                                                if (o != null) {
                                                    ComplainIssueBean cplIs = new ComplainIssueBean();
                                                    cplIs.setId(o.optString("ct_id"));
                                                    cplIs.setName(o.optString("ct_name"));
                                                    cplIsList.add(cplIs);
                                                }
                                            }
                                        }
                                    }
                                }
                                onCplIsListener.success(cplIsList);
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
