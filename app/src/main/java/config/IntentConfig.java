package config;

/**
 * 创建日期：2017/8/25 on 9:29
 * 作者:孙明明
 * 描述:意图配置
 */

public interface IntentConfig {

    String intentName = "status";//意图名称
    int COLLECT = 1;//我的收藏意图
    int EVALUATE = 2;//我的评价意图
    int MESSAGE = 3;//我的消息意图
    String CITY = "CITY";//城市意图名称
    String LOCAL_CITY = "LOCAL_CITY";//定位城市意图名称
    int CITY_REQUEST = 4;//城市请求码
    int CITY_RESULT = 5;//城市结果码
}
