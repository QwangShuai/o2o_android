package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.gjzg.R;

import cache.LruJsonCache;
import utils.Utils;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, clearCacheRl;
    LruJsonCache lruJsonCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_setting, null);
        setContentView(rootView);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_setting_return);
        clearCacheRl = (RelativeLayout) rootView.findViewById(R.id.rl_setting_clear_cache);
    }

    private void initData() {
        lruJsonCache = LruJsonCache.get(this);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        clearCacheRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_setting_return:
                finish();
                break;
            case R.id.rl_setting_clear_cache:
                Utils.toast(this, lruJsonCache.getCacheSize());
//                lruJsonCache.clear();
                break;
        }
    }
}
