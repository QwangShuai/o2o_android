package config;

/**
 * 创建日期：2017/8/16 on 10:27
 * 作者:孙明明
 * 描述:状态配置
 */

public interface StateConfig {

    //加载状态
    public static final int LOAD_NO_NET = 0;
    public static final int LOAD_DONE = 1;
    public static final int LOAD_REFRESH = 2;
    public static final int LOAD_LOAD = 3;

    //工人状态
    public static final int LEISURE = 0;
    public static final int WAIT = 1;
    public static final int TALKING = 2;
    public static final int WORKING = 3;
    public static final int OVER = 4;

    //加载结果
    public static final String loadNonet = "网络异常";
    public static final String loadRefreshSuccess = "刷新成功";
    public static final String loadRefreshFailure = "刷新失败，请检查网络设置";
    public static final String loadLoadSuccess = "加载成功";
    public static final String loadLoadFailure = "加载失败，请检查网络设置";

    //客服电话
    public static final String cusSevNumber = "4000788889";

    //获取动态密码间隔时间（秒）
    public static final int getMovePwdSec = 3;
}
