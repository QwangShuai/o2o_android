package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.AccountDetailBean;

/**
 * 创建日期：2017/8/9 on 10:55
 * 作者:孙明明
 * 描述:明细适配器
 */

public class DetailAdapter extends CommonAdapter<AccountDetailBean> {

    public DetailAdapter(Context context, List<AccountDetailBean> list) {
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
        AccountDetailBean accountDetailBean = list.get(position);
        if (accountDetailBean != null) {
            holer.titleTv.setText(accountDetailBean.getTitle());
            holer.balanceTv.setText(accountDetailBean.getBalance());
            holer.timeTv.setText(accountDetailBean.getTime());
            holer.desTv.setText(accountDetailBean.getDes());
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
