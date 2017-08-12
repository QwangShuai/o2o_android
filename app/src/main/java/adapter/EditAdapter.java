package adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.Edit;
import utils.Utils;

/**
 * 创建日期：2017/8/11 on 11:18
 * 作者:孙明明
 * 描述:编辑适配器
 */

public class EditAdapter extends BaseAdapter {

    private Context context;
    private List<Edit> list;
    private int viewType;
    private int radio;
    private ViewHolder0 holder0;
    private ViewHolder1 holder1;
    private ViewHolder2 holder2;
    private ViewHolder3 holder3;

    public EditAdapter(Context context, List<Edit> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 2:
            case 4:
            case 6:
                viewType = 0;
                break;
            case 0:
            case 3:
            case 5:
                viewType = 1;
                break;
            case 1:
            case 7:
                viewType = 2;
                break;
            case 8:
                viewType = 3;
                break;
        }
        return viewType;
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
        switch (getItemViewType(position)) {
            case 0:
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.item_person_manage_edit_type_1, null);
                    holder0 = new ViewHolder0(convertView);
                    convertView.setTag(holder0);
                } else {
                    holder0 = (ViewHolder0) convertView.getTag();
                }
                Edit edit0 = list.get(position);
                if (edit0 != null) {
                    switch (position) {
                        case 2:
                            holder0.titleTv.setText("出生日期");
                            if (!TextUtils.isEmpty(edit0.getBirth())) {
                                holder0.contentTv.setText(edit0.getBirth());
                            } else {
                                holder0.contentTv.setText("选择出生日期");
                            }
                            break;
                        case 4:
                            holder0.titleTv.setText("现居地");
                            if (!TextUtils.isEmpty(edit0.getAddress())) {
                                holder0.contentTv.setText(edit0.getAddress());
                            } else {
                                holder0.contentTv.setText("选择居住地");
                            }
                            break;
                        case 6:
                            holder0.titleTv.setText("手机号码（已绑定）");
                            if (!TextUtils.isEmpty(edit0.getPhoneNumber())) {
                                holder0.contentTv.setText(edit0.getPhoneNumber());
                            } else {
                                holder0.contentTv.setText("");
                            }
                            break;
                    }
                }
                break;
            case 1:
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.item_person_manage_edit_type_2, null);
                    holder1 = new ViewHolder1(convertView);
                    convertView.setTag(holder1);
                } else {
                    holder1 = (ViewHolder1) convertView.getTag();
                }
                Edit edit1 = list.get(position);
                if (edit1 != null) {
                    switch (position) {
                        case 0:
                            holder1.titleTv.setText("姓名");
                            if (!TextUtils.isEmpty(edit1.getName())) {
                                holder1.contentEt.setText(edit1.getName());
                            } else {
                                holder1.contentEt.setHint("请输入姓名");
                            }
                            break;
                        case 3:
                            holder1.titleTv.setText("身份证号");
                            if (!TextUtils.isEmpty(edit1.getIdNumber())) {
                                holder1.contentEt.setText(edit1.getIdNumber());
                            } else {
                                holder1.contentEt.setHint("输入身份证号");
                            }
                            break;
                        case 5:
                            holder1.titleTv.setText("个人简介");
                            if (!TextUtils.isEmpty(edit1.getBrief())) {
                                holder1.contentEt.setText(edit1.getBrief());
                            } else {
                                holder1.contentEt.setHint("请简要描述自己");
                            }
                            break;
                    }
                }
                break;
            case 2:
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.item_person_manage_edit_type_3, null);
                    holder2 = new ViewHolder2(convertView);
                    convertView.setTag(holder2);
                } else {
                    holder2 = (ViewHolder2) convertView.getTag();
                }
                Edit edit2 = list.get(position);
                if (edit2 != null) {
                    switch (position) {
                        case 1:
                            holder2.titleTv.setText("性别");
                            holder2.rb1.setText("男");
                            holder2.rb2.setText("女");
                            if (edit2.isMale()) {
                                radio = 0;
                            } else {
                                radio = 1;
                            }
                            ((RadioButton) holder2.rg.getChildAt(radio)).setChecked(true);
                            break;
                        case 7:
                            holder2.titleTv.setText("角色选择");
                            holder2.rb1.setText("我不是工人");
                            holder2.rb2.setText("我是工人");
                            if (edit2.isWorker()) {
                                radio = 1;
                            } else {
                                radio = 0;
                            }
                            ((RadioButton) holder2.rg.getChildAt(radio)).setChecked(true);
                            break;
                    }
                }
                break;
            case 3:
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.item_person_manage_edit_type_4, null);
                    holder3 = new ViewHolder3(convertView);
                    convertView.setTag(holder3);
                } else {
                    holder3 = (ViewHolder3) convertView.getTag();
                }
                Edit edit3 = list.get(position);
                if (edit3 != null) {
                    switch (position) {
                        case 8:
                            if (edit3.getRoleList() != null && edit3.getRoleList().size() != 0) {
                                holder3.gridView.setAdapter(new RoleAdapter(context, edit3.getRoleList()));
                                Utils.setGridViewHeight(holder3.gridView, 4);
                            }
                            break;
                    }
                }
                break;
        }
        return convertView;
    }

    private class ViewHolder0 {

        private TextView titleTv, contentTv;

        public ViewHolder0(View itemView) {
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_person_manage_edit_type_1_title);
            contentTv = (TextView) itemView.findViewById(R.id.tv_item_person_manage_edit_type_1_content);
        }
    }

    private class ViewHolder1 {

        private TextView titleTv;
        private EditText contentEt;

        public ViewHolder1(View itemView) {
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_person_manage_edit_type_2_title);
            contentEt = (EditText) itemView.findViewById(R.id.et_item_person_manage_edit_type_2_content);
        }
    }

    private class ViewHolder2 {

        private TextView titleTv;
        private RadioGroup rg;
        private RadioButton rb1, rb2;

        public ViewHolder2(View itemView) {
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_person_manage_edit_type_3_title);
            rg = (RadioGroup) itemView.findViewById(R.id.rg_item_person_manage_edit_type_3);
            rb1 = (RadioButton) itemView.findViewById(R.id.rb_item_person_manage_edit_type_3_1);
            rb2 = (RadioButton) itemView.findViewById(R.id.rb_item_person_manage_edit_type_3_2);
        }
    }

    private class ViewHolder3 {

        private GridView gridView;

        public ViewHolder3(View itemView) {
            gridView = (GridView) itemView.findViewById(R.id.gv_item_person_manage_edit_type_4);
        }
    }
}
