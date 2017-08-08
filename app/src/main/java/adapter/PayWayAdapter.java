package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.PayWay;
import listener.ListItemClickHelp;

/**
 * 创建日期：2017/8/7 on 14:18
 * 作者:孙明明
 * 描述:
 */

public class PayWayAdapter extends BaseAdapter {

    private Context context;
    private List<PayWay> list;
    private ListItemClickHelp clickHelp;
    private ViewHolder holder;

    public PayWayAdapter(Context context, List<PayWay> list, ListItemClickHelp clickHelp) {
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
            convertView = View.inflate(context, R.layout.item_pay_way, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PayWay payWay = list.get(position);
        final View view = convertView;
        final int p = position;
        final int id = holder.yesOrnoCb.getId();
        holder.yesOrnoCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clickHelp.onClick(view, parent, p, id, isChecked);
            }
        });
        if (payWay != null) {
            holder.imageIv.setImageResource(payWay.getImageResource());
            holder.contentTv.setText(payWay.getContent());
            holder.yesOrnoCb.setChecked(payWay.isYesOrno());
        }
        return convertView;
    }

    private class ViewHolder {

        private ImageView imageIv;
        private TextView contentTv;
        private CheckBox yesOrnoCb;

        public ViewHolder(View itemView) {
            imageIv = (ImageView) itemView.findViewById(R.id.iv_item_pay_way_image);
            contentTv = (TextView) itemView.findViewById(R.id.tv_item_pay_way_content);
            yesOrnoCb = (CheckBox) itemView.findViewById(R.id.cb_item_pay_way_yes_or_no);
        }
    }
}
