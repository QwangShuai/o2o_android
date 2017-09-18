package config;

//变量
public interface VarConfig {

    long exitTime = 3000;//退出计时(单位：毫秒)
    String exitTip = "再次点击退出";//退出提示
    String notyetTip = "该功能暂未开通";//暂未开通提示
    int PWD_FIRST = 0;//第一次设置密码
    int PWD_AGAIN = 1;//二次密码验证
    int PWD_ORIGIN = 2;//第二次设置密码
    int PWD_NEW = 3;//设置新密码
    String pwdFirstTip = "设置六位提现密码";//第一次设置密码提示
    String pwdAgainTip = "请在输入一次";//二次密码验证提示
    String pwdOriginTip = "输入原有提现密码";//第二次设置密码提示
    String pwdNewTip = "设置新的提现密码";//设置新密码
    String pwdSuccessTip = "设置密码成功";//设置密码成功提示
    String pwdDifferTip = "两次密码不一致";//两次密码不一致提示
    String pwdErrorTip = "原密码不正确";//原密码不正确
    int PWD_COUNT_DOWN = 60;//重新设置密码手机验证码倒计时(单位：秒)
    String pwdResendTip = "重新发送";//重新发送提示
    String phoneErrorTip = "手机号格式不正确";//验证手机号格式
    String getPwdTip = "获取动态密码";//获取动态密码提示
    int WYZG = 0;//我要招工状态
    int YYQQ = 1;//邀约请求已发送
    int QRKG = 2;//确认开工状态
    int DDGR = 3;//等待工人确认
    int GGJS = 4;//工程结束
}
