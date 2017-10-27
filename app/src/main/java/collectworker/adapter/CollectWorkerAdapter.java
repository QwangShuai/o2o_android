package collectworker.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gjzg.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import collectworker.bean.CollectWorkerBean;
import listener.IdPosClickHelp;

public class CollectWorkerAdapter extends BaseAdapter {

    private Context context;
    private List<CollectWorkerBean> list;
    private IdPosClickHelp idPosClickHelp;

    public CollectWorkerAdapter(Context context, List<CollectWorkerBean> list, IdPosClickHelp idPosClickHelp) {
        this.context = context;
        this.list = list;
        this.idPosClickHelp = idPosClickHelp;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_worker, null);
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
        }
        final int p = position;
        final int llId = holder.ll.getId();
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idPosClickHelp.onClick(p, llId);
            }
        });
        final int cancelCollectId = holder.collectIv.getId();
        holder.collectIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idPosClickHelp.onClick(p, cancelCollectId);
            }
        });
        return convertView;
    }

    private class ViewHolder {

        private LinearLayout ll;
        private ImageView imageIv, statusIv, collectIv;
        private TextView nameTv, infoTv;

        public ViewHolder(View itemView) {
            ll = (LinearLayout) itemView.findViewById(R.id.ll_item_worker);
            imageIv = (ImageView) itemView.findViewById(R.id.iv_item_worker_icon);
            statusIv = (ImageView) itemView.findViewById(R.id.iv_item_worker_status);
            collectIv = (ImageView) itemView.findViewById(R.id.iv_item_worker_collect);
            nameTv = (TextView) itemView.findViewById(R.id.tv_item_worker_title);
            infoTv = (TextView) itemView.findViewById(R.id.tv_item_worker_info);
        }
    }
}
