package cc.turbosnail.lrhlibrary;

import cc.turbosnail.lrhlibrary.base.BaseHttp;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;

/**
 * @version V0.1.0
 * @ClassName: XpqHttp
 * @Description: 默认的网络请求类
 * @Author: 李儒浩
 * @editor 修改人
 * @Data 2021-1-25 22:02
 */
public class LrhHttp extends BaseHttp {


    private static volatile LrhHttp mInstance;
    public static LrhHttp getInstance() {
        if (mInstance == null) {
            synchronized (LrhHttp.class) {
                if (mInstance == null) {
                    mInstance = new LrhHttp();
                }
            }
        }
        return mInstance;
    }

    public static <T> T getService(Class<T> servlce) {
        return getInstance().getBaseService(servlce);
    }

    @Override
    protected Interceptor getInterceptor() {
        return null;
    }

    @Override
    protected <T> Function<T, T> getAppErrorHandler() {
        return null;
    }
}
