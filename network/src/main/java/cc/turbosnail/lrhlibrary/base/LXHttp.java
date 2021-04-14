package cc.turbosnail.lrhlibrary.base;

import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cc.turbosnail.lrhlibrary.factory.adapter.MyTypeAdapterFactory;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @ProjectName: LXNetHttp
 * @Package: cc.turbosnail.lrhlibrary.base
 * @ClassName: LXHttp
 * @Description: java类作用描述
 * @Author: 李儒浩
 * @CreateDate: 2021/4/9 14:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/9 14:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LXHttp extends BaseHttp {

    private OkHttpClient mOkHttpClient;
    private static HashMap<String, Retrofit> retroFitHashMap = new HashMap<>();  //Retrofit对象管理

    private static LXHttp lxHttp = new LXHttp();

    public static BaseHttp getInstance() {
        return lxHttp;
    }

    /**
     * 绕过https证书验证
     *
     * @param build
     * @return
     */
    public OkHttpClient.Builder bypassHttps(OkHttpClient.Builder build) {
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


    @Override
    public Retrofit createRetrofit(Class service) {
        if (retroFitHashMap.get(mBaseUrl + service.getName()) != null) {
            return retroFitHashMap.get(mBaseUrl + service.getName());
        }
        OkHttpClient mOkHttpClient = createOkHttpClient();
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

    @Override
    public OkHttpClient createOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (this) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.hostnameVerifier((hostname, session) -> true)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS);
                if (createInterceptors() != null) {
                    for (Interceptor interceptor : createInterceptors()) {
                        builder.addInterceptor(interceptor);
                    }
                }
                if (mINetworkRequiredInf != null && mINetworkRequiredInf.isDebug()) {
                    //输出日志
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    builder.addInterceptor(httpLoggingInterceptor);
                }

                //内网绕过https
                if (isNeiWaiNetWork) {
                    builder = bypassHttps(builder);
                }
                mOkHttpClient = builder.build();
            }
        }
        return mOkHttpClient;
    }


    @Override
    public Interceptor[] createInterceptors() {
        return null;
    }

    @Override
    public <T> Function<T, T> createAppErrorHandler() {
        return null;
    }


    /***
     * 请求路径
     * @param baseUrl
     */
    public void setBaseUrl(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }
}
