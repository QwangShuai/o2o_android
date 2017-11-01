package adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gjzg.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import bean.WorkerBean;
import listener.IdPosClickHelp;

public class WorkerAdapter extends BaseAdapter {

    private Context context;
    private List<WorkerBean> list;
    private IdPosClickHelp idPosClickHelp;

    public WorkerAdapter(Context context, List<WorkerBean> list, IdPosClickHelp idPosClickHelp) {
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
        WorkerBean workerBean = list.get(position);
        Picasso.with(context).load(workerBean.getIcon()).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.mipmap.person_face_default).error(R.mipmap.person_face_default).into(holder.iconIv);
        holder.titleTv.setText(workerBean.getTitle());
        holder.infoTv.setText(workerBean.getInfo());
        String status = workerBean.getStatus();
        if (status.equals("0")) {
            holder.statusIv.setImageResource(R.mipmap.worker_leisure);
        } else if (status.equals("1")) {
            holder.statusIv.setImageResource(R.mipmap.worker_mid);
        }
        int favorite = workerBean.getFavorite();
        switch (favorite) {
            case 0:
                holder.collectIv.setImageResource(R.mipmap.collect_gray);
                break;
            case 1:
                holder.collectIv.setImageResource(R.mipmap.collect_yellow);
                break;
        }
        final int p = position;
        final int llId = holder.ll.getId();
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idPosClickHelp.onClick(llId, p);
            }
        });
        final int collectId = holder.collectIv.getId();
        holder.collectIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idPosClickHelp.onClick(collectId, p);
            }
        });
        return convertView;
    }

    private class ViewHolder {

        private LinearLayout ll;
        private ImageView iconIv, statusIv, collectIv;
        private TextView titleTv, infoTv;

        public ViewHolder(View itemView) {
            ll = (LinearLayout) itemView.findViewById(R.id.ll_item_worker);
            iconIv = (ImageView) itemView.findViewById(R.id.iv_item_worker_icon);
            statusIv = (ImageView) itemView.findViewById(R.id.iv_item_worker_status);
            collectIv = (ImageView) itemView.findViewById(R.id.iv_item_worker_collect);
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_worker_title);
            infoTv = (TextView) itemView.findViewById(R.id.tv_item_worker_info);
        }
    }
}
