package adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.ManageEmployer;
import listener.ListItemClickHelp;

/**
 * 创建日期：2017/8/8 on 16:30
 * 作者:孙明明
 * 描述:雇主发布管理适配器
 */

public class ManageEmployerAdapter extends BaseAdapter {

    private Context context;
    private List<ManageEmployer> list;
    private ListItemClickHelp clickHelp;
    private ViewHolder holder;

    public ManageEmployerAdapter(Context context, List<ManageEmployer> list, ListItemClickHelp clickHelp) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_manage_employer, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ManageEmployer manageEmployer = list.get(position);
        if (manageEmployer != null) {
            if (TextUtils.isEmpty(manageEmployer.getIcon())) {
                holder.iconIv.setImageResource(R.mipmap.person_face_default);
            }
            switch (manageEmployer.getState()) {
                case 0:
                    holder.stateIv.setImageResource(R.mipmap.worker_leisure);
                    break;
                case 1:
                    holder.stateIv.setImageResource(R.mipmap.worker_wait);
                    break;
                case 2:
                    holder.stateIv.setImageResource(R.mipmap.worker_talk);
                    break;
                case 3:
                    holder.stateIv.setImageResource(R.mipmap.worker_start);
                    break;
                case 4:
                    holder.stateIv.setImageResource(R.mipmap.worker_mid);
                    break;
                case 5:
                    holder.stateIv.setImageResource(R.mipmap.worker_over);
                    break;
            }
            if (manageEmployer.isCollect()) {
                holder.collectIv.setImageResource(R.mipmap.collect_yellow);
            } else {
                holder.collectIv.setImageResource(R.mipmap.collect_gray);
            }
            holder.titleTv.setText(manageEmployer.getTitle());
            holder.descTv.setText(manageEmployer.getDesc());
            holder.priceTv.setText(manageEmployer.getPrice());
            holder.disTv.setText(manageEmployer.getDis());
        }
        return convertView;
    }

    private class ViewHolder {

        private ImageView iconIv, stateIv, collectIv;
        private TextView titleTv, descTv, priceTv, disTv;
        private LinearLayout optionLl;

        public ViewHolder(View itemView) {
            iconIv = (ImageView) itemView.findViewById(R.id.iv_item_manage_employer_icon);
            stateIv = (ImageView) itemView.findViewById(R.id.iv_item_manage_employer_state);
            collectIv = (ImageView) itemView.findViewById(R.id.iv_item_manage_employer_collect);
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_manage_employer_title);
            descTv = (TextView) itemView.findViewById(R.id.tv_item_manage_employer_desc);
            priceTv = (TextView) itemView.findViewById(R.id.tv_item_manage_employer_price);
            disTv = (TextView) itemView.findViewById(R.id.tv_item_manage_employer_dis);
            optionLl = (LinearLayout) itemView.findViewById(R.id.ll_item_manage_option);
        }
    }
}
