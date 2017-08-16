package fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import adapter.DiscountAdapter;
import bean.Discount;
import listener.OnRefreshListener;
import utils.Utils;
import view.CListView;
import view.CProgressDialog;

/**
 * 创建日期：2017/7/28 on 13:53
 * 作者:孙明明
 * 描述:优惠信息
 */

public class DiscountFragment extends Fragment implements OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView;
    private CListView listView;
    private CProgressDialog progressDialog;

    private List<Discount> discountList = new ArrayList<>();
    private DiscountAdapter discountAdapter;

    private int[] arr = {0, 1, 2, 3};

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                stopAnim();
                switch (msg.what) {
                    case 0:
                        Utils.toast(getActivity(), "网络异常");
                        break;
                    case 1:
                        notifyData();
                        break;
                    case 2:
                        hideHead();
                        break;
                    case 3:
                        hideFoot();
                        break;
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        destoryHandler();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_discount, null);
        initView();
        initData();
        setData();
        setListener();
        loadData();
        return rootView;
    }

    private void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView = (CListView) rootView.findViewById(R.id.lv_discount);
    }

    private void initDialogView() {
        progressDialog = new CProgressDialog(getActivity(), R.style.dialog_cprogress);
    }

    private void startAnim() {
        progressDialog.show();
    }

    private void stopAnim() {
        progressDialog.dismiss();
    }

    private void initData() {
        discountAdapter = new DiscountAdapter(getActivity(), discountList);
    }

    private void setData() {
        listView.setAdapter(discountAdapter);
    }

    private void setListener() {
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
    }

    private void loadData() {
        startAnim();
        Discount dc0 = new Discount();
        dc0.setTitle("百度");
        dc0.setUrl("www.baidu.com");
        Discount dc1 = new Discount();
        dc1.setTitle("淘宝");
        dc1.setUrl("www.taobao.com");
        Discount dc2 = new Discount();
        dc2.setTitle("小米");
        dc2.setUrl("www.xiaomi.com");
        Discount dc3 = new Discount();
        dc3.setTitle("华为");
        dc3.setUrl("www.huawei.com");
        discountList.add(dc0);
        discountList.add(dc1);
        discountList.add(dc2);
        discountList.add(dc3);
        handler.sendEmptyMessage(1);
    }

    private void notifyData() {
        discountAdapter.notifyDataSetChanged();
    }

    private void hideHead() {
        listView.hideHeadView();
    }

    private void hideFoot() {
        listView.hideFootView();
    }

    private void browser(String url) {
        if (!TextUtils.isEmpty(url)) {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            i.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
            startActivity(i);
        }
    }

    private void destoryHandler() {
        for (int i = 0; i < arr.length; i++) {
            handler.removeMessages(arr[i]);
        }
    }

    @Override
    public void onDownPullRefresh() {
        handler.sendEmptyMessageDelayed(2, 1000);
    }

    @Override
    public void onLoadingMore() {
        handler.sendEmptyMessageDelayed(3, 1000);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_discount:
                browser(discountList.get(position - 1).getUrl());
                break;
        }
    }
}
