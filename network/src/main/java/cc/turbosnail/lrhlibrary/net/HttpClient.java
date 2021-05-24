package cc.turbosnail.lrhlibrary.net;

import android.util.Log;

import cc.turbosnail.lrhlibrary.netinterface.AppHandlerInterface;
import cc.turbosnail.lrhlibrary.netinterface.IntranetBypassInterface;
import cc.turbosnail.lrhlibrary.netinterface.OkHttpClientInterface;
import cc.turbosnail.lrhlibrary.netinterface.ParsingAnnotationInterface;
import cc.turbosnail.lrhlibrary.netinterface.RetrofitInterface;
import cc.turbosnail.lrhlibrary.netinterface.ServiceInterface;
import cc.turbosnail.lrhlibrary.netinterface.ThreadSwitchingInterface;
import cc.turbosnail.lrhlibrary.netinterface.impl.AppHandlerInterfaceImpl;
import cc.turbosnail.lrhlibrary.netinterface.impl.IntranetBypassImpl;
import cc.turbosnail.lrhlibrary.netinterface.impl.OkHttpClientImpl;
import cc.turbosnail.lrhlibrary.netinterface.impl.ParsingAnnotationImpl;
import cc.turbosnail.lrhlibrary.netinterface.impl.RetrofitInterfaceImpl;
import cc.turbosnail.lrhlibrary.netinterface.impl.ThreadSwitchingImpl;
import cc.turbosnail.lrhlibrary.netinterface.impl.VariableConfig;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * 接口集成
 */
public class HttpClient implements ServiceInterface {
    private static HttpClient mHttpClient;
    private AppHandlerInterface mAppHandlerInterface;  //应用错误处理接口
    private IntranetBypassInterface mIntranetBypassInterface;  //内网https证书绕过
    private OkHttpClientInterface mHttpClientInterface;  //OkHttpClient对象创建
    private RetrofitInterface mRetrofitInterface; //Retrofit对象创建
    private ThreadSwitchingInterface mSwitchingInterface; //线程切换
    private ParsingAnnotationInterface mParsingAnnotationInterface; //注解解析

    public static HttpClient getInstance() {
        if (mHttpClient == null) {
            mHttpClient = new HttpClient();
        }
        return mHttpClient;
    }

    /**
     * 默认构造方法使用内置的配置
     */
    private HttpClient() {
        mAppHandlerInterface = new AppHandlerInterfaceImpl();
        mIntranetBypassInterface = new IntranetBypassImpl();
        mHttpClientInterface = new OkHttpClientImpl();
        mParsingAnnotationInterface = new ParsingAnnotationImpl();
        initHttpClient();
        mSwitchingInterface = new ThreadSwitchingImpl(mAppHandlerInterface);
    }

    public HttpClient(Builder builder) {
        mAppHandlerInterface = builder.mAppHandlerInterface;
        mIntranetBypassInterface = builder.mIntranetBypassInterface;
        mHttpClientInterface = builder.mHttpClientInterface;
        mParsingAnnotationInterface = builder.mParsingAnnotationInterface;
        initHttpClient();
        mSwitchingInterface = builder.mSwitchingInterface;
    }

    private static final String TAG = "HttpClient";

    public Builder newBuilder() {
        return new Builder(this);
    }

    /**
     * 是否打印日志
     *
     * @param isDebug
     */
    public static void init(boolean isDebug) {
        VariableConfig.isDebug = isDebug;
    }

    /***
     * 初始化okhttp对象
     */
    private void initHttpClient() {
        OkHttpClient.Builder builder = mHttpClientInterface.createOkHttpClient().newBuilder();
        if (mAppHandlerInterface != null && mAppHandlerInterface.createInterceptors() != null) {
            for (Interceptor interceptor : mAppHandlerInterface.createInterceptors()) {
                builder.addInterceptor(interceptor);
            }
        }
        //内网绕过https
        if (VariableConfig.isNeiWaiNetWork || mIntranetBypassInterface != null) {
            mIntranetBypassInterface.bypassHttps(builder);
        }
    }

    @Override
    public <T> T createService(Class<T> service) {
        mParsingAnnotationInterface.parsing(service);

        VariableConfig.isNeiWaiNetWork = mParsingAnnotationInterface.url.contains("192.168.");  //内网
        if (mRetrofitInterface == null) {
            mRetrofitInterface = new RetrofitInterfaceImpl(mHttpClientInterface, mParsingAnnotationInterface);
        }
        return mRetrofitInterface.createRetrofit(service).create(service);
    }

    @Override
    public <T> ObservableTransformer<T, T> applySchedulers(Observer<T> observer) {
        return mSwitchingInterface.applySchedulers(observer);
    }

    @Override
    public void cancelRequest(Observer... observer) {
        mSwitchingInterface.cancelRequest(observer);
    }

    /**
     * 创建Builder 对象
     *
     * @return Builder 对象
     */
    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {
        private AppHandlerInterface mAppHandlerInterface;  //应用错误处理接口
        private IntranetBypassInterface mIntranetBypassInterface;  //内网https证书绕过
        private OkHttpClientInterface mHttpClientInterface;  //OkHttpClient对象创建
        private RetrofitInterface mRetrofitInterface; //Retrofit对象创建
        private ThreadSwitchingInterface mSwitchingInterface; //线程切换
        private ParsingAnnotationInterface mParsingAnnotationInterface; //注解解析

        public Builder(HttpClient httpClient) {
            this.mAppHandlerInterface = httpClient.mAppHandlerInterface;
            this.mIntranetBypassInterface = httpClient.mIntranetBypassInterface;
            this.mHttpClientInterface = httpClient.mHttpClientInterface;
            this.mRetrofitInterface = httpClient.mRetrofitInterface;
            this.mSwitchingInterface = httpClient.mSwitchingInterface;
            this.mParsingAnnotationInterface = httpClient.mParsingAnnotationInterface;
        }

        public Builder() {
        }

        public Builder setAppHandlerInterface(AppHandlerInterface mAppHandlerInterface) {
            this.mAppHandlerInterface = mAppHandlerInterface;
            return this;
        }

        public Builder setIntranetBypassInterface(IntranetBypassInterface mIntranetBypassInterface) {
            this.mIntranetBypassInterface = mIntranetBypassInterface;
            return this;
        }

        public Builder setHttpClientInterface(OkHttpClientInterface mHttpClientInterface) {
            this.mHttpClientInterface = mHttpClientInterface;
            return this;
        }

        public Builder setRetrofitInterface(RetrofitInterface mRetrofitInterface) {
            this.mRetrofitInterface = mRetrofitInterface;
            return this;
        }

        public Builder setSwitchingInterface(ThreadSwitchingInterface mSwitchingInterface) {
            this.mSwitchingInterface = mSwitchingInterface;
            return this;
        }

        public Builder setParsingAnnotationInterface(ParsingAnnotationInterface mParsingAnnotationInterface) {
            this.mParsingAnnotationInterface = mParsingAnnotationInterface;
            return this;
        }

        public HttpClient builder() {
            return new HttpClient(this);
        }
    }
}
