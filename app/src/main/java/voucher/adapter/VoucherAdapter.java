package voucher.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import listener.ListItemClickHelp;
import voucher.bean.VoucherBean;

public class VoucherAdapter extends BaseAdapter {

    private Context context;
    private List<VoucherBean> list;
    private ListItemClickHelp listItemClickHelp;

    public VoucherAdapter(Context context, List<VoucherBean> list, ListItemClickHelp listItemClickHelp) {
        this.context = context;
        this.list = list;
        this.listItemClickHelp = listItemClickHelp;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_red_packet, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VoucherBean voucherBean = list.get(position);
        if (voucherBean != null) {
            holder.amountTv.setText(voucherBean.getAmount() + "元");
            holder.infoTv.setText(voucherBean.getInfo());
            holder.startTimeTv.setText(voucherBean.getStartTime());
            holder.endTimeTv.setText(voucherBean.getEndTime());
            final View view = convertView;
            final int p = position;
            final int id = holder.statusTv.getId();
            holder.statusTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listItemClickHelp.onClick(view, parent, p, id, false);
                }
            });
        }
        return convertView;
    }

    private class ViewHolder {

        private TextView amountTv, infoTv, startTimeTv, endTimeTv, statusTv;

        public ViewHolder(View itemView) {
            amountTv = (TextView) itemView.findViewById(R.id.tv_item_red_packet_amount);
            infoTv = (TextView) itemView.findViewById(R.id.tv_item_red_packet_info);
            startTimeTv = (TextView) itemView.findViewById(R.id.tv_item_red_packet_start_time);
            endTimeTv = (TextView) itemView.findViewById(R.id.tv_item_red_packet_end_time);
            statusTv = (TextView) itemView.findViewById(R.id.tv_item_red_packet_status);
        }
    }
}
