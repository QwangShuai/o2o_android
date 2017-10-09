package city.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import adapter.CommonAdapter;
import city.bean.CityBean;
import config.ColorConfig;

public class CityAdapter extends CommonAdapter<CityBean> {

    public CityAdapter(Context context, List<CityBean> list) {
        super(context, list);
    }

    @Override
    public boolean isEnabled(int position) {
        if (list.get(position).getId().equals("-1")) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CityBean cityBean = list.get(position);
        if (cityBean != null) {
            if (position == 0) {
                holder.tv.setText("当前定位城市：" + cityBean.getName());
            } else {
                holder.tv.setText(cityBean.getName());
            }
            if (cityBean.getId().equals("-1")) {
                holder.rl.setBackgroundColor(ColorConfig.gray_c4ced3);
                holder.tv.setTextColor(ColorConfig.gray_a0a0a0);
            } else {
                holder.rl.setBackgroundColor(ColorConfig.white_ffffff);
                holder.tv.setTextColor(ColorConfig.black_252323);
            }
        }
        return convertView;
    }

    private class ViewHolder {

        private RelativeLayout rl;
        private TextView tv;

        public ViewHolder(View itemView) {
            rl = (RelativeLayout) itemView.findViewById(R.id.rl_item_city);
            tv = (TextView) itemView.findViewById(R.id.tv_item_city);
        }
    }
}
