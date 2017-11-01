package editinfo.module;


import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import config.NetConfig;
import config.VarConfig;
import editinfo.listener.AddSkillListener;
import editinfo.listener.SubmitListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import usermanage.bean.UserInfoBean;
import utils.DataUtils;
import utils.Utils;

public class EditInfoModule implements IEditInfoModule {

    private OkHttpClient okHttpClient;
    private Call call, submitCall;

    public EditInfoModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void load(String url, final AddSkillListener addSkillListener) {
        Request request = new Request.Builder().url(url).get().build();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                addSkillListener.failure(VarConfig.noNet);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    addSkillListener.success(DataUtils.getSkillBeanList(json));
                }
            }
        });
    }

    @Override
    public void submit(UserInfoBean userInfoBean, final SubmitListener submitListener) {
        RequestBody requestBody = new FormBody.Builder()
                .add("u_id", userInfoBean.getU_id())
                .add("u_true_name", userInfoBean.getU_true_name())
                .add("u_sex", userInfoBean.getU_sex())
                .add("u_idcard", userInfoBean.getU_idcard())
                .add("uei_province", userInfoBean.getArea_uei_province())
                .add("uei_city", userInfoBean.getArea_uei_city())
                .add("uei_area", userInfoBean.getArea_uei_area())
                .add("uei_address", userInfoBean.getArea_uei_address())
                .add("uei_info", userInfoBean.getU_info())
                .add("u_skills", userInfoBean.getU_skills())
                .build();
        Request request = new Request.Builder().url(NetConfig.userInfoEditUrl).post(requestBody).build();
        submitCall = okHttpClient.newCall(request);
        submitCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                submitListener.failure(VarConfig.noNet);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    if (!TextUtils.isEmpty(json)) {
                        try {
                            JSONObject beanObj = new JSONObject(json);
                            JSONObject dataObj = beanObj.optJSONObject("data");
                            if (dataObj != null) {
                                int code = beanObj.optInt("code");
                                String msg = dataObj.optString("msg");
                                switch (code) {
                                    case 0:
                                        submitListener.failure(msg);
                                        break;
                                    case 1:
                                        submitListener.success(msg);
                                        break;
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
        if (submitCall != null) {
            submitCall.cancel();
            submitCall = null;
        }
        if (okHttpClient != null) {
            okHttpClient = null;
        }
    }
}
