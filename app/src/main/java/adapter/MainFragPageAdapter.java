package adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import view.CFragment;

/**
 * 创建日期：2017/7/28 on 13:49
 * 作者:孙明明
 * 描述:主页碎片适配器
 */

public class MainFragPageAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<CFragment> cFragmentList;

    public MainFragPageAdapter(FragmentManager fm, Context context, List<CFragment> cFragmentList) {
        super(fm);
        this.context = context;
        this.cFragmentList = cFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return cFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return cFragmentList.size();
    }
}
