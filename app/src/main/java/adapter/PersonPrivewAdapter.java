package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.gjzg.R;

import personmanage.bean.PersonPreviewBean;
import utils.Utils;

/**
 * 创建日期：2017/8/10 on 15:07
 * 作者:孙明明
 * 描述:信息预览适配器
 */

public class PersonPrivewAdapter extends BaseAdapter {

    private Context context;
    private PersonPreviewBean personPreviewBean;

    public PersonPrivewAdapter(Context context, PersonPreviewBean personPreviewBean) {
        this.context = context;
        this.personPreviewBean = personPreviewBean;
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
        return personPreviewBean == null ? 0 : 9;
    }

    @Override
    public Object getItem(int position) {
        return personPreviewBean;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1;
        ViewHolder2 holder2;
        String title = "";
        String content = "";
        if (getItemViewType(position) == 0) {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_person_manage_preview_type_1, null);
                holder1 = new ViewHolder1(convertView);
                convertView.setTag(holder1);
            } else {
                holder1 = (ViewHolder1) convertView.getTag();
            }
            if (personPreviewBean != null) {
                switch (position) {
                    case 0:
                        title = personPreviewBean.getNameTitle();
                        content = personPreviewBean.getNameContent();
                        break;
                    case 1:
                        title = personPreviewBean.getSexTitle();
                        if (personPreviewBean.isSex()) {
                            content = "男";
                        } else {
                            content = "女";
                        }
                        break;
                    case 2:
                        title = personPreviewBean.getIdNumberTitle();
                        content = personPreviewBean.getIdNumberContent();
                        break;
                    case 3:
                        title = personPreviewBean.getAddressTitle();
                        content = personPreviewBean.getAddressContent();
                        break;
                    case 4:
                        title = personPreviewBean.getHouseHoldTitle();
                        content = personPreviewBean.getHouseHoldContent();
                        break;
                    case 5:
                        title = personPreviewBean.getBriefTitle();
                        content = personPreviewBean.getBriefContent();
                        break;
                    case 6:
                        title = personPreviewBean.getPhoneNumberTitle();
                        content = personPreviewBean.getPhoneNumberContent();
                        break;
                    case 7:
                        title = personPreviewBean.getRoleTitle();
                        if (personPreviewBean.isRole()) {
                            content = "我不是工人";
                        } else {
                            content = "我是工人";
                        }
                        break;
                }
                holder1.titleTv.setText(title);
                holder1.contentTv.setText(content);
            }
        } else if (getItemViewType(position) == 1) {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_person_manage_preview_type_2, null);
                holder2 = new ViewHolder2(convertView);
                convertView.setTag(holder2);
            } else {
                holder2 = (ViewHolder2) convertView.getTag();
            }
            if (personPreviewBean != null) {
                if (position == 8) {
                    if (personPreviewBean.getRoleBeanList() != null && personPreviewBean.getRoleBeanList().size() != 0) {
                        holder2.gridView.setAdapter(new RoleAdapter(context, 0, personPreviewBean.getRoleBeanList()));
                        Utils.setGridViewHeight(holder2.gridView, 4);
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
