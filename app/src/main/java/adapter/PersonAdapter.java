package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.PersonBean;
import config.StateConfig;

/**
 * 创建日期：2017/8/2 on 10:39
 * 作者:孙明明
 * 描述:工人/雇主适配器
 */

public class PersonAdapter extends CommonAdapter<PersonBean> {

    public PersonAdapter(Context context, List<PersonBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_worker, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PersonBean personBean = list.get(position);
        if (personBean != null) {
            holder.imageIv.setImageResource(R.mipmap.person_face_default);
            switch (personBean.getState()) {
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
                default:
                    break;
            }
            if (personBean.isCollect()) {
                holder.collectIv.setImageResource(R.mipmap.collect_yellow);
            } else {
                holder.collectIv.setImageResource(R.mipmap.collect_gray);
            }
            holder.nameTv.setText(personBean.getName());
            holder.playTv.setText(personBean.getPlay());
            holder.showTv.setText(personBean.getShow());
            holder.distanceTv.setText(personBean.getDistance());
        }
        return convertView;
    }

    private class ViewHolder {

        private ImageView imageIv, stateIv, collectIv;
        private TextView nameTv, playTv, showTv, distanceTv;

        public ViewHolder(View itemView) {
            imageIv = (ImageView) itemView.findViewById(R.id.iv_item_worker_mag_image);
            stateIv = (ImageView) itemView.findViewById(R.id.iv_item_worker_mag_state);
            collectIv = (ImageView) itemView.findViewById(R.id.iv_item_worker_mag_collect);
            nameTv = (TextView) itemView.findViewById(R.id.tv_item_worker_mag_name);
            playTv = (TextView) itemView.findViewById(R.id.tv_item_worker_mag_play);
            showTv = (TextView) itemView.findViewById(R.id.tv_item_worker_mag_show);
            distanceTv = (TextView) itemView.findViewById(R.id.tv_item_worker_mag_distance);
        }
    }
}
