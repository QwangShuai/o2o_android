package workerkind.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import adapter.CommonAdapter;
import workerkind.bean.WorkerKindBean;
import utils.Utils;

public class WorkerKindAdapter extends CommonAdapter<WorkerKindBean> {

    public WorkerKindAdapter(Context context, List<WorkerKindBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_worker_kind, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        WorkerKindBean workerKindBean = list.get(position);
        if (workerKindBean == null) {
            Utils.log(context, "workerKindBean == null");
        } else {
            holder.iconIv.setImageResource(R.mipmap.person_face_default);
            holder.nameTv.setText(workerKindBean.getName());
        }
        return convertView;
    }

    private class ViewHolder {

        private ImageView iconIv;
        private TextView nameTv;

        public ViewHolder(View itemView) {
            iconIv = (ImageView) itemView.findViewById(R.id.iv_item_worker_kind_icon);
            nameTv = (TextView) itemView.findViewById(R.id.tv_item_worker_kind_name);
        }
    }
}
