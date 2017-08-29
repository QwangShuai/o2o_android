package listener;

import android.view.View;

/**
 * 创建日期：2017/8/28 on 16:10
 * 作者:孙明明
 * 描述:信息编辑回调接口
 */

public interface EditClickHelp {
    void onClick(View view,View parent,int position,int id,String hasInput);
}
