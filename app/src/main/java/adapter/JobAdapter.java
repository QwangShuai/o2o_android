package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.JobBean;

//工作适配器
public class JobAdapter extends CommonAdapter<JobBean> {

    public JobAdapter(Context context, List<JobBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_job, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JobBean jobBean = list.get(position);
        if (jobBean != null) {
            holder.titleTv.setText(jobBean.getT_title());
            holder.infoTv.setText(jobBean.getT_info());
            String status = jobBean.getT_status();
            if (status.equals("0")) {
                holder.statusIv.setImageResource(R.mipmap.worker_wait);
            } else if (status.equals("1")) {
                holder.statusIv.setImageResource(R.mipmap.worker_talk);
            } else if (status.equals("2")) {
                holder.statusIv.setImageResource(R.mipmap.worker_mid);
            } else if (status.equals("3")) {
                holder.statusIv.setImageResource(R.mipmap.worker_over);
            }
        }
        return convertView;
    }

    private class ViewHolder {

        private TextView titleTv, infoTv;
        private ImageView statusIv;

        public ViewHolder(View itemView) {
            statusIv = (ImageView) itemView.findViewById(R.id.iv_item_job_status);
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_job_title);
            infoTv = (TextView) itemView.findViewById(R.id.tv_item_job_info);
        }
    }
}
