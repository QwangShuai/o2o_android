package activity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import utils.Utils;

public class RechargeActivity extends CommonActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private EditText moneyEt;
    private LinearLayout wxLl;
    private LinearLayout zfbLl;
    private LinearLayout ylLl;
    private ImageView wxIv, zfbIv, ylIv;
    private TextView toPayTv;
    private int curState;
    private int tarState;
    private final int WX_PAY = 0;
    private final int ZFB_PAY = 1;
    private final int YL_PAY = 2;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_recharge, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_recharge_return);
        moneyEt = (EditText) rootView.findViewById(R.id.et_recharge_money);
        wxLl = (LinearLayout) rootView.findViewById(R.id.ll_recharge_wx);
        zfbLl = (LinearLayout) rootView.findViewById(R.id.ll_recharge_zfb);
        ylLl = (LinearLayout) rootView.findViewById(R.id.ll_recharge_yl);
        wxIv = (ImageView) rootView.findViewById(R.id.iv_recharge_wx_point);
        zfbIv = (ImageView) rootView.findViewById(R.id.iv_recharge_zfb_point);
        ylIv = (ImageView) rootView.findViewById(R.id.iv_recharge_yl_point);
        toPayTv = (TextView) rootView.findViewById(R.id.tv_recharge_pay);
    }

    @Override
    protected void initData() {
        curState = WX_PAY;
        tarState = -1;
    }

    @Override
    protected void setData() {
    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        wxLl.setOnClickListener(this);
        zfbLl.setOnClickListener(this);
        ylLl.setOnClickListener(this);
        toPayTv.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
    }

    private void changePay() {
        if (curState != tarState) {
            switch (curState) {
                case WX_PAY:
                    wxIv.setImageResource(R.mipmap.point_gray);
                    break;
                case ZFB_PAY:
                    zfbIv.setImageResource(R.mipmap.point_gray);
                    break;
                case YL_PAY:
                    ylIv.setImageResource(R.mipmap.point_gray);
                    break;
                default:
                    break;
            }
            switch (tarState) {
                case WX_PAY:
                    wxIv.setImageResource(R.mipmap.pay_choosed);
                    break;
                case ZFB_PAY:
                    zfbIv.setImageResource(R.mipmap.pay_choosed);
                    break;
                case YL_PAY:
                    ylIv.setImageResource(R.mipmap.pay_choosed);
                    break;
                default:
                    break;
            }
            curState = tarState;
        }
    }

    private void toPay() {
        String money = moneyEt.getText().toString();
        if (TextUtils.isEmpty(money)) {
            Utils.toast(this, "请输入金额");
        } else {
            switch (curState) {
                case WX_PAY:
                    Utils.toast(this, "微信支付：" + money);
                    break;
                case ZFB_PAY:
                    Utils.toast(this, "支付宝支付：" + money);
                    break;
                case YL_PAY:
                    Utils.toast(this, "银联支付：" + money);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_recharge_return:
                finish();
                break;
            case R.id.ll_recharge_wx:
                tarState = WX_PAY;
                changePay();
                break;
            case R.id.ll_recharge_zfb:
                tarState = ZFB_PAY;
                changePay();
                break;
            case R.id.ll_recharge_yl:
                tarState = YL_PAY;
                changePay();
                break;
            case R.id.tv_recharge_pay:
                toPay();
                break;
        }
    }

}
