package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gjzg.R;

/**
 * 创建日期：2017/8/10 on 14:34
 * 作者:孙明明
 * 描述:投递记录
 */

public class PersonManageRecordFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_person_manage_record, null);
        return rootView;
    }
}
