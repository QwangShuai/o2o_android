package adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjzg.R;
import com.squareup.picasso.Picasso;

import bean.EvaluateBean;
import bean.PersonDetailBean;
import utils.Utils;
import view.CImageView;

public class PersonDetailAdapter extends BaseAdapter {

    private Context context;
    private PersonDetailBean personDetailBean;

    public PersonDetailAdapter(Context context, PersonDetailBean personDetailBean) {
        this.context = context;
        this.personDetailBean = personDetailBean;
    }

    @Override
    public int getViewTypeCount() {
        if (personDetailBean.isWorker()) {
            return 5;
        } else {
            return 4;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
        if (getViewTypeCount() == 10 + personDetailBean.getEvaluateBeanList().size()) {
            switch (position) {
                case 0:
                    type = 0;
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    type = 1;
                    break;
                case 8:
                    type = 2;
                    break;
                case 9:
                    type = 3;
                    break;
                default:
                    type = 4;
                    break;
            }
        } else if (getViewTypeCount() == 9 + personDetailBean.getEvaluateBeanList().size()) {
            switch (position) {
                case 0:
                    type = 0;
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    type = 1;
                    break;
                case 8:
                    type = 3;
                    break;
                default:
                    type = 4;
                    break;
            }
        }
        return type;
    }

    @Override
    public int getCount() {
        if (personDetailBean == null) {
            return 0;
        } else if (personDetailBean.isWorker()) {
            return 10 + personDetailBean.getEvaluateBeanList().size();
        } else {
            return 9 + personDetailBean.getEvaluateBeanList().size();
        }
    }

    @Override
    public Object getItem(int position) {
        return personDetailBean;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder0 holder0;
        ViewHolder1 holder1;
        ViewHolder2 holder2;
        ViewHolder3 holder3;
        ViewHolder4 holder4;
        switch (getItemViewType(position)) {
            case 0:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_person_detail_0, null);
                    holder0 = new ViewHolder0(convertView);
                    convertView.setTag(holder0);
                } else {
                    holder0 = (ViewHolder0) convertView.getTag();
                }
                if (TextUtils.isEmpty(personDetailBean.getIcon())) {
                    holder0.iconIv.setImageResource(R.mipmap.person_face_default);
                } else {
                    Picasso.with(context).load(personDetailBean.getIcon()).placeholder(holder0.iconIv.getDrawable()).into(holder0.iconIv);
                }
                break;
            case 1:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_person_detail_1, null);
                    holder1 = new ViewHolder1(convertView);
                    convertView.setTag(holder1);
                } else {
                    holder1 = (ViewHolder1) convertView.getTag();
                }
                switch (position) {
                    case 1:
                        holder1.titleTv.setText("姓名");
                        holder1.contentTv.setText(personDetailBean.getName());
                        break;
                    case 2:
                        holder1.titleTv.setText("性别");
                        if (personDetailBean.isMale()) {
                            holder1.contentTv.setText("男");
                        } else {
                            holder1.contentTv.setText("女");
                        }
                        break;
                    case 3:
                        holder1.titleTv.setText("年龄");
                        holder1.contentTv.setText(personDetailBean.getAge() + "");
                        break;
                    case 4:
                        holder1.titleTv.setText("现居地");
                        holder1.contentTv.setText(personDetailBean.getAddress());
                        break;
                    case 5:
                        holder1.titleTv.setText("户口所在地");
                        holder1.contentTv.setText(personDetailBean.getHousehold());
                        break;
                    case 6:
                        holder1.titleTv.setText("个人简介");
                        holder1.contentTv.setText(personDetailBean.getBrief());
                        break;
                    case 7:
                        holder1.titleTv.setText("角色选择");
                        if (personDetailBean.isWorker()) {
                            holder1.contentTv.setText("工人");
                        } else {
                            holder1.contentTv.setText("雇主");
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_person_detail_2, null);
                    holder2 = new ViewHolder2(convertView);
                    convertView.setTag(holder2);
                } else {
                    holder2 = (ViewHolder2) convertView.getTag();
                }
                if (personDetailBean.getRoleBeanList() != null && personDetailBean.getRoleBeanList().size() != 0) {
                    holder2.gv.setAdapter(new RoleAdapter(context, 0, personDetailBean.getRoleBeanList()));
                    Utils.setGridViewHeight(holder2.gv, 4);
                }
                break;
            case 3:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_person_detail_3, null);
                    holder3 = new ViewHolder3(convertView);
                    convertView.setTag(holder3);
                } else {
                    holder3 = (ViewHolder3) convertView.getTag();
                }
                holder3.countTv.setText("Ta收到的评价（" + personDetailBean.getCount() + "）");
                break;
            case 4:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_person_detail_4, null);
                    holder4 = new ViewHolder4(convertView);
                    convertView.setTag(holder4);
                } else {
                    holder4 = (ViewHolder4) convertView.getTag();
                }
                if (personDetailBean.getEvaluateBeanList() != null && personDetailBean.getEvaluateBeanList().size() != 0) {
                    EvaluateBean evaluateBean = personDetailBean.getEvaluateBeanList().get(position - 10);
                    if (TextUtils.isEmpty(evaluateBean.getIcon())) {
                        holder4.iconIv.setImageResource(R.mipmap.person_face_default);
                    } else {
                        Picasso.with(context).load(evaluateBean.getIcon()).placeholder(holder4.iconIv.getDrawable()).into(holder4.iconIv);
                    }
                    holder4.descriptionTv.setText(evaluateBean.getContent());
                    holder4.dateTv.setText(evaluateBean.getTime());
                }
                break;
            default:
                break;
        }
        return convertView;
    }

    private class ViewHolder0 {

        private CImageView iconIv;

        public ViewHolder0(View itemView) {
            iconIv = (CImageView) itemView.findViewById(R.id.civ_item_person_detail_0_icon);
        }
    }

    private class ViewHolder1 {

        private TextView titleTv, contentTv;

        public ViewHolder1(View itemView) {
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_person_detail_1_title);
            contentTv = (TextView) itemView.findViewById(R.id.tv_item_person_detail_1_content);
        }
    }

    private class ViewHolder2 {

        private GridView gv;

        public ViewHolder2(View itemView) {
            gv = (GridView) itemView.findViewById(R.id.gv_item_person_detail_2);
        }
    }

    private class ViewHolder3 {

        private TextView countTv;

        public ViewHolder3(View itemView) {
            countTv = (TextView) itemView.findViewById(R.id.tv_item_person_detail_3);
        }
    }

    private class ViewHolder4 {

        private ImageView iconIv;
        private TextView descriptionTv, dateTv;

        public ViewHolder4(View itemView) {
            iconIv = (ImageView) itemView.findViewById(R.id.civ_item_person_detail_4_icon);
            descriptionTv = (TextView) itemView.findViewById(R.id.tv_item_person_detail_4_description);
            dateTv = (TextView) itemView.findViewById(R.id.tv_item_person_detail_4_date);
        }
    }
}
