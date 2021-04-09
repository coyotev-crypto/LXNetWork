package cc.turbosnail.lrhlibrary.netinterface;

import java.util.List;

import cc.turbosnail.lrhlibrary.base.INetworkRequiredInfo;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @ProjectName: LXNetHttp
 * @Package: cc.turbosnail.lrhlibrary.netinterface
 * @ClassName: INetWorkRequest
 * @Description: 网络请求接口
 * @Author: 李儒浩
 * @CreateDate: 2021/4/9 14:26
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/9 14:26
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface INetWorkRequest {

    Retrofit createRetrofit(Class service);  //构建Retrofit
    OkHttpClient createOkHttpClient();          //构建OkHttpClient
    <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer);  //线程切换
    <T> T createService(Class<T> service);  //服务对象
    boolean parsingAnnotation(Class clazz);   //解析当前类注解
    void parsingInterfacesAnnotation(Class clazz);  //解析父类注解
    Interceptor[] createInterceptors();        //拦截
    <T> Function<T, T> createAppErrorHandler();  //应用错误
    OkHttpClient.Builder bypassHttps(OkHttpClient.Builder build); //内网绕过
}
