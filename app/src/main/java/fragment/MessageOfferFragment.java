package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gjzg.R;

/**
 * 创建日期：2017/8/8 on 11:06
 * 作者:孙明明
 * 描述:工作邀约
 */

public class MessageOfferFragment extends Fragment {

    private View rootView;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_message, null);
        initView();
        loadData();
        return rootView;
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        textView = (TextView) rootView.findViewById(R.id.tv_message_content);
    }

    private void loadData() {
        textView.setText("工作邀约");
    }
}
