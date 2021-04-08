package cc.turbosnail.lrhlibrary.base;

/**
 * @version V0.1.0
 * @ClassName: INetworkRequiredInfo
 * @Description: 网络请求是否输出日志
 * @Author: 李儒浩
 * @editor 修改人
 * @Data 2021-1-25 21:43
 */
public interface INetworkRequiredInfo {
    String getAppVersionName();
    String getAppVersionCode();
    boolean isDebug();
}
