package login.module;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import config.NetConfig;
import login.bean.UserBean;
import login.listener.GetSecurityCodeListener;
import login.listener.LoginListener;
import login.listener.PostOnLineListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.Utils;

public class LoginModule implements ILoginModule {

    private OkHttpClient okHttpClient;
    private Call codeCall, loginCall, postOnLineCall;

    public LoginModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void getSecurityCode(String phoneNumber, final GetSecurityCodeListener getSecurityCodeListener) {
        String codeUrl = NetConfig.codeUrl + phoneNumber;
        Request request = new Request.Builder().url(codeUrl).get().build();
        codeCall = okHttpClient.newCall(request);
        codeCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.e("LoginModule", "json=" + json);
                    try {
                        JSONObject objBean = new JSONObject(json);
                        int code = objBean.optInt("code");
                        JSONObject objData = objBean.optJSONObject("data");
                        if (objData == null) {
                        } else {
                            String msg = objData.optString("msg");
                            if (TextUtils.isEmpty(msg)) {
                            } else {
                                switch (code) {
                                    case 0:
                                        getSecurityCodeListener.getSecurityCodeFailure(msg);
                                        break;
                                    case 1:
                                        getSecurityCodeListener.getSecurityCodeSuccess(msg);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }
        });
    }

    @Override
    public void login(String phoneNumber, String securityCode, final LoginListener loginListener) {
        String loginUrl = NetConfig.loginUrl + "?phone_number=" + phoneNumber + "&verify_code=" + securityCode;
        Request request = new Request.Builder().url(loginUrl).get().build();
        loginCall = okHttpClient.newCall(request);
        loginCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.e("LoginModulelogin", "json=" + json);
                    json = Utils.cutJson(json);
                    try {
                        JSONObject objBean = new JSONObject(json);
                        int code = objBean.optInt("code");
                        JSONObject objData = objBean.optJSONObject("data");
                        if (objData == null) {
                        } else {
                            switch (code) {
                                case 0:
                                    String msg = objData.optString("msg");
                                    if (!TextUtils.isEmpty(msg))
                                        loginListener.loginFailure(msg);
                                    break;
                                case 1:
                                    UserBean userBean = new UserBean();
                                    userBean.setId(objData.optString("u_id"));
                                    userBean.setName(objData.optString("u_name"));
                                    userBean.setSex(objData.optString("u_sex"));
                                    userBean.setOnline(objData.optString("u_online"));
                                    userBean.setIcon(objData.optString("u_img"));
                                    userBean.setToken(objData.optString("token"));
                                    userBean.setPass(objData.optString("u_pass"));
                                    userBean.setIdcard(objData.optString("u_idcard"));
                                    loginListener.loginSuccess(userBean);
                                    break;
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
    public void postOnLine(String id, final PostOnLineListener postOnLineListener) {
        RequestBody body = new FormBody.Builder().add("u_id", id).add("u_online", "1").build();
        Request request = new Request.Builder().url(NetConfig.userInfoEditUrl).post(body).build();
        postOnLineCall = okHttpClient.newCall(request);
        postOnLineCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                postOnLineListener.failure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    if (!TextUtils.isEmpty(result)) {
                        try {
                            JSONObject objBean = new JSONObject(result);
                            switch (objBean.optInt("code")) {
                                case 0:
                                    postOnLineListener.failure();
                                    break;
                                case 1:
                                    postOnLineListener.success();
                                    break;
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
        if (codeCall != null) {
            codeCall.cancel();
            codeCall = null;
        }
        if (loginCall != null) {
            loginCall.cancel();
            loginCall = null;
        }
        if (postOnLineCall != null) {
            postOnLineCall.cancel();
            postOnLineCall = null;
        }
        if (okHttpClient != null) {
            okHttpClient = null;
        }
    }
}
