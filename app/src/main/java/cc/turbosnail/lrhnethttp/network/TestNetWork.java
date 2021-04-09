package cc.turbosnail.lrhnethttp.network;

import cc.turbosnail.lrhlibrary.base.BaseHttp;
import cc.turbosnail.lrhlibrary.netinterface.INetWorkRequest;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @ProjectName: LXNetHttp
 * @Package: cc.turbosnail.lrhnethttp.network
 * @ClassName: TestNetWork
 * @Description: 自定义网络请求
 * @Author: 李儒浩
 * @CreateDate: 2021/4/9 15:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/9 15:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TestNetWork extends BaseHttp {
    private static TestNetWork testHttp = new TestNetWork();

    public static TestNetWork getInstance(){
        return testHttp;
    }
    @Override
    public Retrofit createRetrofit(Class service) {
        return null;
    }

    @Override
    public OkHttpClient createOkHttpClient() {
        return null;
    }

    @Override
    public Interceptor createInterceptor() {
        return null;
    }

    @Override
    public <T> Function<T, T> createAppErrorHandler() {
        return null;
    }

    @Override
    public OkHttpClient.Builder bypassHttps(OkHttpClient.Builder build) {
        return null;
    }
}
