package activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import config.VarConfig;
import utils.Utils;

public class IdActivity extends CommonActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private TextView tipTv, nextTv;
    private EditText numberEt;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_id, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_id_return);
        tipTv = (TextView) rootView.findViewById(R.id.tv_id_tip);
        nextTv = (TextView) rootView.findViewById(R.id.tv_id_next);
        numberEt = (EditText) rootView.findViewById(R.id.et_id_number);
    }

    @Override
    protected void initData() {
        tipTv.setText("请填写王小二的身份证号");
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        nextTv.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_id_return:
                finish();
                break;
            case R.id.tv_id_next:
                Utils.toast(this, VarConfig.notyetTip);
                break;
            default:
                break;
        }
    }
}
