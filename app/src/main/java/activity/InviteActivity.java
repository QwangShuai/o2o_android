package activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import config.VarConfig;
import utils.Utils;

public class InviteActivity extends CommonActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private TextView inviteTv;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_invite, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_invite_return);
        inviteTv = (TextView) rootView.findViewById(R.id.tv_invite);
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
        inviteTv.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_invite_return:
                finish();
                break;
            case R.id.tv_invite:
                Utils.toast(this, VarConfig.notyetTip);
                break;
        }
    }
}
