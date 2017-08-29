package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.gjzg.R;

import bean.PersonPreview;
import utils.Utils;

/**
 * 创建日期：2017/8/10 on 15:07
 * 作者:孙明明
 * 描述:信息预览适配器
 */

public class PersonPrivewAdapter extends BaseAdapter {

    private Context context;
    private PersonPreview personPreview;

    public PersonPrivewAdapter(Context context, PersonPreview personPreview) {
        this.context = context;
        this.personPreview = personPreview;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 7) {
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
        return personPreview == null ? 0 : 8;
    }

    @Override
    public Object getItem(int position) {
        return personPreview;
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
            if (personPreview != null) {
                switch (position) {
                    case 0:
                        title = personPreview.getNameTitle();
                        content = personPreview.getNameContent();
                        break;
                    case 1:
                        title = personPreview.getSexTitle();
                        content = personPreview.getSexContent();
                        break;
                    case 2:
                        title = personPreview.getIdNumberTitle();
                        content = personPreview.getIdNumberContent();
                        break;
                    case 3:
                        title = personPreview.getAddressTitle();
                        content = personPreview.getAddressContent();
                        break;
                    case 4:
                        title = personPreview.getBriefTitle();
                        content = personPreview.getBriefContent();
                        break;
                    case 5:
                        title = personPreview.getPhoneNumberTitle();
                        content = personPreview.getPhoneNumberContent();
                        break;
                    case 6:
                        title = personPreview.getRoleTitle();
                        content = personPreview.getRoleContent();
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
            if (personPreview != null) {
                if (position == 7) {
                    if (personPreview.getRoleList() != null && personPreview.getRoleList().size() != 0) {
                        holder2.gridView.setAdapter(new RoleAdapter(context, 0, personPreview.getRoleList()));
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
