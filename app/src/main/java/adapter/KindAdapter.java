package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.KindBean;

/**
 * 创建日期：2017/7/31 on 13:46
 * 作者:孙明明
 * 描述:种类适配器
 */

public class KindAdapter extends CommonAdapter<KindBean> {

    public KindAdapter(Context context, List<KindBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_kind, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        KindBean job = list.get(position);
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
            imageIv = (ImageView) itemView.findViewById(R.id.iv_item_kind_img);
            nameTv = (TextView) itemView.findViewById(R.id.tv_item_kind_name);
        }
    }
}
