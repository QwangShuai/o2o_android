package login.module;


import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import config.NetConfig;
import login.bean.UserBean;
import login.listener.GetSecurityCodeListener;
import login.listener.LoginListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginModule implements ILoginModule {

    private OkHttpClient okHttpClient;
    private Call codeCall, loginCall;

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
                    getSecurityCodeListener.getSecurityCodeFailure(json);
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
                                    loginListener.loginSuccess(userBean);
                                    break;
                                default:
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
    public void cancelTask() {
        if (codeCall != null) {
            codeCall.cancel();
            codeCall = null;
        }
        if (loginCall != null) {
            loginCall.cancel();
            loginCall = null;
        }
    }
}
