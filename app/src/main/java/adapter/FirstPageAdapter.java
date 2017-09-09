package adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gjzg.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 创建日期：2017/8/15 on 10:35
 * 作者:孙明明
 * 描述:首页适配器
 */

public class FirstPageAdapter extends CommonAdapter<String> {

    public FirstPageAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_first_page, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String str = list.get(position);
        if (!TextUtils.isEmpty(str)) {
            Picasso.with(context).load(str).placeholder(holder.imgIv.getDrawable()).into(holder.imgIv);
        } else {
            switch (position) {
                case 0:
                    holder.imgIv.setImageResource(R.mipmap.find_worker);
                    break;
                case 1:
                    holder.imgIv.setImageResource(R.mipmap.find_job);
                    break;
                case 2:
                    holder.imgIv.setImageResource(R.mipmap.send_job);
                    break;
            }
        }
        return convertView;
    }

    private class ViewHolder {

        private ImageView imgIv;

        public ViewHolder(View itemView) {
            imgIv = (ImageView) itemView.findViewById(R.id.iv_item_first_page);
        }
    }
}
