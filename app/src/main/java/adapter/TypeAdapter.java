package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.Type;

/**
 * 创建日期：2017/7/31 on 13:46
 * 作者:孙明明
 * 描述:工种适配器
 */

public class TypeAdapter extends BaseAdapter {

    private Context context;
    private List<Type> list;
    private ViewHolder holder;

    public TypeAdapter(Context context, List<Type> list) {
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
            convertView = View.inflate(context, R.layout.item_job, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Type job = list.get(position);
        if (job != null) {
            holder.imageIv.setImageResource(R.mipmap.ic_launcher);
            holder.nameTv.setText(job.getName());
        }
        return convertView;
    }

    private class ViewHolder {

        private ImageView imageIv;
        private TextView nameTv;

        public ViewHolder(View itemView) {
            imageIv = (ImageView) itemView.findViewById(R.id.iv_item_job_image);
            nameTv = (TextView) itemView.findViewById(R.id.tv_item_job_name);
        }
    }
}
