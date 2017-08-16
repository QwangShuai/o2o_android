package adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.Discount;

/**
 * 创建日期：2017/8/15 on 14:22
 * 作者:孙明明
 * 描述:
 */

public class DiscountAdapter extends BaseAdapter {

    private Context context;
    private List<Discount> list;
    private ViewHolder holder;

    public DiscountAdapter(Context context, List<Discount> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_discount, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Discount discount = list.get(position);
        if (discount != null) {
            if (!TextUtils.isEmpty(discount.getTitle())) {
                holder.titleTv.setText(discount.getTitle());
            } else {
                holder.titleTv.setText("");
            }
        }
        return convertView;
    }

    private class ViewHolder {

        private TextView titleTv;

        public ViewHolder(View itemView) {
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_discount);
        }
    }
}
