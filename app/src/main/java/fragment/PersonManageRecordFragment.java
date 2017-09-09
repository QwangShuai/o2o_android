package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.PersonAdapter;
import bean.Person;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import view.CProgressDialog;

/**
 * 创建日期：2017/8/10 on 14:34
 * 作者:孙明明
 * 描述:投递记录
 */

public class PersonManageRecordFragment extends Fragment {

    //根视图
    private View rootView;
    //无网络空视图
    private LinearLayout noNetLl;
    private TextView noNetTv;
    //无数据空视图
    private LinearLayout noDataLl;
    //刷新布局视图
    private PullToRefreshLayout pTrl;
    //刷新ListView视图
    private PullableListView pLv;
    //加载对话框视图
    private CProgressDialog cPd;

    //投递记录数据集合
    private List<Person> recordList;
    //投递记录数据适配器
    private PersonAdapter recordAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_msg, null);
        initView();
        initData();
        setData();
        loadData();
        return rootView;
    }

    private void initView(){
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        //初始化无网络空视图
        noNetLl = (LinearLayout) rootView.findViewById(R.id.ll_no_net);
        noNetTv = (TextView) rootView.findViewById(R.id.tv_empty_no_net_refresh);
        //初始化无数据空视图
        noDataLl = (LinearLayout) rootView.findViewById(R.id.ll_no_data);
        //初始化刷新布局视图
        pTrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl_msg);
        //初始化刷新ListView视图
        pLv = (PullableListView) rootView.findViewById(R.id.plv_msg);
    }

    private void initDialogView(){
        //初始化加载对话框
    }

    private void initData(){
        //初始化投递记录数据集合
        recordList = new ArrayList<>();
        //初始化投递记录数据适配器
        recordAdapter = new PersonAdapter(getActivity(),recordList);
    }

    private void setData(){
        //绑定投递记录数据适配器
        pLv.setAdapter(recordAdapter);
    }

    private void loadData(){

    }
}
