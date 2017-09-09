package activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gjzg.R;

public class SetPwdActivity extends CommonActivity implements View.OnClickListener {

    //根视图
    private View rootView;
    //返回视图
    private RelativeLayout returnRl;
    //设置提现密码视图
    private LinearLayout cashLl;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_set_pwd, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        //初始化返回视图
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_set_pwd_return);
        //初始化设置提现密码视图
        cashLl = (LinearLayout) rootView.findViewById(R.id.ll_set_pwd_cash);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        //返回视图监听
        returnRl.setOnClickListener(this);
        //设置提现密码视图监听
        cashLl.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回视图点击事件
            case R.id.rl_set_pwd_return:
                finish();
                break;
            //设置提现密码视图点击事件
            case R.id.ll_set_pwd_cash:
                startActivity(new Intent(this,PwdActivity.class));
                break;
        }
    }
}
