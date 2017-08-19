package adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.Screen;
import listener.ListItemClickHelp;

/**
 * 创建日期：2017/8/17 on 11:32
 * 作者:孙明明
 * 描述:筛选历史适配器
 */

public class ScreenHistoryAdapter extends BaseAdapter {

    private Context context;
    private List<Screen> list;
    private ListItemClickHelp clickHelp;
    private ViewHolder holder;

    public ScreenHistoryAdapter(Context context, List<Screen> list, ListItemClickHelp clickHelp) {
        this.context = context;
        this.list = list;
        this.clickHelp = clickHelp;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_screen_history, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Screen screen = list.get(position);
        if (screen != null) {
            if (!TextUtils.isEmpty(screen.getKind())) {
                holder.kindTv.setText(screen.getKind());
            } else {
                holder.kindTv.setText("");
            }
            if (!TextUtils.isEmpty(screen.getDis())) {
                holder.disTv.setText(screen.getDis());
            } else {
                holder.disTv.setText("");
            }
            switch (screen.getState()) {
                case 0:
                    holder.stateTv.setText("空闲");
                    break;
                case 1:
                    holder.stateTv.setText("洽谈中");
                    break;
                case 2:
                    holder.stateTv.setText("工作中");
                    break;
            }
            final View view = convertView;
            final int p = position;
            final int id = holder.delIv.getId();
            holder.delIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickHelp.onClick(view, parent, p, id, false);
                }
            });
        }
        return convertView;
    }

    private class ViewHolder {

        private TextView kindTv, disTv, stateTv;
        private ImageView delIv;

        public ViewHolder(View itemView) {
            kindTv = (TextView) itemView.findViewById(R.id.tv_item_screen_history_kind);
            disTv = (TextView) itemView.findViewById(R.id.tv_item_screen_history_dis);
            stateTv = (TextView) itemView.findViewById(R.id.tv_item_screen_history_state);
            delIv = (ImageView) itemView.findViewById(R.id.iv_item_screen_history_del);
        }
    }
}
