package cc.turbosnail.lrhnethttp;

import android.app.Application;

import cc.turbosnail.lrhlibrary.base.LXHttp;

/**
 * @version V0.1.0
 * @ClassName: XpqApplication
 * @Description: application
 * @Author: 李儒浩
 * @editor 修改人
 * @Data 2021-1-32 20:00
 */
//@HiltAndroidApp
public class LrhApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LXHttp.init(new NetworkConfig());
    }
}
