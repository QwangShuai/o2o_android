package workerscreen.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import bean.ScreenBean;
import config.CodeConfig;
import config.IntentConfig;

public class WorkerScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout closeRl, searchRl;
    private EditText nameEt;
    private ImageView leisureIv, midIv;
    private int workerState = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_screen_worker, null);
        setContentView(rootView);
        initView();
        setListener();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        closeRl = (RelativeLayout) rootView.findViewById(R.id.rl_screen_close);
        searchRl = (RelativeLayout) rootView.findViewById(R.id.rl_screen_search);
        nameEt = (EditText) rootView.findViewById(R.id.et_screen_name);
        leisureIv = (ImageView) rootView.findViewById(R.id.iv_screen_worker_leisure);
        midIv = (ImageView) rootView.findViewById(R.id.iv_screen_worker_mid);
    }

    private void setListener() {
        closeRl.setOnClickListener(this);
        searchRl.setOnClickListener(this);
        leisureIv.setOnClickListener(this);
        midIv.setOnClickListener(this);
    }

    private void changeState(int tarState) {
        if (tarState != workerState) {
            switch (tarState) {
                case 0:
                    leisureIv.setImageResource(R.mipmap.worker_leisure);
                    midIv.setImageResource(R.mipmap.worker_mid_g);
                    break;
                case 1:
                    leisureIv.setImageResource(R.mipmap.worker_leisure_g);
                    midIv.setImageResource(R.mipmap.worker_mid);
                    break;
            }
            workerState = tarState;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_screen_close:
                finish();
                break;
            case R.id.rl_screen_search:
                result();
                break;
            case R.id.iv_screen_worker_leisure:
                changeState(0);
                break;
            case R.id.iv_screen_worker_mid:
                changeState(1);
                break;
        }
    }

    private void result() {
        Intent intent = new Intent();
        ScreenBean screenBean = new ScreenBean();
        screenBean.setName(nameEt.getText().toString());
        screenBean.setState(workerState);
        intent.putExtra(IntentConfig.screenToWorker, screenBean);
        setResult(CodeConfig.screenResultCode, intent);
        finish();
    }
}
