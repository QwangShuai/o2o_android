package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.PayWayAdapter;
import bean.PayWay;
import listener.ListItemClickHelp;
import utils.Utils;

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener, ListItemClickHelp {

    private View rootView;
    private RelativeLayout returnRl, toPayRl;
    private EditText moneyEt;
    private ListView listView;

    private PayWayAdapter payWayAdapter;
    private List<PayWay> payWayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_recharge, null);
        setContentView(rootView);
        initView();
        initData();
        setData();
        setListener();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        payWayAdapter = null;
        payWayList = null;
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_recharge_return);
        moneyEt = (EditText) rootView.findViewById(R.id.et_recharge_money);
        listView = (ListView) rootView.findViewById(R.id.lv_pay_way);
        toPayRl = (RelativeLayout) rootView.findViewById(R.id.rl_recharge_to_pay);
    }

    private void initData() {
        payWayList = new ArrayList<>();
        payWayAdapter = new PayWayAdapter(this, payWayList, this);
    }

    private void setData() {
        listView.setAdapter(payWayAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        toPayRl.setOnClickListener(this);
    }

    private void loadData() {
        PayWay payWay1 = new PayWay();
        payWay1.setImageResource(R.mipmap.wx_pay);
        payWay1.setContent("微信支付");
        payWay1.setYesOrno(true);
        PayWay payWay2 = new PayWay();
        payWay2.setImageResource(R.mipmap.ali_pay);
        payWay2.setContent("支付宝支付");
        payWay2.setYesOrno(false);
        PayWay payWay3 = new PayWay();
        payWay3.setImageResource(R.mipmap.yl_pay);
        payWay3.setContent("银行卡支付");
        payWay3.setYesOrno(false);
        payWayList.add(payWay1);
        payWayList.add(payWay2);
        payWayList.add(payWay3);
        payWayAdapter.notifyDataSetChanged();
    }

    private void payJudge() {
        if (!TextUtils.isEmpty(moneyEt.getText().toString())) {
            int payNoCount = -3;
            int payWhich = 0;
            for (int i = 0; i < payWayList.size(); i++) {
                if (!payWayList.get(i).isYesOrno()) {
                    payNoCount++;
                } else {
                    payWhich = i;
                }
            }
            if (payNoCount == 0) {
                Utils.toast(this, "请选择支付方式");
            } else {
                switch (payWhich) {
                    case 0:
                        Utils.toast(this, "微信支付:" + moneyEt.getText().toString());
                        break;
                    case 1:
                        Utils.toast(this, "支付宝支付:" + moneyEt.getText().toString());
                        break;
                    case 2:
                        Utils.toast(this, "银行卡支付:" + moneyEt.getText().toString());
                        break;
                }
            }
        } else {
            Utils.toast(this, "请输入充值金额");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_recharge_return:
                finish();
                break;
            case R.id.rl_recharge_to_pay:
                payJudge();
                break;
        }
    }

    @Override
    public void onClick(View item, View widget, int position, int which, boolean isChecked) {
        switch (which) {
            case R.id.cb_item_pay_way_yes_or_no:
                payWayList.get(position).setYesOrno(isChecked);
                if (isChecked) {
                    for (int i = 0; i < payWayList.size(); i++) {
                        if (i != position) {
                            payWayList.get(i).setYesOrno(false);
                        }
                    }
                }
                payWayAdapter.notifyDataSetChanged();
                break;
        }
    }
}
