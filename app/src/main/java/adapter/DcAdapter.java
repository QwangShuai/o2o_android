package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import discount.bean.DiscountBean;

/**
 * 创建日期：2017/8/31 on 15:29
 * 作者:孙明明
 * 描述:优惠适配器
 */

public class DcAdapter extends CommonAdapter<DiscountBean> {

    public DcAdapter(Context context, List<DiscountBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_discount, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DiscountBean d = list.get(position);
        if (d != null) {
            holder.tv.setText(d.getTitle());
        }
        return convertView;
    }

    private class ViewHolder {

        private TextView tv;

        public ViewHolder(View itemView) {
            tv = (TextView) itemView.findViewById(R.id.tv_item_discount);
        }
    }
}
