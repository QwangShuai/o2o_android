package login.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import config.ColorConfig;
import config.VarConfig;
import login.bean.UserBean;
import login.presenter.ILoginPresenter;
import login.presenter.LoginPresenter;
import service.GetLoginCodeTimerService;
import utils.UserUtils;
import utils.Utils;
import view.CProgressDialog;

public class LoginActivity extends AppCompatActivity implements ILoginActivity, View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, getMovePwdRl;
    private EditText phoneNumberEt, movePwdEt;
    private TextView getMovePwdTv, loginTv;
    private GradientDrawable getMovePwdGd, loginGd;
    private CProgressDialog cpd;
    private ILoginPresenter iLoginPresenter = new LoginPresenter(this);

    private UserBean mUserBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_login, null);
        setContentView(rootView);
        initView();
        setListener();
    }

    private void initView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_login_return);
        phoneNumberEt = (EditText) rootView.findViewById(R.id.et_login_phone_number);
        getMovePwdTv = (TextView) rootView.findViewById(R.id.tv_login_get_move_pwd);
        getMovePwdRl = (RelativeLayout) rootView.findViewById(R.id.rl_login_get_move_pwd);
        getMovePwdGd = (GradientDrawable) getMovePwdRl.getBackground();
        movePwdEt = (EditText) rootView.findViewById(R.id.et_login_move_pwd);
        loginTv = (TextView) rootView.findViewById(R.id.tv_login_log);
        loginGd = (GradientDrawable) loginTv.getBackground();
        cpd = Utils.initProgressDialog(LoginActivity.this, cpd);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        getMovePwdRl.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        loginTv.setEnabled(false);
        movePwdEt.addTextChangedListener(textWatcher);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_login_return:
                finish();
                break;
            case R.id.rl_login_get_move_pwd:
                String phoneNumber = phoneNumberEt.getText().toString();
                if (Utils.isPhonenumber(phoneNumber))
                    iLoginPresenter.getSecurityCode(phoneNumber);
                break;
            case R.id.tv_login_log:
                String phone_number = phoneNumberEt.getText().toString();
                String verify_code = movePwdEt.getText().toString();
                if (Utils.isPhonenumber(phone_number))
                    iLoginPresenter.login(phone_number, verify_code);
                break;
            default:
                break;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iLoginPresenter != null) {
            iLoginPresenter.destroy();
            iLoginPresenter = null;
        }
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

    @Override
    public void showLoading() {
        cpd.show();
    }

    @Override
    public void hideLoading() {
        cpd.dismiss();
    }

    @Override
    public void getSecurityCodeFailure(String codeFailure) {
        Utils.toast(LoginActivity.this, codeFailure);
        Log.e("LoginActivity", codeFailure);
    }

    @Override
    public void getSecurityCodeSuccess(String codeSuccess) {
        Utils.toast(LoginActivity.this, codeSuccess);
        startService(new Intent(LoginActivity.this, GetLoginCodeTimerService.class));
    }

    @Override
    public void loginFailure(String loginFailure) {
        Utils.toast(LoginActivity.this, loginFailure);
    }

    @Override
    public void loginSuccess(UserBean userBean) {
        mUserBean = userBean;
        iLoginPresenter.postOnLine(userBean.getId());
    }

    @Override
    public void postOnlineFailure() {

    }

    @Override
    public void postOnlineSuccess() {
        mUserBean.setOnline("1");
        Utils.log(LoginActivity.this, "mUserBean=" + mUserBean.toString());
        UserUtils.saveUserData(LoginActivity.this, mUserBean);
        finish();
    }

}
