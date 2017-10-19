package adapter;


import android.content.Context;
import android.text.TextUtils;
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

import bean.PersonBean;
import config.StateConfig;
import listener.ListItemClickHelp;

public class WorkerMagAdapter extends BaseAdapter {

    private Context context;
    private List<PersonBean> list;
    private ListItemClickHelp clickHelp;

    public WorkerMagAdapter(Context context, List<PersonBean> list, ListItemClickHelp clickHelp) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_worker_mag, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PersonBean personBean = list.get(position);
        if (TextUtils.isEmpty(personBean.getImage())) {
            holder.iconIv.setImageResource(R.mipmap.person_face_default);
        } else {
            Picasso.with(context).load(personBean.getImage()).placeholder(holder.iconIv.getDrawable()).into(holder.iconIv);
        }
        switch (personBean.getState()) {
            case StateConfig.WAIT:
                holder.stateIv.setImageResource(R.mipmap.worker_wait);
                holder.waitLl.setVisibility(View.VISIBLE);
                holder.talkingLl.setVisibility(View.GONE);
                holder.workingLl.setVisibility(View.GONE);
                holder.overLl.setVisibility(View.GONE);
                break;
            case StateConfig.TALKING:
                holder.stateIv.setImageResource(R.mipmap.worker_talk);
                holder.waitLl.setVisibility(View.GONE);
                holder.talkingLl.setVisibility(View.VISIBLE);
                holder.workingLl.setVisibility(View.GONE);
                holder.overLl.setVisibility(View.GONE);
                break;
            case StateConfig.WORKING:
                holder.stateIv.setImageResource(R.mipmap.worker_mid);
                holder.waitLl.setVisibility(View.GONE);
                holder.talkingLl.setVisibility(View.GONE);
                holder.workingLl.setVisibility(View.VISIBLE);
                holder.overLl.setVisibility(View.GONE);
                break;
            case StateConfig.OVER:
                holder.stateIv.setImageResource(R.mipmap.worker_over);
                holder.waitLl.setVisibility(View.GONE);
                holder.talkingLl.setVisibility(View.GONE);
                holder.workingLl.setVisibility(View.GONE);
                holder.overLl.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        if (personBean.isCollect()) {
            holder.collectIv.setImageResource(R.mipmap.collect_yellow);
        } else {
            holder.collectIv.setImageResource(R.mipmap.collect_gray);
        }
        holder.titleTv.setText(personBean.getName());
        holder.contentTv.setText(personBean.getPlay());
        holder.priceTv.setText(personBean.getShow());
        holder.distanceTv.setText(personBean.getDistance());
        final View view = convertView;
        final int p = position;
        final int cancelId = holder.cancelTv.getId();
        final int changeId = holder.changeTv.getId();
        final int quitId = holder.quitTv.getId();
        final int refuseId = holder.refuseTv.getId();
        final int complainId = holder.complainTv.getId();
        final int deleteId = holder.deleteTv.getId();
        final int evaluateId = holder.evaluateTv.getId();
        holder.cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHelp.onClick(view, parent, p, cancelId, false);
            }
        });
        holder.changeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHelp.onClick(view, parent, p, changeId, false);
            }
        });
        holder.quitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHelp.onClick(view, parent, p, quitId, false);
            }
        });
        holder.refuseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHelp.onClick(view, parent, p, refuseId, false);
            }
        });
        holder.complainTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHelp.onClick(view, parent, p, complainId, false);
            }
        });
        holder.deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHelp.onClick(view, parent, p, deleteId, false);
            }
        });
        holder.evaluateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHelp.onClick(view, parent, p, evaluateId, false);
            }
        });
        return convertView;
    }

    private class ViewHolder {

        private ImageView iconIv, stateIv, collectIv;
        private TextView titleTv, contentTv, priceTv, distanceTv;
        private LinearLayout waitLl, talkingLl, workingLl, overLl;
        private TextView cancelTv, changeTv, quitTv, refuseTv, complainTv, deleteTv, evaluateTv;

        public ViewHolder(View itemView) {
            iconIv = (ImageView) itemView.findViewById(R.id.iv_item_person_img);
            stateIv = (ImageView) itemView.findViewById(R.id.iv_item_person_status);
            collectIv = (ImageView) itemView.findViewById(R.id.iv_item_person_collect);
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_person_name);
            contentTv = (TextView) itemView.findViewById(R.id.tv_item_person_info);
            priceTv = (TextView) itemView.findViewById(R.id.tv_item_worker_mag_show);
            distanceTv = (TextView) itemView.findViewById(R.id.tv_item_person_distance);
            waitLl = (LinearLayout) itemView.findViewById(R.id.ll_item_worker_mag_wait);
            talkingLl = (LinearLayout) itemView.findViewById(R.id.ll_item_worker_mag_talking);
            workingLl = (LinearLayout) itemView.findViewById(R.id.ll_item_worker_mag_working);
            overLl = (LinearLayout) itemView.findViewById(R.id.ll_item_worker_mag_over);
            cancelTv = (TextView) itemView.findViewById(R.id.tv_item_worker_mag_cancel);
            changeTv = (TextView) itemView.findViewById(R.id.tv_item_worker_mag_change);
            quitTv = (TextView) itemView.findViewById(R.id.tv_item_worker_mag_quit);
            refuseTv = (TextView) itemView.findViewById(R.id.tv_item_worker_mag_refuse);
            complainTv = (TextView) itemView.findViewById(R.id.tv_item_worker_mag_complain);
            deleteTv = (TextView) itemView.findViewById(R.id.tv_item_worker_mag_delete);
            evaluateTv = (TextView) itemView.findViewById(R.id.tv_item_worker_mag_evaluate);
        }
    }
}
