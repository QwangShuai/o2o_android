package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.MsgAdapter;
import bean.MsgBean;
import config.StateConfig;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;

//系统消息
public class SysMsgFragment extends Fragment implements PullToRefreshLayout.OnRefreshListener {

    private View rootView;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<MsgBean> list;
    private MsgAdapter adapter;
    private int state = StateConfig.LOAD_DONE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.common_listview, null);
        initView();
        initData();
        setData();
        setListener();
        loadData();
        return rootView;
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new MsgAdapter(getActivity(), list);
    }

    private void setData() {
        plv.setAdapter(adapter);
    }

    private void setListener() {
        ptrl.setOnRefreshListener(this);
    }

    private void loadData() {
        MsgBean sm0 = new MsgBean();
        sm0.setTitle("系统消息");
        sm0.setDate("2017/03/05");
        sm0.setDes("系统升级，钢建众工2.0版本全新上市，更多高薪工作等你来");
        sm0.setArrowShow(false);
        MsgBean sm1 = new MsgBean();
        sm1.setTitle("系统消息");
        sm1.setDate("2017/03/04");
        sm1.setDes("你有新的红包等待领取");
        sm1.setArrowShow(false);
        list.add(sm0);
        list.add(sm1);
        adapter.notifyDataSetChanged();
        switch (state) {
            case StateConfig.LOAD_REFRESH:
                ptrl.hideHeadView();
                break;
            case StateConfig.LOAD_LOAD:
                ptrl.hideFootView();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        list.clear();
        state = StateConfig.LOAD_REFRESH;
        loadData();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        state = StateConfig.LOAD_LOAD;
        loadData();
    }
}
