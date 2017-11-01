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
import com.squareup.picasso.Picasso;

import java.util.List;

import bean.TaskBean;
import listener.IdPosClickHelp;
import view.CImageView;

/**
 * Created by Administrator on 2017/10/25.
 */

public class CollectTaskAdapter extends BaseAdapter {

    private Context context;
    private List<TaskBean> list;
    private IdPosClickHelp idPosClickHelp;

    public CollectTaskAdapter(Context context, List<TaskBean> list, IdPosClickHelp idPosClickHelp) {
        this.context = context;
        this.list = list;
        this.idPosClickHelp = idPosClickHelp;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TaskBean taskBean = list.get(position);
        holder.titleTv.setText(taskBean.getTitle());
        holder.infoTv.setText(taskBean.getInfo());
        Picasso.with(context).load(taskBean.getIcon()).into(holder.iconIv);
        String status = taskBean.getStatus();
        if (status.equals("0")) {
            holder.statusIv.setImageResource(R.mipmap.worker_wait);
        } else if (status.equals("1")) {
            holder.statusIv.setImageResource(R.mipmap.worker_talk);
        } else if (status.equals("2")) {
            holder.statusIv.setImageResource(R.mipmap.worker_mid);
        } else if (status.equals("3")) {
            holder.statusIv.setImageResource(R.mipmap.worker_over);
        }
        holder.collectIv.setImageResource(R.mipmap.collect_yellow);
        final int p = position;
        final int llId = holder.ll.getId();
        final int collectId = holder.collectIv.getId();
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idPosClickHelp.onClick(llId, p);
            }
        });
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
        private CImageView iconIv;
        private ImageView statusIv, collectIv;
        private TextView titleTv, infoTv;

        public ViewHolder(View itemView) {
            ll = (LinearLayout) itemView.findViewById(R.id.ll_item_task);
            iconIv = (CImageView) itemView.findViewById(R.id.iv_item_task_icon);
            statusIv = (ImageView) itemView.findViewById(R.id.iv_item_task_status);
            collectIv = (ImageView) itemView.findViewById(R.id.iv_item_task_collect);
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_task_name);
            infoTv = (TextView) itemView.findViewById(R.id.tv_item_task_info);
        }
    }
}
