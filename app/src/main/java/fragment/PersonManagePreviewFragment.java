package fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.PersonPrivewAdapter;
import bean.PersonPreview;
import view.CProgressDialog;

/**
 * 创建日期：2017/8/10 on 14:30
 * 作者:孙明明
 * 描述:信息预览
 */

public class PersonManagePreviewFragment extends Fragment {

    private View rootView;
    private ListView listView;
    private CProgressDialog progressDialog;

    private List<PersonPreview> personPreviewList;
    private PersonPrivewAdapter personPrivewAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        progressDialog.dismiss();
                        PersonPreview personPreview0 = new PersonPreview();
                        personPreview0.setType(0);
                        personPreview0.setName("王小二");
                        PersonPreview personPreview1 = new PersonPreview();
                        personPreview1.setType(0);
                        personPreview1.setSex("男");
                        PersonPreview personPreview2 = new PersonPreview();
                        personPreview2.setType(0);
                        personPreview2.setBirth("1992/03/24");
                        PersonPreview personPreview3 = new PersonPreview();
                        personPreview3.setType(0);
                        personPreview3.setIdNumber("23023028402839274");
                        PersonPreview personPreview4 = new PersonPreview();
                        personPreview4.setType(0);
                        personPreview4.setAddress("哈尔滨 道外区");
                        PersonPreview personPreview5 = new PersonPreview();
                        personPreview5.setType(0);
                        personPreview5.setHousehold("哈尔滨");
                        PersonPreview personPreview6 = new PersonPreview();
                        personPreview6.setType(0);
                        personPreview6.setBrief("啥都会");
                        PersonPreview personPreview7 = new PersonPreview();
                        personPreview7.setType(0);
                        personPreview7.setPhoneNumber("166666666");
                        personPreviewList.add(personPreview0);
                        personPreviewList.add(personPreview1);
                        personPreviewList.add(personPreview2);
                        personPreviewList.add(personPreview3);
                        personPreviewList.add(personPreview4);
                        personPreviewList.add(personPreview5);
                        personPreviewList.add(personPreview6);
                        personPreviewList.add(personPreview7);
                        personPrivewAdapter.notifyDataSetChanged();
                        break;
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_person_manage_preview, null);
        initView();
        initData();
        setData();
        loadData();
        return rootView;
    }

    private void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        listView = (ListView) rootView.findViewById(R.id.lv_person_manage_preview);
    }

    private void initDialogView() {
        progressDialog = new CProgressDialog(getActivity(), R.style.dialog_cprogress);
    }

    private void initData() {
        personPreviewList = new ArrayList<>();
        personPrivewAdapter = new PersonPrivewAdapter(getActivity(), personPreviewList);
    }

    private void setData() {
        listView.setAdapter(personPrivewAdapter);
    }

    private void loadData() {
        progressDialog.show();
        handler.sendEmptyMessageDelayed(1, 500);
    }
}
