package config;

//变量
public interface VarConfig {

    String onlineTip = "公开信息";
    String offlineTip = "隐藏信息";
    long exitTime = 3000;//退出计时(单位：毫秒)
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
    int GET_LOGIN_CODE_DOWN = 5;//获取登录验证码倒计时时间(单位：秒)
    String pwdResendTip = "重新发送";//重新发送提示
    String phoneErrorTip = "手机号格式不正确";//验证手机号格式
    String getPwdTip = "获取动态密码";//获取动态密码提示
    int WYZG = 0;//我要招工状态
    int YYQQ = 1;//邀约请求已发送
    int QRKG = 2;//确认开工状态
    int DDGR = 3;//等待工人确认
    int GCJS = 4;//工程结束
    int QXGR = 5;//取消工人
    int qrkg = 6;//确认开工
    int jggr = 7;//解雇工人
    int qrwg = 8;//确认完工
    String wyzgTip = "我要招工";
    String yyqqTip = "邀约请求已发送";
    String qxgrHint = "不合适的话可以取消该工人取消后，您将无法与Ta联系";
    String qxgrTip = "取消工人";
    String qrkgHint = "请确认工人到岗并确认工期和工资，然后再确认开工";
    String qrkgTip = "确认开工";
    String ddgrTip = "等待工人确认";
    String jggrTip = "解雇工人";
    String jggrHint = "解雇工人后，系统将按已完成的工时计算工人工资并支付给工人";
    String gcjsTip = "工程结束";
    String gcjsHint = "确认工程结束后，系统将把预付的工资支付给工人";
}
