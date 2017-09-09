package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 创建日期：2017/8/31 on 14:28
 * 作者:孙明明
 * 描述:通用碎片
 */

public abstract class CommonFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = getRootView();
        initView();
        initData();
        setData();
        setListener();
        loadData();
        return rootView;
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
