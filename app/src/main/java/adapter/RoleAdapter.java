package adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.Role;

/**
 * 创建日期：2017/8/11 on 9:43
 * 作者:孙明明
 * 描述:角色适配器
 */

public class RoleAdapter extends BaseAdapter {

    private Context context;
    private List<Role> list;
    private ViewHolder holder;

    public RoleAdapter(Context context, List<Role> list) {
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
            convertView = View.inflate(context, R.layout.item_gridview_role, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Role role = list.get(position);
        if (role != null) {
            if (!TextUtils.isEmpty(role.getContent())) {
                holder.contentTv.setText(role.getContent());
            } else {
                holder.contentTv.setText("");
            }
        }
        return convertView;
    }

    private class ViewHolder {

        private TextView contentTv;

        public ViewHolder(View itemView) {
            contentTv = (TextView) itemView.findViewById(R.id.tv_item_gridview_role_content);
        }
    }
}
