package activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import cache.LruJsonCache;
import utils.Utils;

//设置页面
public class SetActivity extends AppCompatActivity implements View.OnClickListener {

    //根视图
    private View rootView;
    //返回视图
    private RelativeLayout returnRl;
    //手机绑定视图
    private RelativeLayout phoneBindRl;
    //清除缓存视图
    private RelativeLayout clearCacheRl;
    //消息提示视图
    private RelativeLayout msgTipRl;
    //退出登录视图
    private RelativeLayout quitRl;
    //清除缓存对话框视图
    private AlertDialog cacheDialog;
    private View cacheDialogView;
    private TextView cacheDialogTipTv;
    private TextView cacheDialogNoTv;
    private TextView cacheDialogYesTv;
    //缓存工具类
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
        //初始化返回视图
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_set_return);
        //初始化手机绑定视图
        phoneBindRl = (RelativeLayout) rootView.findViewById(R.id.rl_set_phone_bind);
        //初始化清除缓存视图
        clearCacheRl = (RelativeLayout) rootView.findViewById(R.id.rl_set_cache_clear);
        //初始化消息提示视图
        msgTipRl = (RelativeLayout) rootView.findViewById(R.id.rl_set_msg_tip);
        //初始化退出登录视图
        quitRl = (RelativeLayout) rootView.findViewById(R.id.rl_set_quit);
    }

    private void initDialogView() {
        //初始化清除缓存对话框视图
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
        //初始化缓存工具类
        lruJsonCache = LruJsonCache.get(this);
    }

    private void setListener() {
        //返回视图监听
        returnRl.setOnClickListener(this);
        //手机绑定视图监听
        phoneBindRl.setOnClickListener(this);
        //清除缓存视图监听
        clearCacheRl.setOnClickListener(this);
        //消息提示视图监听
        msgTipRl.setOnClickListener(this);
        //退出登录视图监听
        quitRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回视图点击事件
            case R.id.rl_set_return:
                finish();
                break;
            //手机绑定视图点击事件
            case R.id.rl_set_phone_bind:
                Utils.toast(this,"手机绑定");
                break;
            //清除缓存视图点击事件
            case R.id.rl_set_cache_clear:
                cacheDialogTipTv.setText("缓存大小为" + lruJsonCache.getCacheSize() +
                        "，是否清理缓存？");
                cacheDialog.show();
                break;
            //消息提示视图点击事件
            case R.id.rl_set_msg_tip:
                Utils.toast(this,"消息提示");
                break;
            //退出登录视图点击事件
            case R.id.rl_set_quit:
                Utils.toast(this,"退出登录");
                break;
        }
    }
}
