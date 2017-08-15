package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.Person;

/**
 * 创建日期：2017/8/2 on 10:39
 * 作者:孙明明
 * 描述:工人/雇主适配器
 */

public class PersonAdapter extends BaseAdapter {

    private Context context;
    private List<Person> list;
    private ViewHolder holder;

    public PersonAdapter(Context context, List<Person> list) {
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
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_worker, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Person person = list.get(position);
        if (person != null) {
            holder.imageIv.setImageResource(R.mipmap.person_face_default);
            holder.stateIv.setImageResource(R.mipmap.worker_leisure);
            holder.collectIv.setImageResource(R.mipmap.collect_gray);
            holder.nameTv.setText(person.getName());
            holder.playTv.setText(person.getPlay());
            holder.showTv.setText(person.getShow());
            holder.distanceTv.setText(person.getDistance());
        }
        return convertView;
    }

    private class ViewHolder {

        private ImageView imageIv, stateIv, collectIv;
        private TextView nameTv, playTv, showTv, distanceTv;

        public ViewHolder(View itemView) {
            imageIv = (ImageView) itemView.findViewById(R.id.iv_item_worker_image);
            stateIv = (ImageView) itemView.findViewById(R.id.iv_item_worker_state);
            collectIv = (ImageView) itemView.findViewById(R.id.iv_item_worker_collect);
            nameTv = (TextView) itemView.findViewById(R.id.tv_item_worker_name);
            playTv = (TextView) itemView.findViewById(R.id.tv_item_worker_play);
            showTv = (TextView) itemView.findViewById(R.id.tv_item_worker_show);
            distanceTv = (TextView) itemView.findViewById(R.id.tv_item_worker_distance);
        }
    }
}
