package listener;

/**
 * 创建日期：2017/7/28 on 13:41
 * 作者:孙明明
 * 描述:自定义ListView的滑动监听
 */

public interface OnRefreshListener {

    void onDownPullRefresh();

    void onLoadingMore();
}
