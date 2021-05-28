package cc.turbosnail.lrhlibrary.net;

import cc.turbosnail.lrhlibrary.adapter.Adapter;
import cc.turbosnail.lrhlibrary.adapter.NetworkAdapter;
import cc.turbosnail.lrhlibrary.netinterface.AppHandlerInterface;
import cc.turbosnail.lrhlibrary.netinterface.IntranetBypassInterface;
import cc.turbosnail.lrhlibrary.netinterface.OkHttpClientInterface;
import cc.turbosnail.lrhlibrary.netinterface.abstracts.ParsingAnnotationAbstract;
import cc.turbosnail.lrhlibrary.netinterface.RetrofitInterface;
import cc.turbosnail.lrhlibrary.netinterface.ServiceInterface;
import cc.turbosnail.lrhlibrary.netinterface.abstracts.ThreadSwitchingAbstract;
import cc.turbosnail.lrhlibrary.netinterface.impl.AppHandlerInterfaceImpl;
import cc.turbosnail.lrhlibrary.netinterface.impl.IntranetBypassImpl;
import cc.turbosnail.lrhlibrary.netinterface.impl.OkHttpClientImpl;
import cc.turbosnail.lrhlibrary.netinterface.impl.ParsingAnnotationImpl;
import cc.turbosnail.lrhlibrary.netinterface.impl.RetrofitInterfaceImpl;
import cc.turbosnail.lrhlibrary.netinterface.impl.ThreadSwitchingImpl;
import cc.turbosnail.lrhlibrary.netinterface.impl.VariableConfig;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;

/**
 * 接口集成
 */
public class HttpClient implements ServiceInterface {

    private AppHandlerInterface mAppHandlerInterface;  //应用错误处理接口
    private IntranetBypassInterface mIntranetBypassInterface;  //内网https证书绕过
    private OkHttpClientInterface mHttpClientInterface;  //OkHttpClient对象创建
    private RetrofitInterface mRetrofitInterface; //Retrofit对象创建
    private ThreadSwitchingAbstract mSwitchingInterface; //线程切换
    private ParsingAnnotationAbstract mParsingAnnotationInterface; //注解解析
    private NetworkAdapter mAdapter; //适配器

    public void setAdapter(NetworkAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    /**
     * 默认构造方法使用内置的配置
     */
    public HttpClient() {
        this.mAppHandlerInterface = new AppHandlerInterfaceImpl();
        this.mIntranetBypassInterface = new IntranetBypassImpl();
        this.mHttpClientInterface = new OkHttpClientImpl(mAppHandlerInterface, mIntranetBypassInterface);
        this.mParsingAnnotationInterface = new ParsingAnnotationImpl();
        this.mSwitchingInterface = new ThreadSwitchingImpl(mAppHandlerInterface);
    }

    public HttpClient(Builder builder) {
        this.mAppHandlerInterface = builder.mAppHandlerInterface;
        this.mIntranetBypassInterface = builder.mIntranetBypassInterface;
        this.mHttpClientInterface = builder.mHttpClientInterface;
        this.mHttpClientInterface.setAppHandlerInterface(mAppHandlerInterface);
        this.mHttpClientInterface.setIntranetBypassInterface(mIntranetBypassInterface);
        this.mParsingAnnotationInterface = builder.mParsingAnnotationInterface;
        this.mSwitchingInterface = builder.mSwitchingInterface;
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

    /**
     * 创建服务
     *
     * @param service
     * @param <T>
     * @return
     */
    @Override
    public <T> T createService(Class<T> service) {
        if (mAdapter != null) {
            mAdapter.parsingAnnotation.parsing(service);
            VariableConfig.isNeiWaiNetWork = mAdapter.parsingAnnotation.url.contains("192.168.");
            if (this.mRetrofitInterface == null) {
                return mAdapter.createRetrofit(service).create(service);
            }
        } else {
            mParsingAnnotationInterface.parsing(service);
            VariableConfig.isNeiWaiNetWork = mParsingAnnotationInterface.url.contains("192.168.");  //内网
            if (this.mRetrofitInterface == null) {
                this.mRetrofitInterface = new RetrofitInterfaceImpl(mHttpClientInterface, mParsingAnnotationInterface);
            }
        }
        return this.mRetrofitInterface.createRetrofit(service).create(service);
    }

    @Override
    public <T> ObservableTransformer<T, T> applySchedulers(Observer<T> observer) {
        if (mAdapter != null){
            return mAdapter.applySchedulers(observer);
        }
        return mSwitchingInterface.applySchedulers(observer);
    }

    @Override
    public void cancelRequest(Observer... observer) {
        if (mAdapter != null){
            mAdapter.cancelRequest(observer);
            return;
        }
        mSwitchingInterface.cancelRequest(observer);
    }

    /**
     * 创建Builder 对象
     *
     * @return Builder 对象
     */
    public Builder Builder() {
        return new Builder();
    }


    public class Builder {
        private AppHandlerInterface mAppHandlerInterface;  //应用错误处理接口
        private IntranetBypassInterface mIntranetBypassInterface;  //内网https证书绕过
        private OkHttpClientInterface mHttpClientInterface;  //OkHttpClient对象创建
        private RetrofitInterface mRetrofitInterface; //Retrofit对象创建
        private ThreadSwitchingAbstract mSwitchingInterface; //线程切换
        private ParsingAnnotationAbstract mParsingAnnotationInterface; //注解解析

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

        public Builder setSwitchingInterface(ThreadSwitchingAbstract mSwitchingInterface) {
            this.mSwitchingInterface = mSwitchingInterface;
            return this;
        }

        public Builder setParsingAnnotationInterface(ParsingAnnotationAbstract mParsingAnnotationInterface) {
            this.mParsingAnnotationInterface = mParsingAnnotationInterface;
            return this;
        }

        public HttpClient builder() {
            return new HttpClient(this);
        }

    }
}
