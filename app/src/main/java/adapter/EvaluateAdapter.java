package adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gjzg.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import bean.EvaluateBean;

/**
 * 创建日期：2017/8/25 on 16:29
 * 作者:孙明明
 * 描述:评价适配器
 */

public class EvaluateAdapter extends CommonAdapter<EvaluateBean> {

    public EvaluateAdapter(Context context, List<EvaluateBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_evaluate, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        EvaluateBean e = list.get(position);
        if (e != null) {
            if (position == 0) {
                holder.numCountLl.setVisibility(View.VISIBLE);
                if (e.isGet()) {
                    holder.numCountTv.setText("我收到的评价（" + e.getNumCount() + "）");
                } else {
                    holder.numCountTv.setText("给别人的评价（" + e.getNumCount() + "）");
                }
            } else {
                holder.numCountLl.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(e.getIcon())) {
                Picasso.with(context).load(e.getIcon()).placeholder(holder.iconIv.getDrawable()).into(holder.iconIv);
            } else {
                holder.iconIv.setImageResource(R.mipmap.person_face_default);
            }
            holder.contentTv.setText(e.getContent());
            switch (e.getPraiseCount()) {
                case 0:
                    holder.praiseIv.setImageResource(R.mipmap.praise_none);
                    break;
                case 1:
                    holder.praiseIv.setImageResource(R.mipmap.praise_one);
                    break;
                case 2:
                    holder.praiseIv.setImageResource(R.mipmap.praise_two);
                    break;
                case 3:
                    holder.praiseIv.setImageResource(R.mipmap.praise_three);
                    break;
            }
            holder.timeTv.setText(e.getTime());
        }
        return convertView;
    }

    private class ViewHolder {

        private LinearLayout numCountLl;
        private TextView numCountTv;
        private ImageView iconIv;
        private TextView contentTv;
        private ImageView praiseIv;
        private TextView timeTv;

        public ViewHolder(View itemView) {
            numCountLl = (LinearLayout) itemView.findViewById(R.id.ll_item_evaluate_num_count);
            numCountTv = (TextView) itemView.findViewById(R.id.tv_item_evaluate_num_count);
            iconIv = (ImageView) itemView.findViewById(R.id.iv_item_evaluate_icon);
            contentTv = (TextView) itemView.findViewById(R.id.tv_item_evaluate_content);
            praiseIv = (ImageView) itemView.findViewById(R.id.iv_item_evaluate_praise);
            timeTv = (TextView) itemView.findViewById(R.id.tv_item_evaluate_time);
        }
    }
}
