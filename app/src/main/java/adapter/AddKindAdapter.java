package adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjzg.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import bean.AddKind;
import listener.ListItemClickHelp;

/**
 * 创建日期：2017/8/29 on 14:50
 * 作者:孙明明
 * 描述:添加工种适配器
 */

public class AddKindAdapter extends BaseAdapter {

    private Context context;
    private List<AddKind> list;
    private ListItemClickHelp callback;

    public AddKindAdapter(Context context, List<AddKind> list, ListItemClickHelp callback) {
        this.context = context;
        this.list = list;
        this.callback = callback;
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
            convertView = View.inflate(context, R.layout.item_add_kind, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final View view = convertView;
        final int p = position;
        final int id = holder.checkedCb.getId();
        holder.checkedCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                callback.onClick(view, parent, p, id, isChecked);
            }
        });
        AddKind addKind = list.get(position);
        if (addKind != null) {
            if (!TextUtils.isEmpty(addKind.getImg())) {
                Picasso.with(context).load(addKind.getImg()).placeholder(holder.imgIv.getDrawable()).into(holder.imgIv);
            } else {
                holder.imgIv.setImageResource(R.mipmap.ic_launcher);
            }
            holder.contentTv.setText(addKind.getContent());
            holder.checkedCb.setChecked(addKind.isChecked());
        }
        return convertView;
    }

    private class ViewHolder {

        private ImageView imgIv;
        private TextView contentTv;
        private CheckBox checkedCb;

        public ViewHolder(View itemView) {
            imgIv = (ImageView) itemView.findViewById(R.id.iv_item_add_kind_img);
            contentTv = (TextView) itemView.findViewById(R.id.tv_item_add_kind_content);
            checkedCb = (CheckBox) itemView.findViewById(R.id.cb_item_add_kind_checked);
        }
    }
}
