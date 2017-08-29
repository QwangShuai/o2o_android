package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.SysMsg;

/**
 * 创建日期：2017/8/22 on 15:52
 * 作者:孙明明
 * 描述:系统消息适配器
 */

public class SysMsgAdapter extends BaseAdapter {

    private Context context;
    private List<SysMsg> list;
    private ViewHolder holder;

    public SysMsgAdapter(Context context, List<SysMsg> list) {
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_sys_msg, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SysMsg sysMsg = list.get(position);
        if (sysMsg != null) {
            holder.titleTv.setText(sysMsg.getTitle());
            holder.dateTv.setText(sysMsg.getDate());
            holder.desTv.setText(sysMsg.getDes());
            if (sysMsg.isArrowShow()) {
                holder.arrowIv.setVisibility(View.VISIBLE);
            } else {
                holder.arrowIv.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    private class ViewHolder {

        private TextView titleTv, dateTv, desTv;
        private ImageView arrowIv;

        public ViewHolder(View itemView) {
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_message_offer_title);
            dateTv = (TextView) itemView.findViewById(R.id.tv_item_message_offer_date);
            desTv = (TextView) itemView.findViewById(R.id.tv_item_message_offer_des);
            arrowIv = (ImageView) itemView.findViewById(R.id.iv_item_system_message_arrow);
        }
    }
}
