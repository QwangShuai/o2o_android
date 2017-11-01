package userinfo.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gjzg.R;

import userinfo.adapter.UserInfoAdapter;
import usermanage.bean.UserInfoBean;

public class UserInfoFragment extends Fragment {

    private View rootView;
    private ListView lv;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                Bundle bundle = msg.getData();
                if (bundle != null) {
                    UserInfoBean userInfoBean = (UserInfoBean) bundle.getSerializable("userInfoBean");
                    if (userInfoBean != null) {
                        Log.e("UserInfoFragment", "userInfoBean=" + userInfoBean.toString());
                        notifyData(userInfoBean);
                    }
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_userinfo, null);
        initView();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler = null;
        }
    }

    private void initView() {
        lv = (ListView) rootView.findViewById(R.id.lv_fragment_userinfo);
    }

    private void notifyData(UserInfoBean userInfoBean) {
        if (userInfoBean != null) {
            UserInfoAdapter adapter = new UserInfoAdapter(getActivity(), userInfoBean);
            lv.setAdapter(adapter);
        }
    }
}
