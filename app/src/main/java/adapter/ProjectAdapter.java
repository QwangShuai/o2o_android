package adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjzg.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import bean.ProjectBean;
import config.StateConfig;
import listener.ListItemClickHelp;

public class ProjectAdapter extends BaseAdapter {

    private Context context;
    private List<ProjectBean> list;
    private ListItemClickHelp clickHelp;

    public ProjectAdapter(Context context, List<ProjectBean> list, ListItemClickHelp clickHelp) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_project, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ProjectBean projectBean = list.get(position);
        if (TextUtils.isEmpty(projectBean.getIcon())) {
            holder.iconIv.setImageResource(R.mipmap.person_face_default);
        } else {
            Picasso.with(context).load(projectBean.getIcon()).placeholder(holder.iconIv.getDrawable()).into(holder.iconIv);
        }
        switch (projectBean.getState()) {
            case StateConfig.LEISURE:
                holder.stateIv.setImageResource(R.mipmap.worker_leisure);
                break;
            case StateConfig.WAIT:
                holder.stateIv.setImageResource(R.mipmap.worker_wait);
                break;
            case StateConfig.TALKING:
                holder.stateIv.setImageResource(R.mipmap.worker_talk);
                break;
            case StateConfig.WORKING:
                holder.stateIv.setImageResource(R.mipmap.worker_mid);
                break;
            case StateConfig.OVER:
                holder.stateIv.setImageResource(R.mipmap.worker_over);
                break;
        }
        holder.titleTv.setText(projectBean.getTitle());
        holder.contentTv.setText(projectBean.getContent());
        holder.priceTv.setText(projectBean.getPrice());
        holder.addressTv.setText(projectBean.getAddress());
        final View view = convertView;
        final int p = position;
        final int id = holder.chooseTv.getId();
        holder.chooseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHelp.onClick(view, parent, p, id, false);
            }
        });
        return convertView;
    }

    private class ViewHolder {

        private ImageView iconIv, stateIv;
        private TextView titleTv, contentTv, priceTv, addressTv;
        private TextView chooseTv;

        public ViewHolder(View itemView) {
            iconIv = (ImageView) itemView.findViewById(R.id.iv_item_project_icon);
            stateIv = (ImageView) itemView.findViewById(R.id.iv_item_project_state);
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_project_title);
            contentTv = (TextView) itemView.findViewById(R.id.tv_item_project_content);
            priceTv = (TextView) itemView.findViewById(R.id.tv_item_project_price);
            addressTv = (TextView) itemView.findViewById(R.id.tv_item_project_address);
            chooseTv = (TextView) itemView.findViewById(R.id.tv_item_project_choose);
        }
    }
}
