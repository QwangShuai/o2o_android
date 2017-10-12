package aboutus.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.gjzg.R;

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(AboutUsActivity.this).inflate(R.layout.activity_about_us, null);
        setContentView(rootView);
        initView();
        setListener();
    }

    private void initView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_about_us_return);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_about_us_return:
                finish();
                break;
            default:
                break;
        }
    }
}
