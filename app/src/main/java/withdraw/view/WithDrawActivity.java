package withdraw.view;

import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import taskconfirm.adapter.InputPasswordAdapter;
import taskconfirm.bean.InputPasswordBean;
import utils.UserUtils;
import utils.Utils;
import view.CProgressDialog;
import bean.WithDrawBean;
import withdraw.presenter.IWithDrawPresenter;
import withdraw.presenter.WithDrawPresenter;

public class WithDrawActivity extends AppCompatActivity implements IWithDrawActivity, View.OnClickListener, AdapterView.OnItemClickListener {

    private View rootView;

    private View inputPasswordView;
    private GridView inputPasswordGv;
    private List<InputPasswordBean> inputPasswordBeanList = new ArrayList<>();
    private InputPasswordAdapter inputPasswordAdapter;
    private PopupWindow inputPasswordPop;
    private TextView inputPasswordCloseTv, inputPasswordForgetTv;
    private ImageView inputPasswordPoint0Iv, inputPasswordPoint1Iv, inputPasswordPoint2Iv, inputPasswordPoint3Iv, inputPasswordPoint4Iv, inputPasswordPoint5Iv;
    private StringBuilder inputPasswordSb;
    private WithDrawBean withDrawBean = new WithDrawBean();
    private RelativeLayout returnRl;
    private EditText nameEt, numberEt, moneyEt;
    private TextView bankTv, limitTv, nextTv;
    private CProgressDialog cpd;
    private IWithDrawPresenter withDrawPresenter;

