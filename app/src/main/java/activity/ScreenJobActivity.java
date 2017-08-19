package activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.gjzg.R;

import utils.Utils;

public class ScreenJobActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, searchRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_screen_job, null);
        setContentView(rootView);
        initView();
        setListener();
    }

    private void initView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_screen_job_return);
        searchRl = (RelativeLayout) rootView.findViewById(R.id.rl_screen_job_search);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        searchRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_screen_job_return:
                finish();
                break;
            case R.id.rl_screen_job_search:
                Utils.toast(this, "搜索");
                break;
        }
    }
}
