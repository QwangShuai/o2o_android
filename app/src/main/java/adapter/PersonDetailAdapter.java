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
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        switch (position) {
            case 0:
                type = 0;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                type = 1;
                break;
            case 6:
                type = 2;
                break;
            default:
                type = 3;
                break;
        }
        return type;
    }

    @Override
    public int getCount() {
        return personDetailBean == null ? 0 : 7 + personDetailBean.getEvaluateBeanList().size();
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
                        switch (personDetailBean.getMale()) {
                            case "-1":
                                holder1.contentTv.setText("无");
                                break;
                            case "0":
                                holder1.contentTv.setText("女");
                                break;
                            case "1":
                                holder1.contentTv.setText("男");
                                break;
                            default:
                                break;
                        }
                        break;
                    case 3:
                        holder1.titleTv.setText("现居地");
                        holder1.contentTv.setText(personDetailBean.getAddress());
                        break;
                    case 4:
                        holder1.titleTv.setText("户口所在地");
                        holder1.contentTv.setText(personDetailBean.getHousehold());
                        break;
                    case 5:
                        holder1.titleTv.setText("个人简介");
                        holder1.contentTv.setText(personDetailBean.getBrief());
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
                holder2.titleTv.setText("角色选择");
                holder2.contentTv.setText("");
                holder2.gv.setVisibility(View.VISIBLE);
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
                if (personDetailBean.getEvaluateBeanList() != null && personDetailBean.getEvaluateBeanList().size() != 0) {
                    EvaluateBean evaluateBean = personDetailBean.getEvaluateBeanList().get(position - 7);
                    if (position == 8) {
                        holder3.countTv.setText("Ta收到的评价（" + personDetailBean.getCount() + ")");
                        holder3.countTv.setVisibility(View.VISIBLE);
                    } else {
                        holder3.countTv.setVisibility(View.GONE);
                    }
                    if (TextUtils.isEmpty(evaluateBean.getIcon())) {
                        holder3.iconIv.setImageResource(R.mipmap.person_face_default);
                    } else {
                        Picasso.with(context).load(evaluateBean.getIcon()).placeholder(holder3.iconIv.getDrawable()).into(holder3.iconIv);
                    }
                    holder3.descriptionTv.setText(evaluateBean.getContent());
                    holder3.dateTv.setText(evaluateBean.getTime());
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

        private TextView titleTv, contentTv;
        private GridView gv;

        public ViewHolder2(View itemView) {
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_person_detail_2_title);
            contentTv = (TextView) itemView.findViewById(R.id.tv_item_person_detail_2_content);
            gv = (GridView) itemView.findViewById(R.id.gv_item_person_detail_2);
        }
    }

    private class ViewHolder3 {

        private ImageView iconIv;
        private TextView countTv, descriptionTv, dateTv;

        public ViewHolder3(View itemView) {
            iconIv = (ImageView) itemView.findViewById(R.id.civ_item_person_detail_3_icon);
            countTv = (TextView) itemView.findViewById(R.id.tv_item_person_detail_3_count);
            descriptionTv = (TextView) itemView.findViewById(R.id.tv_item_person_detail_3_description);
            dateTv = (TextView) itemView.findViewById(R.id.tv_item_person_detail_3_date);
        }
    }
}
