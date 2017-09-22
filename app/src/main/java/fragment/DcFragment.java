package fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.DcAdapter;
import bean.DcBean;
import config.StateConfig;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import utils.Utils;

//优惠
public class DcFragment extends CommonFragment implements PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<DcBean> list;
    private DcAdapter adapter;
    private int state = StateConfig.LOAD_DONE;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_discount, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new DcAdapter(getActivity(), list);
    }

    @Override
    protected void setData() {
        plv.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        ptrl.setOnRefreshListener(this);
        plv.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        DcBean d0 = new DcBean();
        d0.setTitle("优惠一");
        d0.setUrl("http://www.toutiao.com/");
        DcBean d1 = new DcBean();
        d1.setTitle("优惠二");
        d1.setUrl("http://www.toutiao.com/");
        DcBean d2 = new DcBean();
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
}
