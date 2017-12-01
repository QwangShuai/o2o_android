package com.gjzg.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

import com.gjzg.config.ColorConfig;
import com.gjzg.config.NetConfig;
import com.gjzg.config.VarConfig;
import com.gjzg.bean.UserBean;
import com.gjzg.service.GetLoginCodeTimerService;
import com.gjzg.utils.UserUtils;
import com.gjzg.utils.Utils;
import com.gjzg.view.CProgressDialog;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.rl_login_return)
    RelativeLayout returnRl;
    @BindView(R.id.et_login_phone_number)
    EditText numberEt;
    @BindView(R.id.tv_login_get_move_pwd)
    TextView getTv;
    @BindView(R.id.rl_login_get_move_pwd)
    RelativeLayout getRl;
    @BindView(R.id.et_login_move_pwd)
    EditText codeEt;
    @BindView(R.id.tv_login_log)
    TextView loginTv;
    @BindView(R.id.tv_login_agreement)
    TextView dealTv;

    private Unbinder unbinder;
    private GradientDrawable getGd, loginGd;
    private CProgressDialog cpd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mUpdateReceiver, updateIntentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    private void initView() {
        loginTv.setEnabled(false);
        getGd = (GradientDrawable) getRl.getBackground();
        loginGd = (GradientDrawable) loginTv.getBackground();
        cpd = Utils.initProgressDialog(LoginActivity.this, cpd);
    }

    @OnTextChanged(value = R.id.et_login_move_pwd, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable s) {
        if (s.length() != 6) {
            loginTv.setEnabled(false);
            loginGd.setColor(ColorConfig.white_ffffff);
            loginTv.setTextColor(ColorConfig.gray_a0a0a0);
        } else {
            loginTv.setEnabled(true);
            loginGd.setColor(ColorConfig.yellow_ffc822);
            loginTv.setTextColor(ColorConfig.white_ffffff);
        }
    }

    @OnClick({R.id.rl_login_return, R.id.rl_login_get_move_pwd, R.id.tv_login_log, R.id.tv_login_agreement})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_login_return:
                finish();
                break;
            case R.id.rl_login_get_move_pwd:
                getCode(numberEt.getText().toString());
                break;
            case R.id.tv_login_log:
                login(numberEt.getText().toString(), codeEt.getText().toString());
                break;
            case R.id.tv_login_agreement:
                startActivity(new Intent(LoginActivity.this, AgreementActivity.class));
                break;
        }
    }

    private void getCode(final String number) {
        if (Utils.isPhonenumber(number)) {
            cpd.show();
            OkHttpUtils
                    .get()
                    .tag(this)
                    .url(NetConfig.codeUrl)
                    .addParams("phone_number", number)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Request request, Exception e) {
                            cpd.dismiss();
                            Utils.toast(LoginActivity.this, VarConfig.noNetTip);
                        }

                        @Override
                        public void onResponse(String response) {
                            cpd.dismiss();
                            if (!TextUtils.isEmpty(response)) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int code = jsonObject.optInt("code");
                                    String msg = null;
                                    JSONObject dataObj = jsonObject.optJSONObject("data");
                                    if (dataObj != null) {
                                        msg = dataObj.optString("msg");
                                    }
                                    if (!TextUtils.isEmpty(msg))
                                        Utils.toast(LoginActivity.this, msg);
                                    if (code == 1) {
                                        startService(new Intent(LoginActivity.this, GetLoginCodeTimerService.class));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        } else {
            Utils.toast(LoginActivity.this, "手机号格式不正确");
        }
    }

    private void login(final String number, String code) {
        if (Utils.isPhonenumber(number)) {
            if (!TextUtils.isEmpty(code)) {
                cpd.show();
                OkHttpUtils
                        .get()
                        .tag(this)
                        .url(NetConfig.loginUrl)
                        .addParams("phone_number", number)
                        .addParams("verify_code", code)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Request request, Exception e) {
                                cpd.dismiss();
                                Utils.toast(LoginActivity.this, VarConfig.noNetTip);
                            }

                            @Override
                            public void onResponse(String response) {
                                cpd.dismiss();
                                if (!TextUtils.isEmpty(response)) {
                                    Utils.log(LoginActivity.this, response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int code = jsonObject.optInt("code");
                                        String msg = null;
                                        JSONObject dataObj = jsonObject.optJSONObject("data");
                                        switch (code) {
                                            case 0:
                                                msg = dataObj.optString("msg");
                                                if (!TextUtils.isEmpty(msg))
                                                    Utils.toast(LoginActivity.this, msg);
                                                break;
                                            case 1:
                                                UserBean userBean = new UserBean();
                                                userBean.setId(dataObj.optString("u_id"));
                                                userBean.setName(dataObj.optString("u_name"));
                                                userBean.setSex(dataObj.optString("u_sex"));
                                                userBean.setOnline(dataObj.optString("u_online"));
                                                userBean.setIcon(dataObj.optString("u_img"));
                                                userBean.setToken(dataObj.optString("token"));
                                                userBean.setPass(dataObj.optString("u_pass"));
                                                userBean.setIdcard(dataObj.optString("u_idcard"));
                                                postOnline(userBean);
                                                break;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
            }
        } else {
            Utils.toast(LoginActivity.this, "手机号格式不正确");
        }
    }

    private void postOnline(final UserBean userBean) {
        OkHttpUtils
                .post()
                .tag(this)
                .url(NetConfig.userInfoEditUrl)
                .addParams("u_id", userBean.getId())
                .addParams("u_online", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optInt("code") == 1) {
                                    cpd.dismiss();
                                    userBean.setOnline("1");
                                    UserUtils.saveUserData(LoginActivity.this, userBean);
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private static IntentFilter updateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GetLoginCodeTimerService.IN_RUNNING);
        intentFilter.addAction(GetLoginCodeTimerService.END_RUNNING);
        return intentFilter;
    }

    private final BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            switch (action) {
                case GetLoginCodeTimerService.IN_RUNNING:
                    if (getRl.isEnabled()) {
                        getGd.setColor(ColorConfig.gray_a0a0a0);
                        getRl.setEnabled(false);
                    }
                    getTv.setText(intent.getStringExtra("time") + "秒后重新发送");
                    break;
                case GetLoginCodeTimerService.END_RUNNING:
                    getGd.setColor(ColorConfig.blue_2681fc);
                    getRl.setEnabled(true);
                    getTv.setText(VarConfig.getPwdTip);
                    break;
            }
        }
    };
}
