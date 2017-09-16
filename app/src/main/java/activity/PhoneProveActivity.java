package activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import config.VarConfig;
import service.CodeTimerService;

public class PhoneProveActivity extends CommonActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private EditText editText;
    private TextView tv0, tv1, tv2, tv3, resendTv;
    private LinearLayout pwdLl;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_phone_prove, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_phone_prove_return);
        editText = (EditText) rootView.findViewById(R.id.et_phone_prove);
        tv0 = (TextView) rootView.findViewById(R.id.tv_phone_prove_0);
        tv1 = (TextView) rootView.findViewById(R.id.tv_phone_prove_1);
        tv2 = (TextView) rootView.findViewById(R.id.tv_phone_prove_2);
        tv3 = (TextView) rootView.findViewById(R.id.tv_phone_prove_3);
        pwdLl = (LinearLayout) rootView.findViewById(R.id.ll_phone_prove_pwd);
        resendTv = (TextView) rootView.findViewById(R.id.tv_phone_prove_resend);
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
        editText.addTextChangedListener(textWatcher);
        pwdLl.setOnClickListener(this);
        resendTv.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

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
            refreshTv(s.toString());
            if (s.length() == 4) {
                startActivity(new Intent(PhoneProveActivity.this, IdActivity.class));
            }
        }
    };

    private void refreshTv(String str) {
        int length = str.length();
        switch (length) {
            case 0:
                tv0.setText("");
                tv1.setText("");
                tv2.setText("");
                tv3.setText("");
                break;
            case 1:
                tv0.setText(str.substring(0, 1));
                tv1.setText("");
                tv2.setText("");
                tv3.setText("");
                break;
            case 2:
                tv0.setText(str.substring(0, 1));
                tv1.setText(str.substring(1, 2));
                tv2.setText("");
                tv3.setText("");
                break;
            case 3:
                tv0.setText(str.substring(0, 1));
                tv1.setText(str.substring(1, 2));
                tv2.setText(str.substring(2, 3));
                tv3.setText("");
                break;
            case 4:
                tv0.setText(str.substring(0, 1));
                tv1.setText(str.substring(1, 2));
                tv2.setText(str.substring(2, 3));
                tv3.setText(str.substring(3, 4));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_phone_prove_return:
                finish();
                break;
            case R.id.ll_phone_prove_pwd:
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.tv_phone_prove_resend:
                startService(new Intent(this, CodeTimerService.class));
                break;
            default:
                break;
        }
    }

    private static IntentFilter updateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CodeTimerService.IN_RUNNING);
        intentFilter.addAction(CodeTimerService.END_RUNNING);
        return intentFilter;
    }

    private final BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            switch (action) {
                case CodeTimerService.IN_RUNNING:
                    if (resendTv.isEnabled())
                        resendTv.setEnabled(false);
                    // 正在倒计时
                    resendTv.setText(intent.getStringExtra("time"));
                    Log.e("TAG", intent.getStringExtra("time"));
                    break;
                case CodeTimerService.END_RUNNING:
                    // 完成倒计时
                    resendTv.setEnabled(true);
                    resendTv.setText(VarConfig.pwdResendTip);
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        // 注册广播
        registerReceiver(mUpdateReceiver, updateIntentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 移除注册
        unregisterReceiver(mUpdateReceiver);
    }
}
