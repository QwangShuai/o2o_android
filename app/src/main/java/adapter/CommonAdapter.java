package adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 创建日期：2017/8/30 on 17:09
 * 作者:孙明明
 * 描述:通用适配器
 */

public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> list;

    public CommonAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