    private final int LOAD_SUCCESS = 1;
    private final int LOAD_FAILURE = 2;
    private String tip = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case LOAD_SUCCESS:
                        cpd.dismiss();
                        Utils.toast(WithDrawActivity.this, tip);
                        finish();
                        break;
                    case LOAD_FAILURE:
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_with_draw, null);
        setContentView(rootView);
        initView();
        initData();
        setData();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeMessages(LOAD_SUCCESS);
            handler.removeMessages(LOAD_FAILURE);
        }
    }

    private void initView() {
        initRootView();
        initPopView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_withdraw_return);
        nameEt = (EditText) rootView.findViewById(R.id.et_with_draw_card_name);
        numberEt = (EditText) rootView.findViewById(R.id.et_with_draw_card_number);
        moneyEt = (EditText) rootView.findViewById(R.id.et_with_draw_money);
        bankTv = (TextView) rootView.findViewById(R.id.tv_with_draw_bank);
        limitTv = (TextView) rootView.findViewById(R.id.tv_with_draw_limit);
        nextTv = (TextView) rootView.findViewById(R.id.tv_with_draw_next);
        cpd = Utils.initProgressDialog(WithDrawActivity.this, cpd);
    }

    private void initPopView() {
        inputPasswordView = LayoutInflater.from(WithDrawActivity.this).inflate(R.layout.pop_input_password, null);
        inputPasswordGv = (GridView) inputPasswordView.findViewById(R.id.gv_pop_input_password);
        inputPasswordCloseTv = (TextView) inputPasswordView.findViewById(R.id.tv_pop_input_password_close);
        inputPasswordCloseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputPasswordPop.dismiss();
            }
        });
        inputPasswordForgetTv = (TextView) inputPasswordView.findViewById(R.id.tv_pop_input_password_forget);
        inputPasswordPoint0Iv = (ImageView) inputPasswordView.findViewById(R.id.iv_pwd_point_0);
        inputPasswordPoint1Iv = (ImageView) inputPasswordView.findViewById(R.id.iv_pwd_point_1);
        inputPasswordPoint2Iv = (ImageView) inputPasswordView.findViewById(R.id.iv_pwd_point_2);
        inputPasswordPoint3Iv = (ImageView) inputPasswordView.findViewById(R.id.iv_pwd_point_3);
        inputPasswordPoint4Iv = (ImageView) inputPasswordView.findViewById(R.id.iv_pwd_point_4);
        inputPasswordPoint5Iv = (ImageView) inputPasswordView.findViewById(R.id.iv_pwd_point_5);
        inputPasswordPop = new PopupWindow(inputPasswordView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        inputPasswordPop.setFocusable(true);
        inputPasswordPop.setTouchable(true);
        inputPasswordPop.setOutsideTouchable(true);
        inputPasswordPop.setBackgroundDrawable(new PaintDrawable());
        inputPasswordPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
                inputPasswordSb.delete(0, inputPasswordSb.length());
                notifyPoints(inputPasswordSb.length());
                inputPasswordBeanList.clear();
            }
        });
    }

    private void initData() {
        inputPasswordAdapter = new InputPasswordAdapter(WithDrawActivity.this, inputPasswordBeanList);
        withDrawPresenter = new WithDrawPresenter(this);
        withDrawBean.setU_id(UserUtils.readUserData(WithDrawActivity.this).getId());
        withDrawBean.setP_id("1");
    }

    private void setData() {
        inputPasswordGv.setAdapter(inputPasswordAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        bankTv.setOnClickListener(this);
        limitTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);
        inputPasswordGv.setOnItemClickListener(this);
        nameEt.addTextChangedListener(nameTw);
        numberEt.addTextChangedListener(numberTw);
        moneyEt.addTextChangedListener(moneyTw);
    }

    private void judge() {
        if (TextUtils.isEmpty(withDrawBean.getUwl_truename())) {
            Utils.toast(WithDrawActivity.this, "姓名不能为空");
        } else if (TextUtils.isEmpty(withDrawBean.getUwl_card())) {
            Utils.toast(WithDrawActivity.this, "卡号不能为空");
        } else if (TextUtils.isEmpty(withDrawBean.getP_id())) {
            Utils.toast(WithDrawActivity.this, "请选择银行");
        } else if (TextUtils.isEmpty(withDrawBean.getUwl_amount())) {
            Utils.toast(WithDrawActivity.this, "请输入金额");
        } else {
            Utils.log(WithDrawActivity.this, "withDrawBean=" + withDrawBean.toString());
            pop();
        }
    }

    private void backgroundAlpha(float f) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = f;
        getWindow().setAttributes(lp);
    }

    private void notifyPoints(int num) {
        switch (num) {
            case 0:
                inputPasswordPoint0Iv.setVisibility(View.GONE);
                inputPasswordPoint1Iv.setVisibility(View.GONE);
                inputPasswordPoint2Iv.setVisibility(View.GONE);
                inputPasswordPoint3Iv.setVisibility(View.GONE);
                inputPasswordPoint4Iv.setVisibility(View.GONE);
                inputPasswordPoint5Iv.setVisibility(View.GONE);
                break;
            case 1:
                inputPasswordPoint0Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint1Iv.setVisibility(View.GONE);
                inputPasswordPoint2Iv.setVisibility(View.GONE);
                inputPasswordPoint3Iv.setVisibility(View.GONE);
                inputPasswordPoint4Iv.setVisibility(View.GONE);
                inputPasswordPoint5Iv.setVisibility(View.GONE);
                break;
            case 2:
                inputPasswordPoint0Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint1Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint2Iv.setVisibility(View.GONE);
                inputPasswordPoint3Iv.setVisibility(View.GONE);
                inputPasswordPoint4Iv.setVisibility(View.GONE);
                inputPasswordPoint5Iv.setVisibility(View.GONE);
                break;
            case 3:
                inputPasswordPoint0Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint1Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint2Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint3Iv.setVisibility(View.GONE);
                inputPasswordPoint4Iv.setVisibility(View.GONE);
                inputPasswordPoint5Iv.setVisibility(View.GONE);
                break;
            case 4:
                inputPasswordPoint0Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint1Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint2Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint3Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint4Iv.setVisibility(View.GONE);
                inputPasswordPoint5Iv.setVisibility(View.GONE);
                break;
            case 5:
                inputPasswordPoint0Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint1Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint2Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint3Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint4Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint5Iv.setVisibility(View.GONE);
                break;
            case 6:
                inputPasswordPoint0Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint1Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint2Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint3Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint4Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint5Iv.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_withdraw_return:
                finish();
                break;
            case R.id.tv_with_draw_bank:
                break;
            case R.id.tv_with_draw_limit:
                break;
            case R.id.tv_with_draw_next:
                judge();
                break;
        }
    }

    private void pop() {
        inputPasswordSb = new StringBuilder();
        InputPasswordBean inputPasswordBean0 = new InputPasswordBean(0, 1, "");
        InputPasswordBean inputPasswordBean1 = new InputPasswordBean(0, 2, "ABC");
        InputPasswordBean inputPasswordBean2 = new InputPasswordBean(0, 3, "DEF");
        InputPasswordBean inputPasswordBean3 = new InputPasswordBean(0, 4, "GHI");
        InputPasswordBean inputPasswordBean4 = new InputPasswordBean(0, 5, "JKL");
        InputPasswordBean inputPasswordBean5 = new InputPasswordBean(0, 6, "MNO");
        InputPasswordBean inputPasswordBean6 = new InputPasswordBean(0, 7, "PQRS");
        InputPasswordBean inputPasswordBean7 = new InputPasswordBean(0, 8, "TUV");
        InputPasswordBean inputPasswordBean8 = new InputPasswordBean(0, 9, "WXYZ");
        InputPasswordBean inputPasswordBean9 = new InputPasswordBean(1, 0, "");
        InputPasswordBean inputPasswordBean10 = new InputPasswordBean(0, 0, "");
        InputPasswordBean inputPasswordBean11 = new InputPasswordBean(2, 0, "");
        inputPasswordBeanList.add(inputPasswordBean0);
        inputPasswordBeanList.add(inputPasswordBean1);
        inputPasswordBeanList.add(inputPasswordBean2);
        inputPasswordBeanList.add(inputPasswordBean3);
        inputPasswordBeanList.add(inputPasswordBean4);
        inputPasswordBeanList.add(inputPasswordBean5);
        inputPasswordBeanList.add(inputPasswordBean6);
        inputPasswordBeanList.add(inputPasswordBean7);
        inputPasswordBeanList.add(inputPasswordBean8);
        inputPasswordBeanList.add(inputPasswordBean9);
        inputPasswordBeanList.add(inputPasswordBean10);
        inputPasswordBeanList.add(inputPasswordBean11);
        inputPasswordAdapter.notifyDataSetChanged();
        Utils.setGridViewHeight(inputPasswordGv, 3);
        backgroundAlpha(0.5f);
        inputPasswordPop.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void loadSuccess(String json) {
        Utils.log(WithDrawActivity.this, "json=" + json);
        try {
            JSONObject beanObj = new JSONObject(json);
            int code = beanObj.optInt("code");
            switch (code) {
                case 1:
                    JSONObject dataObj = beanObj.optJSONObject("data");
                    if (dataObj != null) {
                        tip = dataObj.optString("msg");
                        handler.sendEmptyMessage(LOAD_SUCCESS);
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFailure(String failure) {
        Utils.log(WithDrawActivity.this, failure);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.gv_pop_input_password:
                InputPasswordBean inputPasswordBean = inputPasswordBeanList.get(position);
                if (inputPasswordBean != null) {
                    switch (inputPasswordBean.getType()) {
                        case 0:
                            if (inputPasswordSb.length() < 6) {
                                inputPasswordSb.append(inputPasswordBean.getNumber());
                                notifyPoints(inputPasswordSb.length());
                            }
                            if (inputPasswordSb.length() == 6) {
                                withDrawBean.setPassword(inputPasswordSb.toString());
                                cpd.show();
                                withDrawPresenter.withdraw(withDrawBean);
                            }
                            break;
                        case 2:
                            if (inputPasswordSb.length() != 0) {
                                inputPasswordSb.deleteCharAt(inputPasswordSb.length() - 1);
                                notifyPoints(inputPasswordSb.length());
                            }
                            break;
                    }
                }
                break;
        }
    }

    TextWatcher nameTw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            withDrawBean.setUwl_truename(s.toString());
        }
    };

    TextWatcher numberTw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            withDrawBean.setUwl_card(s.toString());
        }
    };

    TextWatcher moneyTw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            withDrawBean.setUwl_amount(s.toString());
        }
    };
}
