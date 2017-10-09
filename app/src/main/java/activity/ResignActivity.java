package activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gjzg.R;

//辞职
public class ResignActivity extends CommonActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private LinearLayout complainEmployerLl;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_resign, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_resign_return);
        complainEmployerLl = (LinearLayout) rootView.findViewById(R.id.ll_resign_complain_employer);
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
        complainEmployerLl.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_resign_return:
                finish();
                break;
            case R.id.ll_resign_complain_employer:
                startActivity(new Intent(this, ComplainActivity.class));
                break;
            default:
                break;
        }
    }
}
