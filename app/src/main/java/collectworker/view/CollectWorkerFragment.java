package collectworker.view;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import collectworker.adapter.CollectWorkerAdapter;
import collectworker.bean.CollectWorkerBean;
import collectworker.presenter.CollectWorkerPresenter;
import collectworker.presenter.ICollectWorkerPresenter;
import listener.ListItemClickHelp;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import utils.Utils;
import view.CProgressDialog;

public class CollectWorkerFragment extends Fragment implements ICollectWorkerFragment, ListItemClickHelp, PullToRefreshLayout.OnRefreshListener {

    private View rootView;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private CProgressDialog cpd;
    private List<CollectWorkerBean> list;
    private CollectWorkerAdapter adapter;

    private ICollectWorkerPresenter iCollectWorkerPresenter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                if (msg.what == 1) {
                    adapter.notifyDataSetChanged();
                }
            }
        }
    };

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
        if (iCollectWorkerPresenter != null) {
            iCollectWorkerPresenter.destroy();
            iCollectWorkerPresenter = null;
        }
    }

    private void initView() {
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
        cpd = Utils.initProgressDialog(getActivity(), cpd);
    }

    private void initData() {
        iCollectWorkerPresenter = new CollectWorkerPresenter(this);
        list = new ArrayList<>();
        adapter = new CollectWorkerAdapter(getActivity(), list, this);
    }

    private void setData() {
        plv.setAdapter(adapter);
    }

    private void setListener() {
        ptrl.setOnRefreshListener(this);
    }

    private void loadData() {
        iCollectWorkerPresenter.load("2");
    }

    @Override
    public void showLoading() {
        cpd.show();
    }

    @Override
    public void hideLoading() {
        cpd.dismiss();
    }

    @Override
    public void showLoadSuccess(List<CollectWorkerBean> collectWorkerBeanList) {
        Log.e("CollectWorker", "collectWorkerBeanList=" + collectWorkerBeanList.toString());
        list.addAll(collectWorkerBeanList);
        handler.sendEmptyMessage(1);
    }

    @Override
    public void showLoadFailure(String failure) {
        Log.e("CollectWorker", "failure=" + failure);
    }

    @Override
    public void onClick(View item, View widget, int position, int which, boolean isChecked) {

    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

    }
}
