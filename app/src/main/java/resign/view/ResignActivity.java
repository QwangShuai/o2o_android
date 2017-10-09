package resign.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gjzg.R;

import complain.view.ComplainActivity;

public class ResignActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private LinearLayout complainEmployerLl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_resign, null);
        setContentView(rootView);
        initView();
        setListener();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_resign_return);
        complainEmployerLl = (LinearLayout) rootView.findViewById(R.id.ll_resign_complain_employer);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        complainEmployerLl.setOnClickListener(this);
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
