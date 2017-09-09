package fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gjzg.R;

/**
 * 创建日期：2017/7/28 on 13:53
 * 作者:孙明明
 * 描述:优惠信息
 */

public class DiscountInfoFragment extends CommonFragment {

    private View rootView;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_discount_info,null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void loadData() {

    }

}
