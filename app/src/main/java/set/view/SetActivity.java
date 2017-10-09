package set.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import phonebind.view.PhoneBindActivity;
import cache.LruJsonCache;
import config.VarConfig;
import utils.Utils;

public class SetActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private RelativeLayout phoneBindRl;
    private RelativeLayout clearCacheRl;
    private RelativeLayout msgTipRl;
    private RelativeLayout quitRl;
    private AlertDialog cacheDialog;
    private View cacheDialogView;
    private TextView cacheDialogTipTv;
    private TextView cacheDialogNoTv;
    private TextView cacheDialogYesTv;
    private LruJsonCache lruJsonCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_set, null);
        setContentView(rootView);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_set_return);
        phoneBindRl = (RelativeLayout) rootView.findViewById(R.id.rl_set_phone_bind);
        clearCacheRl = (RelativeLayout) rootView.findViewById(R.id.rl_set_cache_clear);
        msgTipRl = (RelativeLayout) rootView.findViewById(R.id.rl_set_msg_tip);
        quitRl = (RelativeLayout) rootView.findViewById(R.id.rl_set_quit);
    }

    private void initDialogView() {
        cacheDialogView = View.inflate(this, R.layout.dialog_cache, null);
        cacheDialog = new AlertDialog.Builder(this).setView(cacheDialogView).create();
        cacheDialog.setCanceledOnTouchOutside(false);
        cacheDialogTipTv = (TextView) cacheDialogView.findViewById(R.id.tv_dialog_cache_tip);
        cacheDialogNoTv = (TextView) cacheDialogView.findViewById(R.id.tv_dialog_cache_no);
        cacheDialogNoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cacheDialog.dismiss();
            }
        });
        cacheDialogYesTv = (TextView) cacheDialogView.findViewById(R.id.tv_dialog_cache_yes);
        cacheDialogYesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cacheDialog.dismiss();
                lruJsonCache.clear();
                Utils.toast(SetActivity.this, "缓存已清理");
            }
        });
    }

    private void initData() {
        lruJsonCache = LruJsonCache.get(this);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        phoneBindRl.setOnClickListener(this);
        clearCacheRl.setOnClickListener(this);
        msgTipRl.setOnClickListener(this);
        quitRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_set_return:
                finish();
                break;
            case R.id.rl_set_phone_bind:
                startActivity(new Intent(SetActivity.this, PhoneBindActivity.class));
                break;
            case R.id.rl_set_cache_clear:
                cacheDialogTipTv.setText("缓存大小为" + lruJsonCache.getCacheSize() +
                        "，是否清理缓存？");
                cacheDialog.show();
                break;
            case R.id.rl_set_msg_tip:
                Utils.toast(this, VarConfig.notyetTip);
                break;
            case R.id.rl_set_quit:
                Utils.toast(this, VarConfig.notyetTip);
                break;
        }
    }
}
