package cc.turbosnail.lrhlibrary.base;

import android.annotation.SuppressLint;

import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cc.turbosnail.lrhlibrary.annotation.BaseUrl;
import cc.turbosnail.lrhlibrary.annotation.TestUrl;
import cc.turbosnail.lrhlibrary.error.HttpErrorHandler;
import cc.turbosnail.lrhlibrary.factory.adapter.MyTypeAdapterFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @version V0.1.0
 * @ClassName: BaseHttp
 * @Description: 网络请求基类
 * @Author: 李儒浩
 * @editor 修改人
 * @Data 2021-1-25 21:18
 */
public abstract class BaseHttp {
    private static INetworkRequiredInfo mINetworkRequiredInf;  //是否输出日志
    private OkHttpClient mOkHttpClient;
    private String mBaseUrl;
    private static HashMap<String, Retrofit> retroFitHashMap = new HashMap<>();  //Retrofit对象管理
    private HttpErrorHandler tHttpErrorHandler = new HttpErrorHandler<>();

    public static boolean isNeiWaiNetWork = false;  //true 为内网 false为外网

    /***
     *  启动应用就初始化
     * @param iNetworkRequiredInf
     */
    public static void init(INetworkRequiredInfo iNetworkRequiredInf) {
        mINetworkRequiredInf = iNetworkRequiredInf;
    }


    protected Retrofit getRetrofit(Class service) {
        OkHttpClient mOkHttpClient = getOkHttpClient();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(mOkHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapterFactory(new MyTypeAdapterFactory<>()).create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retroFitHashMap.put(mBaseUrl + service.getName(), mRetrofit);
        return mRetrofit;
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (this) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.hostnameVerifier((hostname, session) -> true)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS);
                if (getInterceptor() != null) {
                    builder.addInterceptor(getInterceptor());
                }
                if (mINetworkRequiredInf != null && mINetworkRequiredInf.isDebug()) {
                    //输出日志
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    builder.addInterceptor(httpLoggingInterceptor);
                }
//                builder.addInterceptor(new CommonHeaderInterceptor()) //添加请求头
//                builder.addInterceptor(new CommonResponseIntercept()) //
                //内网绕过https
                if (isNeiWaiNetWork) {
                    builder = bypassHttps(builder);
                }
                mOkHttpClient = builder.build();
            }
        }
        return mOkHttpClient;
    }

    /**
     * 绕过https证书验证
     *
     * @param build
     * @return
     */
    private OkHttpClient.Builder bypassHttps(OkHttpClient.Builder build) {
        try {
            TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManagers, new SecureRandom());
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            build.sslSocketFactory(socketFactory);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return build;
    }

    /***
     * 线程调度
     * @param observer
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @SuppressLint("CheckResult")
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable observable = upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorResumeNext(tHttpErrorHandler);
                if (getAppErrorHandler() != null) {
                    observable.map(getAppErrorHandler());
                }
                observable.subscribe(observer);
                return observable;
            }
        };
    }

    /**
     * 获取服务对象
     *
     * @param servlce 请求api对象
     * @param <T>
     * @return Retrofit
     */
    protected synchronized <T> T getBaseService(Class<T> servlce) {
        //先判断BaseUrl是否存在
        if (null == mBaseUrl) {
            if (parsingAnnotation(servlce)) {
                //如果不存在那就查找父类是否存在该注解
                parsingInterfacesAnnotation(servlce);
            }
        }
        //初始化请求对象
        if (mBaseUrl == null) {
            throw new NullPointerException("BaseUrl is null, the solution is to add @BaseUrl or @TestUrl to the class");
        }

        isNeiWaiNetWork = mBaseUrl.contains("192.168.");  //内网

        return getRetrofit(servlce).create(servlce);
    }


    /**
     * 注解解析
     */
    public boolean parsingAnnotation(Class clazz) {
        BaseUrl baseAnnotation = (BaseUrl) clazz.getAnnotation(BaseUrl.class);  //外网
        TestUrl testAnnotation = (TestUrl) clazz.getAnnotation(TestUrl.class); //内网
        if (baseAnnotation != null) {
            mBaseUrl = baseAnnotation.value();
            return true;
        } else if (testAnnotation != null) {
            mBaseUrl = testAnnotation.value();
            return true;
        }
        return false;
    }

    public void parsingInterfacesAnnotation(Class clazz) {
        Class[] interfaces = clazz.getInterfaces();
        for (Class anInterface : interfaces) {
            if (parsingAnnotation(anInterface))
                return;
        }

    }


    protected abstract Interceptor getInterceptor();

    protected abstract <T> Function<T, T> getAppErrorHandler();

    /***
     * 请求路径
     * @param baseUrl
     */
    public void setBaseUrl(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

}
