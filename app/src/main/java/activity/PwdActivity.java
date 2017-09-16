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

import com.gjzg.R;

import config.ShareConfig;
import config.VarConfig;
import utils.Utils;

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

    private int state = VarConfig.PWD_FIRST;

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
            state = VarConfig.PWD_FIRST;
        } else {
            beforePwd = payPwd;
            state = VarConfig.PWD_ORIGIN;
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
            Utils.toast(this, VarConfig.pwdSuccessTip);
            finish();
        } else {
            Utils.toast(this, VarConfig.pwdDifferTip);
        }
    }

    private void refreshView() {
        switch (state) {
            case VarConfig.PWD_FIRST:
                textView.setText(VarConfig.pwdFirstTip);
                break;
            case VarConfig.PWD_AGAIN:
                textView.setText(VarConfig.pwdAgainTip);
                break;
            case VarConfig.PWD_ORIGIN:
                textView.setText(VarConfig.pwdOriginTip);
                break;
            case VarConfig.PWD_NEW:
                textView.setText(VarConfig.pwdNewTip);
                break;
            default:
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
                switch (state) {
                    case VarConfig.PWD_FIRST:
                        firstPwd = s.toString();
                        editText.setText("");
                        state = VarConfig.PWD_AGAIN;
                        refreshView();
                        refreshPoint(0);
                        break;
                    case VarConfig.PWD_AGAIN:
                        secondPwd = s.toString();
                        judge();
                        break;
                    case VarConfig.PWD_ORIGIN:
                        if (beforePwd.equals(s.toString())) {
                            editText.setText("");
                            state = VarConfig.PWD_NEW;
                            refreshView();
                            refreshPoint(0);
                        } else {
                            Utils.toast(PwdActivity.this, VarConfig.pwdErrorTip);
                        }
                        break;
                    case VarConfig.PWD_NEW:
                        firstPwd = s.toString();
                        editText.setText("");
                        state = VarConfig.PWD_AGAIN;
                        refreshView();
                        refreshPoint(0);
                        break;
                    default:
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
