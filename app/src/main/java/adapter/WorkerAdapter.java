package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.WorkerBean;
import utils.Utils;

//工人适配器
public class WorkerAdapter extends CommonAdapter<WorkerBean> {

    public WorkerAdapter(Context context, List<WorkerBean> list) {
        super(context, list);
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
        if (workerBean == null) {
            Utils.log(context, "workerBean = null");
        } else {
            holder.iconIv.setImageResource(R.mipmap.person_face_default);
            holder.statusIv.setImageResource(R.mipmap.worker_leisure);
            holder.collectIv.setImageResource(R.mipmap.collect_gray);
            holder.nameTv.setText(workerBean.getName());
            holder.briefTv.setText(workerBean.getBrief());
            holder.distanceTv.setText(workerBean.getDistance());
        }
        return convertView;
    }

    private class ViewHolder {

        private ImageView iconIv, statusIv, collectIv;
        private TextView nameTv, briefTv, distanceTv;

        public ViewHolder(View itemView) {
            iconIv = (ImageView) itemView.findViewById(R.id.iv_item_worker_icon);
            statusIv = (ImageView) itemView.findViewById(R.id.iv_item_worker_status);
            collectIv = (ImageView) itemView.findViewById(R.id.iv_item_worker_collect);
            nameTv = (TextView) itemView.findViewById(R.id.tv_item_worker_name);
            briefTv = (TextView) itemView.findViewById(R.id.tv_item_worker_brief);
            distanceTv = (TextView) itemView.findViewById(R.id.tv_item_worker_distance);
        }
    }
}
