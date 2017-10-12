package collectworker.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gjzg.R;

import java.util.List;

import collectworker.bean.CollectWorkerBean;
import collectworker.presenter.CollectWorkerPresenter;
import collectworker.presenter.ICollectWorkerPresenter;
import view.CProgressDialog;

public class CollectWorkerFragment extends Fragment implements ICollectWorkerFragment {

    private View rootView;
    private CProgressDialog cpd;
    private ICollectWorkerPresenter iCollectWorkerPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.common_listview, null);
        initView();
        initData();
        loadData();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (iCollectWorkerPresenter != null) {
            iCollectWorkerPresenter.destroy();
            iCollectWorkerPresenter = null;
        }
    }

    private void initView() {
        cpd = new CProgressDialog(getActivity(), R.style.dialog_cprogress);
    }

    private void initData() {
        iCollectWorkerPresenter = new CollectWorkerPresenter(this);
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
    }

    @Override
    public void showLoadFailure(String failure) {
        Log.e("CollectWorker", "failure=" + failure);
    }
}
