package activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import config.ColorConfig;
import config.NetConfig;
import config.StateConfig;
import config.VarConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import service.GetLoginCodeTimerService;
import utils.Utils;
import view.CProgressDialog;

//登录
public class LoginActivity extends CommonActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, getMovePwdRl;
    private EditText phoneNumberEt, movePwdEt;
    private TextView getMovePwdTv, loginTv;
    private GradientDrawable getMovePwdGd, loginGd;
    private CProgressDialog cpd;

    private OkHttpClient okHttpClient;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                cpd.dismiss();
                String s = (String) msg.obj;
                if (!TextUtils.isEmpty(s))
                    Utils.toast(LoginActivity.this, s);
            }
        }
    };

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_login, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_login_return);
        phoneNumberEt = (EditText) rootView.findViewById(R.id.et_login_phone_number);
        getMovePwdTv = (TextView) rootView.findViewById(R.id.tv_login_get_move_pwd);
        getMovePwdRl = (RelativeLayout) rootView.findViewById(R.id.rl_login_get_move_pwd);
        getMovePwdGd = (GradientDrawable) getMovePwdRl.getBackground();
        movePwdEt = (EditText) rootView.findViewById(R.id.et_login_move_pwd);
        loginTv = (TextView) rootView.findViewById(R.id.tv_login_log);
        loginGd = (GradientDrawable) loginTv.getBackground();
    }

    private void initDialogView() {
        cpd = new CProgressDialog(this, R.style.dialog_cprogress);
    }

    @Override
    protected void initData() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        getMovePwdRl.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        loginTv.setEnabled(false);
        movePwdEt.addTextChangedListener(textWatcher);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_login_return:
                finish();
                break;
            case R.id.rl_login_get_move_pwd:
                String phoneNumber = phoneNumberEt.getText().toString();
                if (judgePhoneNumber(phoneNumber))
                    getVerificationCode(phoneNumber);
                break;
            case R.id.tv_login_log:
                String phone_number = phoneNumberEt.getText().toString();
                String verify_code = movePwdEt.getText().toString();
                if (judgePhoneNumber(phone_number) && judgeVerificationCode(verify_code))
                    login(phone_number, verify_code);
                break;
            default:
                break;
        }
    }

    //判断手机号
    private boolean judgePhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            Utils.toast(LoginActivity.this, "手机号不能为空");
            return false;
        } else if (!Utils.isPhonenumber(phoneNumber)) {
            Utils.toast(this, "手机号码格式不正确");
            return false;
        } else {
            return true;
        }
    }

    //判断验证码
    private boolean judgeVerificationCode(String verifyCode) {
        if (TextUtils.isEmpty(verifyCode)) {
            Utils.toast(LoginActivity.this, "验证码不能为空");
            return false;
        } else {
            return true;
        }
    }

    //获取验证码
    private void getVerificationCode(String phoneNumber) {
        startService(new Intent(this, GetLoginCodeTimerService.class));
        Request request = new Request.Builder().url(NetConfig.codeUrl + phoneNumber).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(StateConfig.LOAD_NO_NET);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    try {
                        JSONObject objBean = new JSONObject(result);
                        if (objBean.optInt("code") == 1) {
                            JSONObject objData = objBean.optJSONObject("data");
                            if (objData != null) {
                                String s = objData.optString("msg");
                                Message msg = new Message();
                                msg.obj = s;
                                handler.sendMessage(msg);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //登录
    private void login(String phone_number, String verify_code) {
//        RequestBody body = new FormBody.Builder().add("phone_number", phone_number).add("verify_code", verify_code).build();
        Request request = new Request.Builder().url(NetConfig.loginUrl + "?phone_number=" + phone_number + "&verify_code=" + verify_code).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("TAG", result);
                    parseLoginJson(result);
                }
            }
        });
    }

    private void parseLoginJson(String json) {
        try {
            JSONObject objBean = new JSONObject(json);
            JSONObject objData = objBean.optJSONObject("data");
            int code = objBean.optInt("code");
            switch (code) {
                case 0:
                    if (objData != null) {
                        String msg = objData.optString("msg");
                        Message message = new Message();
                        message.obj = msg;
                        handler.sendMessage(message);
                    }
                    break;
                case 1:
                    if (objData != null) {
                        String token = objData.optString("token");
                        Message message = new Message();
                        message.obj = token;
                        handler.sendMessage(message);
                    }
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                    if (getMovePwdTv.isEnabled()) {
                        getMovePwdGd.setColor(ColorConfig.gray_a0a0a0);
                        getMovePwdTv.setEnabled(false);
                    }
                    getMovePwdTv.setText(intent.getStringExtra("time") + "秒后重新发送");
                    break;
                case GetLoginCodeTimerService.END_RUNNING:
                    getMovePwdGd.setColor(ColorConfig.blue_2681fc);
                    getMovePwdTv.setEnabled(true);
                    getMovePwdTv.setText(VarConfig.getPwdTip);
                    break;
            }
        }
    };

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

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
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
    };
}
