package redpacket.module;


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
import redpacket.bean.RedPacketBean;
import redpacket.listener.OnLoadRedPacketListener;

public class RedPacketModule implements IRedPacketModule {

    private OkHttpClient okHttpClient;
    private Call call;

    public RedPacketModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void load(final OnLoadRedPacketListener onLoadRedPacketListener) {
        Request request = new Request.Builder().url(NetConfig.redPacketUrl).get().build();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

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
                                    List<RedPacketBean> redPacketBeanList = new ArrayList<RedPacketBean>();
                                    for (int i = 0; i < arrData.length(); i++) {
                                        JSONObject o = arrData.optJSONObject(i);
                                        if (o != null) {
                                            RedPacketBean rp = new RedPacketBean();
                                            rp.setAmount(o.optString("b_amount"));
                                            rp.setStartTime(o.optString("b_start_time"));
                                            rp.setEndTime(o.optString("b_end_time"));
                                            redPacketBeanList.add(rp);
                                        }
                                    }
                                    onLoadRedPacketListener.success(redPacketBeanList);
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
        if (okHttpClient != null) {
            okHttpClient = null;
        }
    }
}
