package activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import config.VarConfig;
import utils.Utils;

//提现-转到银行卡
public class WithDrawActivity extends CommonActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private EditText nameEt, numberEt, moneyEt;
    private TextView bankTv, limitTv, nextTv;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_with_draw, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_withdraw_return);
        nameEt = (EditText) rootView.findViewById(R.id.et_with_draw_card_name);
        numberEt = (EditText) rootView.findViewById(R.id.et_with_draw_card_number);
        moneyEt = (EditText) rootView.findViewById(R.id.et_with_draw_money);
        bankTv = (TextView) rootView.findViewById(R.id.tv_with_draw_bank);
        limitTv = (TextView) rootView.findViewById(R.id.tv_with_draw_limit);
        nextTv = (TextView) rootView.findViewById(R.id.tv_with_draw_next);
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
        bankTv.setOnClickListener(this);
        limitTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_withdraw_return:
                finish();
                break;
            case R.id.tv_with_draw_bank:
                Utils.toast(this, VarConfig.notyetTip);
                break;
            case R.id.tv_with_draw_limit:
                Utils.toast(this, VarConfig.notyetTip);
                break;
            case R.id.tv_with_draw_next:
                Utils.toast(this, VarConfig.notyetTip);
                break;
            default:
                break;
        }
    }

}
