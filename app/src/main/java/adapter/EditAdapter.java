package adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gjzg.R;

import bean.PersonPreview;
import listener.EditClickHelp;
import utils.Utils;

/**
 * 创建日期：2017/8/11 on 11:18
 * 作者:孙明明
 * 描述:编辑适配器
 */

public class EditAdapter extends BaseAdapter {

    private Context context;
    private PersonPreview personPreview;
    private EditClickHelp editClickHelp;

    private int viewType;
    private int radio;
    private int mTouchItemPosition = -1;

    public EditAdapter(Context context, PersonPreview personPreview, EditClickHelp editClickHelp) {
        this.context = context;
        this.personPreview = personPreview;
        this.editClickHelp = editClickHelp;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 3:
            case 5:
                viewType = 0;
                break;
            case 0:
            case 2:
            case 4:
                viewType = 1;
                break;
            case 1:
            case 6:
                viewType = 2;
                break;
            case 7:
                viewType = 3;
                break;
        }
        return viewType;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder0 holder0;
        ViewHolder1 holder1;
        ViewHolder2 holder2;
        ViewHolder3 holder3;
        String title = "";
        String content = "";
        switch (getItemViewType(position)) {
            case 0:
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.item_person_manage_edit_type_1, null);
                    holder0 = new ViewHolder0(convertView);
                    convertView.setTag(holder0);
                } else {
                    holder0 = (ViewHolder0) convertView.getTag();
                }
                if (personPreview != null) {
                    switch (position) {
                        case 3:
                            title = personPreview.getAddressTitle();
                            content = personPreview.getAddressContent();
                            break;
                        case 5:
                            title = personPreview.getPhoneNumberTitle();
                            content = personPreview.getPhoneNumberContent();
                        default:
                            break;
                    }
                    holder0.titleTv.setText(title);
                    holder0.contentTv.setText(content);
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
                if (personPreview != null) {
                    switch (position) {
                        case 0:
                            title = personPreview.getNameTitle();
                            content = personPreview.getNameContent();
                            break;
                        case 2:
                            title = personPreview.getIdNumberTitle();
                            content = personPreview.getIdNumberContent();
                            break;
                        case 4:
                            title = personPreview.getBriefTitle();
                            content = personPreview.getBriefContent();
                            break;
                        default:
                            break;
                    }
                    holder1.titleTv.setText(title);
                    holder1.contentEt.setText(content);
                }
                holder1.contentEt.setTag(position);
                holder1.contentEt.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            mTouchItemPosition = (int) v.getTag();
                        }
                        return false;
                    }
                });
                holder1.contentEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        EditText et = (EditText) v;
                        MyTextWatcher mTw = new MyTextWatcher();
                        if (hasFocus) {
                            et.addTextChangedListener(mTw);
                        } else {
                            et.removeTextChangedListener(mTw);
                        }
                    }
                });
                if(mTouchItemPosition == position){
                    holder1.contentEt.requestFocus();
                    holder1.contentEt.setSelection(holder1.contentEt.getText().length());
                }else{
                    holder1.contentEt.clearFocus();
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
                if (personPreview != null) {
                    switch (position) {
                        case 1:
                            title = personPreview.getSexTitle();
                            content = personPreview.getSexContent();
                            holder2.titleTv.setText(title);
                            holder2.rb1.setText("男");
                            holder2.rb2.setText("女");
                            if (content.equals("无")) {
                                radio = 0;
                            } else if (content.equals("男")) {
                                radio = 0;
                            } else if (content.equals("女")) {
                                radio = 1;
                            }
                            ((RadioButton) holder2.rg.getChildAt(radio)).setChecked(true);
                            break;
                        case 6:
                            title = personPreview.getRoleTitle();
                            content = personPreview.getRoleContent();
                            holder2.titleTv.setText(title);
                            holder2.rb1.setText("我不是工人");
                            holder2.rb2.setText("我是工人");
                            if (content.equals("无")) {
                                radio = 0;
                            } else if (content.equals("我不是工人")) {
                                radio = 0;
                            } else if (content.equals("我是工人")) {
                                radio = 1;
                            }
                            ((RadioButton) holder2.rg.getChildAt(radio)).setChecked(true);
                            break;
                        default:
                            break;
                    }
                }
                final View rdV = convertView;
                final int rdP = position;
                final int rdId1 = holder2.rb1.getId();
                final int rdId2 = holder2.rb2.getId();
                holder2.rb1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editClickHelp.onClick(rdV, parent, rdP, rdId1, "");
                    }
                });
                holder2.rb2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editClickHelp.onClick(rdV, parent, rdP, rdId2, "");
                    }
                });
                break;
            case 3:
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.item_person_manage_edit_type_4, null);
                    holder3 = new ViewHolder3(convertView);
                    convertView.setTag(holder3);
                } else {
                    holder3 = (ViewHolder3) convertView.getTag();
                }
                if (personPreview != null) {
                    switch (position) {
                        case 7:
                            if (personPreview.getRoleList() != null && personPreview.getRoleList().size() != 0) {
                                holder3.gridView.setAdapter(new RoleAdapter(context, 1, personPreview.getRoleList()));
                                Utils.setGridViewHeight(holder3.gridView, 4);
                            }
                            if (personPreview.getRoleContent().equals("无")) {
                                holder3.ll.setVisibility(View.GONE);
                            } else if (personPreview.getRoleContent().equals("我不是工人")) {
                                holder3.ll.setVisibility(View.GONE);
                            } else if (personPreview.getRoleContent().equals("我是工人")) {
                                holder3.ll.setVisibility(View.VISIBLE);
                            }
                            break;
                        default:
                            break;
                    }
                }
                break;
            default:
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

        private LinearLayout ll;
        private GridView gridView;
        private TextView tv;

        public ViewHolder3(View itemView) {
            ll = (LinearLayout) itemView.findViewById(R.id.ll_item_person_manage_edit_type_4);
            gridView = (GridView) itemView.findViewById(R.id.gv_item_person_manage_edit_type_4);
            tv = (TextView) itemView.findViewById(R.id.tv_item_person_edit_add);
        }
    }

    class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
