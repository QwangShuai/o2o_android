package adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.PersonPreview;
import utils.Utils;

/**
 * 创建日期：2017/8/10 on 15:07
 * 作者:孙明明
 * 描述:信息预览适配器
 */

public class PersonPrivewAdapter extends BaseAdapter {

    private Context context;
    private List<PersonPreview> list;
    private ViewHolder1 holder1;
    private ViewHolder2 holder2;

    public PersonPrivewAdapter(Context context, List<PersonPreview> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 8) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
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
        if (getItemViewType(position) == 0) {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_person_manage_preview_type_1, null);
                holder1 = new ViewHolder1(convertView);
                convertView.setTag(holder1);
            } else {
                holder1 = (ViewHolder1) convertView.getTag();
            }
            PersonPreview personPreview = list.get(position);
            if (personPreview != null) {
                if (personPreview.getType() == 0) {
                    switch (position) {
                        case 0:
                            holder1.titleTv.setText("姓名");
                            if (!TextUtils.isEmpty(personPreview.getName())) {
                                holder1.contentTv.setText(personPreview.getName());
                            } else {
                                holder1.contentTv.setText("");
                            }
                            break;
                        case 1:
                            holder1.titleTv.setText("性别");
                            if (!TextUtils.isEmpty(personPreview.getSex())) {
                                holder1.contentTv.setText(personPreview.getSex());
                            } else {
                                holder1.contentTv.setText("");
                            }
                            break;
                        case 2:
                            holder1.titleTv.setText("出生日期");
                            if (!TextUtils.isEmpty(personPreview.getBirth())) {
                                holder1.contentTv.setText(personPreview.getBirth());
                            } else {
                                holder1.contentTv.setText("");
                            }
                            break;
                        case 3:
                            holder1.titleTv.setText("身份证号");
                            if (!TextUtils.isEmpty(personPreview.getIdNumber())) {
                                holder1.contentTv.setText(personPreview.getIdNumber());
                            } else {
                                holder1.contentTv.setText("");
                            }
                            break;
                        case 4:
                            holder1.titleTv.setText("现居地");
                            if (!TextUtils.isEmpty(personPreview.getAddress())) {
                                holder1.contentTv.setText(personPreview.getAddress());
                            } else {
                                holder1.contentTv.setText("");
                            }
                            break;
                        case 5:
                            holder1.titleTv.setText("个人简介");
                            if (!TextUtils.isEmpty(personPreview.getBrief())) {
                                holder1.contentTv.setText(personPreview.getBrief());
                            } else {
                                holder1.contentTv.setText("");
                            }
                            break;
                        case 6:
                            holder1.titleTv.setText("手机号码（已绑定）");
                            if (!TextUtils.isEmpty(personPreview.getPhoneNumber())) {
                                holder1.contentTv.setText(personPreview.getPhoneNumber());
                            } else {
                                holder1.contentTv.setText("");
                            }
                            break;
                        case 7:
                            holder1.titleTv.setText("角色选择");
                            holder1.contentTv.setText("");
                            break;
                    }
                }
            }
        } else if (getItemViewType(position) == 1) {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_person_manage_preview_type_2, null);
                holder2 = new ViewHolder2(convertView);
                convertView.setTag(holder2);
            } else {
                holder2 = (ViewHolder2) convertView.getTag();
            }
            PersonPreview personPreview = list.get(position);
            if (position == 8) {
                if (personPreview != null) {
                    if (personPreview.getType() == 1) {
                        if (personPreview.getRoleList() != null && personPreview.getRoleList().size() != 0) {
                            holder2.gridView.setAdapter(new RoleAdapter(context, personPreview.getRoleList()));
                            Utils.setGridViewHeight(holder2.gridView, 4);
                        }
                    }
                }
            }
        }
        return convertView;
    }

    private class ViewHolder1 {

        private TextView titleTv, contentTv;

        public ViewHolder1(View itemView) {
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_person_manage_preview_type_1_title);
            contentTv = (TextView) itemView.findViewById(R.id.tv_item_person_manage_preview_type_1_content);
        }
    }

    private class ViewHolder2 {

        private GridView gridView;

        public ViewHolder2(View itemView) {
            gridView = (GridView) itemView.findViewById(R.id.gv_item_person_manage_preview_type_2);
            gridView.setEnabled(false);
        }
    }
}
