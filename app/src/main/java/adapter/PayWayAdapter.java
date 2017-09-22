package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.PayWayBean;

//支付方式适配器
public class PayWayAdapter extends CommonAdapter<PayWayBean> {

    public PayWayAdapter(Context context, List<PayWayBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pay_way, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PayWayBean payWayBean = list.get(position);
        if (payWayBean != null) {
            holder.iconIv.setImageResource(R.mipmap.person_face_default);
            holder.nameTv.setText(payWayBean.getP_name());
            if (payWayBean.isCheck()) {
                holder.checkIv.setImageResource(R.mipmap.pay_choosed);
            } else {
                holder.checkIv.setImageResource(R.mipmap.point_gray);
            }
        }
        return convertView;
    }

    private class ViewHolder {

        private ImageView iconIv, checkIv;
        private TextView nameTv;

        public ViewHolder(View itemView) {
            iconIv = (ImageView) itemView.findViewById(R.id.iv_item_pay_way_icon);
            checkIv = (ImageView) itemView.findViewById(R.id.iv_item_pay_way_check);
            nameTv = (TextView) itemView.findViewById(R.id.tv_item_pay_way_name);
        }
    }
}
