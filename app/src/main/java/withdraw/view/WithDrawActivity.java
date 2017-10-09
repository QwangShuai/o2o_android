package withdraw.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import config.VarConfig;
import utils.Utils;

public class WithDrawActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private EditText nameEt, numberEt, moneyEt;
    private TextView bankTv, limitTv, nextTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_with_draw, null);
        setContentView(rootView);
        initView();
        setListener();
    }

    private void initView() {
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

    private void setListener() {
        returnRl.setOnClickListener(this);
        bankTv.setOnClickListener(this);
        limitTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);
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
