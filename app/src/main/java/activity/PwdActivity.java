package activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gjzg.R;

import config.ShareConfig;

public class PwdActivity extends CommonActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private EditText editText;
    private TextView textView;
    private ImageView pIv0, pIv1, pIv2, pIv3, pIv4, pIv5;
    private LinearLayout pwdLl;
    private TextView fgtTv;

    private String firstPwd;
    private String secondPwd;
    private String beforePwd;

    private final int FIRST_PWD = 0;
    private final int SECOND_PWD = 1;
    private final int BEFORE_PWD = 2;
    private int pwdState;

    private SharedPreferences sharedPreferences;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_pwd, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_pwd_return);
        editText = (EditText) rootView.findViewById(R.id.et_pwd);
        textView = (TextView) rootView.findViewById(R.id.tv_pwd_tip);
        pIv0 = (ImageView) rootView.findViewById(R.id.iv_pwd_point_0);
        pIv1 = (ImageView) rootView.findViewById(R.id.iv_pwd_point_1);
        pIv2 = (ImageView) rootView.findViewById(R.id.iv_pwd_point_2);
        pIv3 = (ImageView) rootView.findViewById(R.id.iv_pwd_point_3);
        pIv4 = (ImageView) rootView.findViewById(R.id.iv_pwd_point_4);
        pIv5 = (ImageView) rootView.findViewById(R.id.iv_pwd_point_5);
        pwdLl = (LinearLayout) rootView.findViewById(R.id.ll_pwd);
        fgtTv = (TextView) rootView.findViewById(R.id.tv_pwd_fgt);
    }

    @Override
    protected void initData() {
        sharedPreferences = getSharedPreferences(ShareConfig.shareName, Context.MODE_PRIVATE);
        String payPwd = sharedPreferences.getString(ShareConfig.payPwd, null);
        if (TextUtils.isEmpty(payPwd)) {
            pwdState = FIRST_PWD;
        } else {
            beforePwd = payPwd;
            pwdState = BEFORE_PWD;
            fgtTv.setVisibility(View.VISIBLE);
        }
        refreshView();
        refreshPoint(0);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        editText.addTextChangedListener(textWatcher);
        pwdLl.setOnClickListener(this);
        returnRl.setOnClickListener(this);
        fgtTv.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    private void judge() {
        if (firstPwd.equals(secondPwd)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ShareConfig.payPwd, firstPwd);
            editor.commit();
            Toast.makeText(this, "设置密码成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshView() {
        switch (pwdState) {
            case FIRST_PWD:
                textView.setText("请输入新密码");
                break;
            case SECOND_PWD:
                textView.setText("再次输入密码");
                break;
            case BEFORE_PWD:
                textView.setText("请输入原密码");
                break;
        }
    }

    private void refreshPoint(int length) {
        switch (length) {
            case 0:
                pIv0.setVisibility(View.GONE);
                pIv1.setVisibility(View.GONE);
                pIv2.setVisibility(View.GONE);
                pIv3.setVisibility(View.GONE);
                pIv4.setVisibility(View.GONE);
                pIv5.setVisibility(View.GONE);
                break;
            case 1:
                pIv0.setVisibility(View.VISIBLE);
                pIv1.setVisibility(View.GONE);
                pIv2.setVisibility(View.GONE);
                pIv3.setVisibility(View.GONE);
                pIv4.setVisibility(View.GONE);
                pIv5.setVisibility(View.GONE);
                break;
            case 2:
                pIv0.setVisibility(View.VISIBLE);
                pIv1.setVisibility(View.VISIBLE);
                pIv2.setVisibility(View.GONE);
                pIv3.setVisibility(View.GONE);
                pIv4.setVisibility(View.GONE);
                pIv5.setVisibility(View.GONE);
                break;
            case 3:
                pIv0.setVisibility(View.VISIBLE);
                pIv1.setVisibility(View.VISIBLE);
                pIv2.setVisibility(View.VISIBLE);
                pIv3.setVisibility(View.GONE);
                pIv4.setVisibility(View.GONE);
                pIv5.setVisibility(View.GONE);
                break;
            case 4:
                pIv0.setVisibility(View.VISIBLE);
                pIv1.setVisibility(View.VISIBLE);
                pIv2.setVisibility(View.VISIBLE);
                pIv3.setVisibility(View.VISIBLE);
                pIv4.setVisibility(View.GONE);
                pIv5.setVisibility(View.GONE);
                break;
            case 5:
                pIv0.setVisibility(View.VISIBLE);
                pIv1.setVisibility(View.VISIBLE);
                pIv2.setVisibility(View.VISIBLE);
                pIv3.setVisibility(View.VISIBLE);
                pIv4.setVisibility(View.VISIBLE);
                pIv5.setVisibility(View.GONE);
                break;
            case 6:
                pIv0.setVisibility(View.VISIBLE);
                pIv1.setVisibility(View.VISIBLE);
                pIv2.setVisibility(View.VISIBLE);
                pIv3.setVisibility(View.VISIBLE);
                pIv4.setVisibility(View.VISIBLE);
                pIv5.setVisibility(View.VISIBLE);
                break;
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
            refreshPoint(s.length());
            if (s.length() == 6) {
                switch (pwdState) {
                    case FIRST_PWD:
                        firstPwd = s.toString();
                        editText.setText("");
                        pwdState = SECOND_PWD;
                        refreshView();
                        refreshPoint(0);
                        break;
                    case SECOND_PWD:
                        secondPwd = s.toString();
                        judge();
                        break;
                    case BEFORE_PWD:
                        if (beforePwd.equals(s.toString())) {
                            editText.setText("");
                            pwdState = FIRST_PWD;
                            refreshView();
                            refreshPoint(0);
                        } else {
                            Toast.makeText(PwdActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_pwd_return:
                finish();
                break;
            case R.id.ll_pwd:
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.tv_pwd_fgt:
                startActivity(new Intent(this, PhoneProveActivity.class));
                break;
        }
    }
}
