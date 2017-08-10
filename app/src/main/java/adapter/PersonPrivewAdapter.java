package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.PersonPreview;

/**
 * 创建日期：2017/8/10 on 15:07
 * 作者:孙明明
 * 描述:信息预览适配器
 */

public class PersonPrivewAdapter extends BaseAdapter {

    private Context context;
    private List<PersonPreview> list;
    private ViewHolder holder;

    public PersonPrivewAdapter(Context context, List<PersonPreview> list) {
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
            convertView = View.inflate(context, R.layout.item_person_manage_preview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PersonPreview personPreview = list.get(position);
        if (personPreview != null) {
            if (personPreview.getType() == 0) {
                switch (position) {
                    case 0:
                        holder.titleTv.setText("姓名");
                        holder.contentTv.setText(personPreview.getName());
                        break;
                    case 1:
                        holder.titleTv.setText("性别");
                        holder.contentTv.setText(personPreview.getSex());
                        break;
                    case 2:
                        holder.titleTv.setText("出生日期");
                        holder.contentTv.setText(personPreview.getBirth());
                        break;
                    case 3:
                        holder.titleTv.setText("身份证号");
                        holder.contentTv.setText(personPreview.getIdNumber());
                        break;
                    case 4:
                        holder.titleTv.setText("现居地");
                        holder.contentTv.setText(personPreview.getAddress());
                        break;
                    case 5:
                        holder.titleTv.setText("户口所在地");
                        holder.contentTv.setText(personPreview.getHousehold());
                        break;
                    case 6:
                        holder.titleTv.setText("个人简介");
                        holder.contentTv.setText(personPreview.getBrief());
                        break;
                    case 7:
                        holder.titleTv.setText("手机号码（已绑定）");
                        holder.contentTv.setText(personPreview.getPhoneNumber());
                        break;
                }
            }
        }
        return convertView;
    }

    private class ViewHolder {

        private TextView titleTv, contentTv;

        public ViewHolder(View itemView) {
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_person_manage_preview_title);
            contentTv = (TextView) itemView.findViewById(R.id.tv_item_person_manage_preview_content);
        }
    }
}
