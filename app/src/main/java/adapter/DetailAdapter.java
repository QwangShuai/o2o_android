package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.DetailBean;

/**
 * 创建日期：2017/8/9 on 10:55
 * 作者:孙明明
 * 描述:明细适配器
 */

public class DetailAdapter extends CommonAdapter<DetailBean> {

    public DetailAdapter(Context context, List<DetailBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler holer;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_detail, null);
            holer = new ViewHoler(convertView);
            convertView.setTag(holer);
        } else {
            holer = (ViewHoler) convertView.getTag();
        }
        DetailBean detailBean = list.get(position);
        if (detailBean != null) {
            holer.titleTv.setText(detailBean.getTitle());
            holer.balanceTv.setText(detailBean.getBalance());
            holer.timeTv.setText(detailBean.getTime());
            holer.desTv.setText(detailBean.getDes());
        }
        return convertView;
    }

    private class ViewHoler {

        private TextView titleTv, balanceTv, timeTv, desTv;

        public ViewHoler(View itemView) {
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_detail_title);
            balanceTv = (TextView) itemView.findViewById(R.id.tv_item_detail_balance);
            timeTv = (TextView) itemView.findViewById(R.id.tv_item_detail_time);
            desTv = (TextView) itemView.findViewById(R.id.tv_item_detail_des);
        }
    }
}
