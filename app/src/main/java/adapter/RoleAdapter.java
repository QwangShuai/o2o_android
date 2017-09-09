package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.RoleBean;

/**
 * 创建日期：2017/8/11 on 9:43
 * 作者:孙明明
 * 描述:角色适配器
 */

public class RoleAdapter extends BaseAdapter {

    private Context context;
    private int state;
    private List<RoleBean> list;

    public RoleAdapter(Context context, int state, List<RoleBean> list) {
        this.context = context;
        this.state = state;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_gridview_role, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RoleBean roleBean = list.get(position);
        if (roleBean != null) {
            holder.contentTv.setText(roleBean.getContent());
        }
        if (state == 0) {
            holder.delIv.setVisibility(View.GONE);
        } else if (state == 1) {
            holder.delIv.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private class ViewHolder {

        private TextView contentTv;
        private ImageView delIv;

        public ViewHolder(View itemView) {
            contentTv = (TextView) itemView.findViewById(R.id.tv_item_gridview_role_content);
            delIv = (ImageView) itemView.findViewById(R.id.iv_item_gridview_role_del);
        }
    }
}
