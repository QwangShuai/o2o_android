package adapter;

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

import bean.TalkEmployerBean;
import bean.TalkEmployerWorkerBean;
import view.CImageView;

/**
 * Created by Administrator on 2017/10/26.
 */

public class TalkEmployerAdapter extends BaseAdapter {

    private Context context;
    private List<TalkEmployerWorkerBean> list;

    public TalkEmployerAdapter(Context context, List<TalkEmployerWorkerBean> list) {
        this.context = context;
        this.list = list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_talk_employer, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TalkEmployerWorkerBean talkEmployerWorkerBean = list.get(position);
        holder.skillTv.setText("招" + talkEmployerWorkerBean.getSkill() + talkEmployerWorkerBean.getAmount() + "人");
        holder.priceTv.setText("工资" + talkEmployerWorkerBean.getPrice() + "元/天");
        holder.timeTv.setText(talkEmployerWorkerBean.getStartTime() + "-" + talkEmployerWorkerBean.getEndTime());
        return convertView;
    }

    private class ViewHolder {

        private TextView skillTv, priceTv, timeTv;

        public ViewHolder(View itemView) {
            skillTv = (TextView) itemView.findViewById(R.id.tv_item_talk_employer_skill);
            priceTv = (TextView) itemView.findViewById(R.id.tv_item_talk_employer_price);
            timeTv = (TextView) itemView.findViewById(R.id.tv_item_talk_employer_time);
        }
    }
}
