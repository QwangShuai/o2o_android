package taskconfirm.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import taskconfirm.bean.SelectVoucherBean;

public class SelectVoucherAdapter extends BaseAdapter {

    private Context context;
    private List<SelectVoucherBean> list;

    public SelectVoucherAdapter(Context context, List<SelectVoucherBean> list) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_select_voucher, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SelectVoucherBean selectVoucherBean = list.get(position);
        if (selectVoucherBean != null) {
            holder.titleTv.setText(selectVoucherBean.getTitle());
            holder.descriptionTv.setText(selectVoucherBean.getDescription());
            holder.durationTv.setText("有效期" + selectVoucherBean.getStartTime() + "-" + selectVoucherBean.getEndTime());
        }
        return convertView;
    }

    private class ViewHolder {

        private TextView titleTv, descriptionTv, durationTv;

        public ViewHolder(View itemView) {
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_select_voucher_title);
            descriptionTv = (TextView) itemView.findViewById(R.id.tv_item_select_voucher_description);
            durationTv = (TextView) itemView.findViewById(R.id.tv_item_select_voucher_duration);
        }
    }
}
