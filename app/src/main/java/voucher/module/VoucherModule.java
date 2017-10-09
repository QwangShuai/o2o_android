package voucher.module;


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
import voucher.bean.VoucherBean;
import voucher.listener.OnLoadVoucherListener;

public class VoucherModule implements IVoucherModule {

    private OkHttpClient okHttpClient;
    private Call call;

    public VoucherModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void load(final OnLoadVoucherListener onLoadVoucherListener) {
        Request request = new Request.Builder().url(NetConfig.voucherUrl).get().build();
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
                                    List<VoucherBean> voucherBeanList = new ArrayList<VoucherBean>();
                                    for (int i = 0; i < arrData.length(); i++) {
                                        JSONObject o = arrData.optJSONObject(i);
                                        if (o != null) {
                                            VoucherBean vb = new VoucherBean();
                                            vb.setAmount(o.optString("b_amount"));
                                            vb.setInfo(o.optString("b_info"));
                                            vb.setStartTime(o.optString("b_start_time"));
                                            vb.setEndTime(o.optString("b_end_time"));
                                            voucherBeanList.add(vb);
                                        }
                                    }
                                    onLoadVoucherListener.success(voucherBeanList);
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
