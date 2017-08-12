package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.EditAdapter;
import bean.Edit;
import bean.Role;

/**
 * 创建日期：2017/8/10 on 14:32
 * 作者:孙明明
 * 描述:编辑信息
 */

public class PersonManageEditFragment extends Fragment {

    private View rootView;
    private ListView listView;

    private List<Edit> editList;
    private EditAdapter editAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_person_manage_edit, null);
        initView();
        initData();
        setData();
        loadData();
        return rootView;
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        listView = (ListView) rootView.findViewById(R.id.lv_person_manage_edit);
    }

    private void initData() {
        editList = new ArrayList<>();
        editAdapter = new EditAdapter(getActivity(), editList);
    }

    private void setData() {
        listView.setAdapter(editAdapter);
    }

    private void loadData() {
        Edit edit0 = new Edit();
        Edit edit1 = new Edit();
        edit1.setMale(true);
        Edit edit2 = new Edit();
        edit2.setBirth("选择出生日期");
        Edit edit3 = new Edit();
        Edit edit4 = new Edit();
        edit4.setAddress("选择现居地");
        Edit edit5 = new Edit();
        Edit edit6 = new Edit();
        edit6.setPhoneNumber("11311414");
        Edit edit7 = new Edit();
        edit7.setWorker(true);
        Edit edit8 = new Edit();
        List<Role> list = new ArrayList<>();
        Role role0 = new Role();
        role0.setId("0");
        role0.setContent("水泥工");
        list.add(role0);
        edit8.setRoleList(list);
        editList.add(edit0);
        editList.add(edit1);
        editList.add(edit2);
        editList.add(edit3);
        editList.add(edit4);
        editList.add(edit5);
        editList.add(edit6);
        editList.add(edit7);
        editList.add(edit8);
        editAdapter.notifyDataSetChanged();
    }
}
