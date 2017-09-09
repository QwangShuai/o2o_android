package activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

/**
 * 创建日期：2017/8/31 on 10:31
 * 作者:孙明明
 * 描述:通用活动
 */

public abstract class CommonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getRootView());
        initView();
        initData();
        setData();
        setListener();
        loadData();
    }

    //获取根视图
    protected abstract View getRootView();

    //初始化视图
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    //绑定数据
    protected abstract void setData();

    //绑定监听
    protected abstract void setListener();

    //加载数据
    protected abstract void loadData();

}
