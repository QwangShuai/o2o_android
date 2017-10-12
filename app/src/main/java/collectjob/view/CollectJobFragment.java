package collectjob.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gjzg.R;

import java.util.List;

import collectjob.bean.CollectJobBean;
import collectjob.presenter.CollectJobPresenter;
import collectjob.presenter.ICollectJobPresenter;
import view.CProgressDialog;

public class CollectJobFragment extends Fragment implements ICollectJobFragment {

    private View rootView;
    private CProgressDialog cpd;
    private ICollectJobPresenter iCollectJobPresenter;

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
        if (iCollectJobPresenter != null) {
            iCollectJobPresenter.destroy();
            iCollectJobPresenter = null;
        }
    }

    private void initView() {
        cpd = new CProgressDialog(getActivity(), R.style.dialog_cprogress);
    }

    private void initData() {
        iCollectJobPresenter = new CollectJobPresenter(this);
    }

    private void loadData() {
        iCollectJobPresenter.load("2");
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
    public void showLoadSuccess(List<CollectJobBean> collectJobBeanList) {
        Log.e("CollectJob", "collectJobBeanList=" + collectJobBeanList.toString());
    }

    @Override
    public void showLoadFailure(String failure) {
        Log.e("CollectJob", "failure=" + failure);
    }
}
