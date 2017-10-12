package discount.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.DcAdapter;
import discount.bean.DiscountBean;
import config.StateConfig;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import utils.Utils;

public class DiscountFragment extends Fragment implements IDiscountFragment, PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<DiscountBean> list;
    private DcAdapter adapter;
    private int state = StateConfig.LOAD_DONE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_discount, null);
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
        adapter = new DcAdapter(getActivity(), list);
    }

    private void setData() {
        plv.setAdapter(adapter);
    }

    private void setListener() {
        ptrl.setOnRefreshListener(this);
        plv.setOnItemClickListener(this);
    }

    private void loadData() {
        DiscountBean d0 = new DiscountBean();
        d0.setTitle("优惠一");
        d0.setUrl("http://www.toutiao.com/");
        DiscountBean d1 = new DiscountBean();
        d1.setTitle("优惠二");
        d1.setUrl("http://www.toutiao.com/");
        DiscountBean d2 = new DiscountBean();
        d2.setTitle("优惠三");
        d2.setUrl("http://www.toutiao.com/");
        list.add(d0);
        list.add(d1);
        list.add(d2);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String url = list.get(position).getUrl();
        if (!TextUtils.isEmpty(url))
            Utils.skipBrowser(getActivity(), url);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
