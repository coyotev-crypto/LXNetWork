package cc.turbosnail.lrhnethttp;

import cc.turbosnail.lrhlibrary.base.INetworkRequiredInfo;

/**
 * @version V0.1.0
 * @ClassName: NetworkConfig
 * @Description: java类作用描述
 * @Author: 李儒浩
 * @editor 修改人
 * @Data 2021-1-31 20:01
 */
public class NetworkConfig implements INetworkRequiredInfo {
    @Override
    public String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public String getAppVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
