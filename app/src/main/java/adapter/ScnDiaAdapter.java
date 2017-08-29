package adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

/**
 * 创建日期：2017/8/17 on 10:55
 * 作者:孙明明
 * 描述:筛选对话框适配器
 */

public class ScnDiaAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private ViewHolder holder;

    public ScnDiaAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_screen_dialog, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String str = list.get(position);
        if (!TextUtils.isEmpty(str)) {
            holder.tv.setText(str);
        } else {
            holder.tv.setText("");
        }
        return convertView;
    }

    private class ViewHolder {

        private TextView tv;

        public ViewHolder(View itemView) {
            tv = (TextView) itemView.findViewById(R.id.tv_item_screen_dialog);
        }
    }
}
