package activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import config.StateConfig;
import utils.Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //根视图
    private View rootView;
    //返回视图
    private RelativeLayout returnRl;
    //手机号码视图
    private EditText phoneNumberEt;
    //获取动态密码视图
    private TextView getMovePwdTv;
    private GradientDrawable getMovePwdGd;
    //动态密码视图
    private EditText movePwdEt;
    private int numSec = StateConfig.getMovePwdSec;
    //登录视图
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
                        getMovePwdGd.setColor(Color.parseColor("#2681FC"));
                        getMovePwdTv.setText("获取动态密码");
                        getMovePwdTv.setEnabled(true);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_login, null);
        setContentView(rootView);
        initView();
        setListener();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        //初始化返回视图
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_login_return);
        //初始化手机号码视图
        phoneNumberEt = (EditText) rootView.findViewById(R.id.et_login_phone_number);
        //初始化获取动态密码视图
        getMovePwdTv = (TextView) rootView.findViewById(R.id.tv_login_get_move_pwd);
        getMovePwdGd = (GradientDrawable) getMovePwdTv.getBackground();
        getMovePwdGd.setColor(Color.parseColor("#2681FC"));
        //初始化动态密码视图
        movePwdEt = (EditText) rootView.findViewById(R.id.et_login_move_pwd);
        //初始化登录视图
        loginTv = (TextView) rootView.findViewById(R.id.tv_login_log);
    }

    private void setListener() {
        //返回视图监听
        returnRl.setOnClickListener(this);
        //获取动态密码视图监听
        getMovePwdTv.setOnClickListener(this);
        //登录视图监听
        loginTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回视图点击事件
            case R.id.rl_login_return:
                finish();
                break;
            //获取动态密码视图点击事件
            case R.id.tv_login_get_move_pwd:
                if (Utils.isPhonenumber(phoneNumberEt.getText().toString())) {
                    getMovePwd();
                } else {
                    Utils.toast(this, "手机号码格式不正确");
                }
                break;
            //登录视图点击事件
            case R.id.tv_login_log:
                login();
                break;
            default:
                break;
        }
    }

    private void getMovePwd() {
        Utils.toast(this, "获取动态密码");
        getMovePwdGd.setColor(Color.GRAY);
        getMovePwdTv.setEnabled(false);
        handler.sendEmptyMessage(1);
    }

    private void login() {
        Utils.toast(this, "手机号：" + phoneNumberEt.getText().toString() + "\n" + "动态密码：" + movePwdEt.getText().toString());
    }
}
