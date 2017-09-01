package fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import activity.PersonMagActivity;
import adapter.PersonPrivewAdapter;
import bean.PersonPreview;
import bean.Role;
import config.NetConfig;
import config.StateConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import view.CProgressDialog;

/**
 * 创建日期：2017/8/10 on 14:30
 * 作者:孙明明
 * 描述:信息预览
 */

public class PersonMagPreviewFrag extends CommonFragment {

    //根视图
    private View rootView;
    //ListView
    private ListView listView;
    //加载对话框视图
    private CProgressDialog cPd;
    //信息预览数据类对象
    private PersonPreview personPreview;
    //信息预览数据适配器
    private PersonPrivewAdapter personPrivewAdapter;
    //okHttpClient
    private OkHttpClient okHttpClient;
    //个人管理线程
    private Handler personPreviewHandler;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case StateConfig.LOAD_NO_NET:
                        notifyNoNet();
                        break;
                    case StateConfig.LOAD_DONE:
                        notifyData();
                        break;
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(StateConfig.LOAD_NO_NET);
        handler.removeMessages(StateConfig.LOAD_DONE);
    }

    @Override
    protected View getRootView() {
        //初始化根视图
        return rootView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_person_mag_preview, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        //初始化ListView
        listView = (ListView) rootView.findViewById(R.id.lv_person_mag_preview);
    }

    private void initDialogView() {
        //初始化加载对话框视图
        cPd = new CProgressDialog(getActivity(), R.style.dialog_cprogress);
    }

    @Override
    protected void initData() {
        //初始化个人管理线程
        personPreviewHandler = ((PersonMagActivity) getActivity()).handler;
        //初始化信息预览数据类对象
        personPreview = new PersonPreview();
        //初始化信息预览数据适配器
        personPrivewAdapter = new PersonPrivewAdapter(getActivity(), personPreview);
        //初始化okHttpClient
        okHttpClient = new OkHttpClient();
    }

    @Override
    protected void setData() {
        //绑定信息预览适配器
        listView.setAdapter(personPrivewAdapter);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void loadData() {
        cPd.show();
        loadNetData();
    }

    private void loadNetData() {
        Request request = new Request.Builder().url(NetConfig.testUrl).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(StateConfig.LOAD_NO_NET);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    parseJson(result);
                }
            }
        });
    }

    private void parseJson(String json) {
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 200) {
                personPreview.setNameTitle("姓名");
                personPreview.setNameContent("王小二");
                personPreview.setSexTitle("性别");
                personPreview.setSex(true);
                personPreview.setIdNumberTitle("身份证号");
                personPreview.setIdNumberContent("230***********1234");
                personPreview.setAddressTitle("现居地");
                personPreview.setAddressContent("哈尔滨-道里区");
                personPreview.setHouseHoldTitle("户口所在地");
                personPreview.setHouseHoldContent("蓬莱");
                personPreview.setBriefTitle("个人简介");
                personPreview.setBriefContent("专业水泥工，精通水暖，刮大白");
                personPreview.setPhoneNumberTitle("手机号码（已绑定）");
                personPreview.setPhoneNumberContent("152****4859");
                personPreview.setRoleTitle("角色选择");
                personPreview.setRole(false);
                List<Role> list = new ArrayList<>();
                Role role1 = new Role();
                role1.setId("1");
                role1.setContent("水泥工");
                Role role2 = new Role();
                role2.setId("2");
                role2.setContent("水暖工");
                Role role3 = new Role();
                role3.setId("3");
                role3.setContent("瓦工");
                list.add(role1);
                list.add(role2);
                list.add(role3);
                personPreview.setRoleList(list);
                handler.sendEmptyMessage(StateConfig.LOAD_DONE);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PersonPreview", personPreview);
                Message msg = new Message();
                msg.setData(bundle);
                personPreviewHandler.sendMessage(msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void notifyNoNet() {
        cPd.dismiss();
    }

    private void notifyData() {
        cPd.dismiss();
        personPrivewAdapter.notifyDataSetChanged();
    }
}
