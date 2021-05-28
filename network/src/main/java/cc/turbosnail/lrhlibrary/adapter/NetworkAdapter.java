package cc.turbosnail.lrhlibrary.adapter;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import cc.turbosnail.lrhlibrary.error.HttpErrorHandler;
import cc.turbosnail.lrhlibrary.netinterface.AppHandlerInterface;
import cc.turbosnail.lrhlibrary.netinterface.IntranetBypassInterface;
import cc.turbosnail.lrhlibrary.netinterface.ParsingAnnotation;
import cc.turbosnail.lrhlibrary.netinterface.RetrofitInterface;
import cc.turbosnail.lrhlibrary.netinterface.ThreadSwitching;
import cc.turbosnail.lrhlibrary.netinterface.impl.IntranetBypassImpl;
import cc.turbosnail.lrhlibrary.netinterface.impl.ParsingAnnotationImpl;
import cc.turbosnail.lrhlibrary.netinterface.impl.RetrofitInterfaceImpl;
import cc.turbosnail.lrhlibrary.netinterface.impl.ThreadSwitchingImpl;
import cc.turbosnail.lrhlibrary.netinterface.impl.VariableConfig;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @ProjectName LXNetHttp
 * @Package cc.turbosnail.lrhlibrary.adapter
 * @Describe 网络适配器
 * @Author wolf king
 * @editor 修改人
 * @CreateDate 2021/5/28
 * @UpdateDate 2021/5/28 更新时间
 */
public abstract class NetworkAdapter implements Adapter {
    public ParsingAnnotationImpl parsingAnnotation = new ParsingAnnotationImpl();
    public ThreadSwitching threadSwitching = new ThreadSwitchingImpl(null);
    public IntranetBypassInterface bypassInterface = new IntranetBypassImpl();

    @Override
    public OkHttpClient.Builder bypassHttps(OkHttpClient.Builder build) {
        return bypassInterface.bypassHttps(build);
    }

    public abstract OkHttpClient createOkHttpClient();

    public abstract Retrofit createRetrofit(Class service);

    @Override
    public <T> T createService(Class<T> service) {
        parsingAnnotation.parsing(service);
        VariableConfig.isNeiWaiNetWork = parsingAnnotation.url.contains("192.168.");  //内网
        return createRetrofit(service).create(service);
    }

    @Override
    public <T> ObservableTransformer<T, T> applySchedulers(Observer<T> observer) {
        return threadSwitching.applySchedulers(observer);
    }

    @Override
    public void cancelRequest(Observer... observer) {
        threadSwitching.cancelRequest(observer);
    }
}
