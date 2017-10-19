package collectworker.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjzg.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import collectworker.bean.CollectWorkerBean;
import listener.ListItemClickHelp;

public class CollectWorkerAdapter extends BaseAdapter {

    private Context context;
    private List<CollectWorkerBean> list;
    private ListItemClickHelp clickHelp;

    public CollectWorkerAdapter(Context context, List<CollectWorkerBean> list, ListItemClickHelp clickHelp) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_person, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CollectWorkerBean collectWorkerBean = list.get(position);
        if (collectWorkerBean != null) {
            Picasso.with(context).load(collectWorkerBean.getuImg()).into(holder.imageIv);
            if ("0".equals(collectWorkerBean.getuTaskStatus())) {
                holder.statusIv.setImageResource(R.mipmap.worker_leisure);
            }
            if ("1".equals(collectWorkerBean.getuTaskStatus())) {
                holder.statusIv.setImageResource(R.mipmap.worker_mid);
            }
            holder.collectIv.setImageResource(R.mipmap.collect_yellow);
            holder.nameTv.setText(collectWorkerBean.getuName());
            holder.infoTv.setText(collectWorkerBean.getUeiInfo());
            holder.distanceTv.setText("?");
        }
        return convertView;
    }

    private class ViewHolder {

        private ImageView imageIv, statusIv, collectIv;
        private TextView nameTv, infoTv, distanceTv;

        public ViewHolder(View itemView) {
            imageIv = (ImageView) itemView.findViewById(R.id.iv_item_person_image);
            statusIv = (ImageView) itemView.findViewById(R.id.iv_item_person_status);
            collectIv = (ImageView) itemView.findViewById(R.id.iv_item_person_collect);
            nameTv = (TextView) itemView.findViewById(R.id.tv_item_person_name);
            infoTv = (TextView) itemView.findViewById(R.id.tv_item_person_info);
            distanceTv = (TextView) itemView.findViewById(R.id.tv_item_person_distance);
        }
    }
}
