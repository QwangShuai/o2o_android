package activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import config.ColorConfig;
import config.StateConfig;
import utils.Utils;

public class LoginActivity extends CommonActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, getMovePwdRl;
    private EditText phoneNumberEt;
    private TextView getMovePwdTv;
    private GradientDrawable getMovePwdGd, loginGd;
    private EditText movePwdEt;
    private int numSec = StateConfig.getMovePwdSec;
    private TextView loginTv;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                if (msg.what == 1) {
                    getMovePwdTv.setText(numSec + "秒重新获取");
                    numSec--;
                    if (numSec != -1) {
                        this.sendEmptyMessageDelayed(1, 1000);
                    } else {
                        getMovePwdGd.setColor(ColorConfig.blue_2681fc);
                        getMovePwdTv.setText("获取动态密码");
                        getMovePwdRl.setEnabled(true);
                        numSec = StateConfig.getMovePwdSec;
                    }
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
    }

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_login, null);
    }

    @Override
    protected void initView() {
        initRootView();
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

    @Override
    protected void initData() {

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
                if (Utils.isPhonenumber(phoneNumberEt.getText().toString())) {
                    getMovePwd();
                } else {
                    if (TextUtils.isEmpty(phoneNumberEt.getText().toString())) {
                        Utils.toast(this, "请输入手机号");
                    } else {
                        Utils.toast(this, "手机号码格式不正确");
                    }
                }
                break;
            case R.id.tv_login_log:
                login();
                break;
            default:
                break;
        }
    }

    private void getMovePwd() {
        Utils.toast(this, "获取动态密码");
        getMovePwdGd.setColor(ColorConfig.gray_a0a0a0);
        getMovePwdRl.setEnabled(false);
        handler.sendEmptyMessage(1);
    }

    private void login() {
        Utils.toast(this, "手机号：" + phoneNumberEt.getText().toString() + "\n" + "动态密码：" + movePwdEt.getText().toString());
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
            if (s.length() == 0) {
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
