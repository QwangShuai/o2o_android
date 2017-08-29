package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.gjzg.R;

import activity.PersonManageActivity;
import adapter.EditAdapter;
import bean.PersonPreview;
import listener.EditClickHelp;
import utils.Utils;

/**
 * 创建日期：2017/8/10 on 14:32
 * 作者:孙明明
 * 描述:编辑信息
 */

public class PersonManageEditFragment extends Fragment implements EditClickHelp{

    private View rootView;
    private ListView listView;

    //尾视图
    private View editFootView;
    private TextView editFootSumitTv;

    private PersonPreview personPreview;
    private EditAdapter editAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_person_manage_edit, null);
        initView();
        initData();
        setData();
        return rootView;
    }

    private void initView() {
        initRootView();
        initFootView();
    }

    private void initRootView() {
        listView = (ListView) rootView.findViewById(R.id.lv_person_manage_edit);
    }

    private void initFootView() {
        editFootView = View.inflate(getActivity(), R.layout.foot_person_edit, null);
        editFootSumitTv = (TextView) editFootView.findViewById(R.id.tv_foot_person_edit_submit);
        editFootSumitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toast(getActivity(), personPreview.toString());
            }
        });
        listView.addFooterView(editFootView);
    }

    private void initData() {
        personPreview = ((PersonManageActivity) getActivity()).personPreview;
        Utils.toast(getActivity(), personPreview.toString());
        editAdapter = new EditAdapter(getActivity(), personPreview,this);
    }

    private void setData() {
        listView.setAdapter(editAdapter);
    }

    @Override
    public void onClick(View view, View parent, int position, int id, String hasInput) {
        switch (id){
            //et
            case R.id.et_item_person_manage_edit_type_2_content:
                switch (position){
                    case 0:
                        personPreview.setNameContent(hasInput);
                        break;
                    case 2:
                        personPreview.setIdNumberContent(hasInput);
                        break;
                    case 4:
                        personPreview.setBriefContent(hasInput);
                        break;
                }
                editAdapter.notifyDataSetChanged();
                Utils.toast(getActivity(),personPreview.toString());
                break;
        }
    }
}
