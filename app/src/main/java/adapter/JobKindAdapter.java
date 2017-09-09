package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.gjzg.R;

import java.util.List;

import bean.JobKindBean;
import listener.ListItemClickHelp;

/**
 * 创建日期：2017/8/30 on 10:40
 * 作者:孙明明
 * 描述:发布工作-招聘工种
 */

public class JobKindAdapter extends BaseAdapter {

    private Context context;
    private List<JobKindBean> list;
    private ListItemClickHelp clickHelp;

    public JobKindAdapter(Context context, List<JobKindBean> list, ListItemClickHelp clickHelp) {
        this.context = context;
        this.list = list;
        this.clickHelp = clickHelp;
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
            convertView = View.inflate(context, R.layout.item_send_job, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JobKindBean jobKindBean = list.get(position);
        if (jobKindBean.isDel()) {
            holder.delTv.setVisibility(View.VISIBLE);
        } else {
            holder.delTv.setVisibility(View.GONE);
        }
        final View view = convertView;
        final int p = position;
        final int id = holder.delTv.getId();
        holder.delTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHelp.onClick(view, parent, p, id, false);
            }
        });
        return convertView;
    }

    private class ViewHolder {

        //招聘工种视图
        private TextView kindTv;
        //招工人数视图
        private EditText countEt;
        //工人工资视图
        private EditText moneyEt;
        //开始时间视图
        private TextView startTimeTv;
        //结束时间视图
        private TextView stopTimeTv;
        //删除工种视图
        private TextView delTv;

        public ViewHolder(View itemView) {
            //初始化招聘工种视图
            kindTv = (TextView) itemView.findViewById(R.id.tv_item_send_job_kind);
            //初始化招工人数视图
            countEt = (EditText) itemView.findViewById(R.id.et_item_send_job_count);
            //初始化工人工资视图
            moneyEt = (EditText) itemView.findViewById(R.id.et_item_send_job_money);
            //初始化开始时间视图
            startTimeTv = (TextView) itemView.findViewById(R.id.tv_item_send_job_start_time);
            //初始化结束时间视图
            stopTimeTv = (TextView) itemView.findViewById(R.id.tv_item_send_job_stop_time);
            //初始化删除工种视图
            delTv = (TextView) itemView.findViewById(R.id.tv_item_send_job_del);
        }
    }
}
