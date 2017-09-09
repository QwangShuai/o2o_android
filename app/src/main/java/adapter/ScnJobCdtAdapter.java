package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gjzg.R;

import bean.ScnJobBean;
import listener.ListItemClickHelp;

/**
 * 创建日期：2017/8/23 on 15:14
 * 作者:孙明明
 * 描述:筛选工作条件适配器
 */

public class ScnJobCdtAdapter extends BaseAdapter {

    private Context context;
    private ScnJobBean scnJobBean;
    private ListItemClickHelp clickHelp;

    public ScnJobCdtAdapter(Context context, ScnJobBean scnJobBean, ListItemClickHelp clickHelp) {
        this.context = context;
        this.scnJobBean = scnJobBean;
        this.clickHelp = clickHelp;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Object getItem(int position) {
        return scnJobBean;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private EtVh etVh;
    private TvVh tvVh;

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        switch (getItemViewType(position)) {
            case 0:
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.item_scn_job_cdt_et, null);
                    etVh = new EtVh(convertView);
                    convertView.setTag(etVh);
                } else {
                    etVh = (EtVh) convertView.getTag();
                }
                if (scnJobBean != null) {
                    etVh.nameEt.setText(scnJobBean.getName());
                    etVh.nameEt.setHint(scnJobBean.getNameHint());
                }
                break;
            case 1:
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.item_scn_job_cdt_tv, null);
                    tvVh = new TvVh(convertView);
                    convertView.setTag(tvVh);
                } else {
                    tvVh = (TvVh) convertView.getTag();
                }
                final View view = convertView;
                final int p = position;
                final int id = tvVh.clickLl.getId();
                tvVh.clickLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickHelp.onClick(view, parent, p, id, false);
                    }
                });
                if (scnJobBean != null) {
                    switch (position) {
                        case 1:
                            tvVh.titleTv.setText(scnJobBean.getDisTitle());
                            tvVh.contentTv.setText(scnJobBean.getDisContent());
                            break;
                        case 2:
                            tvVh.titleTv.setText(scnJobBean.getDurationTitle());
                            tvVh.contentTv.setText(scnJobBean.getDurationContent());
                            break;
                        case 3:
                            tvVh.titleTv.setText(scnJobBean.getMoneyTitle());
                            tvVh.contentTv.setText(scnJobBean.getMoneyContent());
                            break;
                        case 4:
                            tvVh.titleTv.setText(scnJobBean.getStartTimeTitle());
                            tvVh.contentTv.setText(scnJobBean.getStartTimeContent());
                            break;
                        case 5:
                            tvVh.titleTv.setText(scnJobBean.getKindTitle());
                            tvVh.contentTv.setText(scnJobBean.getKindContent());
                            break;
                        case 6:
                            tvVh.titleTv.setText(scnJobBean.getTypeTitle());
                            tvVh.contentTv.setText(scnJobBean.getTypeContent());
                            break;
                    }
                }
                break;
        }
        return convertView;
    }

    private class EtVh {

        private EditText nameEt;

        public EtVh(View itemView) {
            nameEt = (EditText) itemView.findViewById(R.id.et_item_scn_job_name);
        }
    }

    private class TvVh {

        private LinearLayout clickLl;
        private TextView titleTv, contentTv;

        public TvVh(View itemView) {
            clickLl = (LinearLayout) itemView.findViewById(R.id.ll_item_scn_job_cdt_click);
            titleTv = (TextView) itemView.findViewById(R.id.tv_item_scn_job_cdt_tv_title);
            contentTv = (TextView) itemView.findViewById(R.id.tv_item_scn_job_cdt_tv_content);
        }
    }
}
