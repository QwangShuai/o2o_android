package set.view;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import aboutus.view.AboutUsActivity;
import cache.LruJsonCache;
import set.presenter.ISetPresenter;
import set.presenter.SetPresenter;
import utils.UserUtils;
import utils.Utils;

public class SetActivity extends AppCompatActivity implements ISetActivity, View.OnClickListener {

    private View rootView, cachePopView;
    private RelativeLayout returnRl, clearCacheRl, aboutUsRl, quitRl;
    private PopupWindow cachePop;
    private LruJsonCache lruJsonCache;
    private ISetPresenter setPresenter;

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
        initPopView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_set_return);
        clearCacheRl = (RelativeLayout) rootView.findViewById(R.id.rl_set_cache_clear);
        aboutUsRl = (RelativeLayout) rootView.findViewById(R.id.rl_set_about_us);
        quitRl = (RelativeLayout) rootView.findViewById(R.id.rl_set_quit);
    }

    private void initPopView() {
        cachePopView = LayoutInflater.from(SetActivity.this).inflate(R.layout.pop_dialog_0, null);
        ((TextView) cachePopView.findViewById(R.id.tv_pop_dialog_0_cancel)).setText("取消");
        ((TextView) cachePopView.findViewById(R.id.tv_pop_dialog_0_sure)).setText("清理");
        (cachePopView.findViewById(R.id.rl_pop_dialog_0_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cachePop.isShowing()) {
                    cachePop.dismiss();
                }
            }
        });
        (cachePopView.findViewById(R.id.rl_pop_dialog_0_sure)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cachePop.isShowing()) {
                    cachePop.dismiss();
                    lruJsonCache.clear();
                    Utils.toast(SetActivity.this, "缓存已清理");
                }
            }
        });
        cachePop = new PopupWindow(cachePopView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        cachePop.setFocusable(true);
        cachePop.setTouchable(true);
        cachePop.setOutsideTouchable(true);
        cachePop.setBackgroundDrawable(new BitmapDrawable());
        cachePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    private void initData() {
        lruJsonCache = LruJsonCache.get(this);
        setPresenter = new SetPresenter(this);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        clearCacheRl.setOnClickListener(this);
        aboutUsRl.setOnClickListener(this);
        quitRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_set_return:
                finish();
                break;
            case R.id.rl_set_cache_clear:
                if (!cachePop.isShowing()) {
                    ((TextView) cachePopView.findViewById(R.id.tv_pop_dialog_0_content)).setText("缓存大小为" + lruJsonCache.getCacheSize() + "，是否清理缓存？");
                    cachePop.showAtLocation(rootView, Gravity.CENTER, 0, 0);
                    backgroundAlpha(0.5f);
                }
                break;
            case R.id.rl_set_about_us:
                startActivity(new Intent(SetActivity.this, AboutUsActivity.class));
                break;
            case R.id.rl_set_quit:
                setPresenter.quit(UserUtils.readUserData(SetActivity.this).getId());
                break;
        }
    }

    private void backgroundAlpha(float f) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = f;
        getWindow().setAttributes(layoutParams);
    }

    @Override
    public void showQuitSuccess(String success) {
        Log.e("showQuitSuccess", success);
        UserUtils.clearUserData(SetActivity.this);
        finish();
    }

    @Override
    public void showQuitFailure(String failure) {
        Log.e("showQuitFailure", failure);
    }
}
