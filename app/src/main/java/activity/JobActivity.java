package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.gjzg.R;

import utils.Utils;

public class JobActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, screenRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_job, null);
        setContentView(rootView);
        initView();
        setListener();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_job_return);
        screenRl = (RelativeLayout) rootView.findViewById(R.id.rl_job_screen);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        screenRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_job_return:
                finish();
                break;
            case R.id.rl_job_screen:
                Utils.toast(this, "筛选");
                break;
        }
    }
}
