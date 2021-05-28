package cc.turbosnail.lrhlibrary.netinterface.impl;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cc.turbosnail.lrhlibrary.netinterface.AppHandlerInterface;
import cc.turbosnail.lrhlibrary.netinterface.IntranetBypassInterface;
import cc.turbosnail.lrhlibrary.netinterface.OkHttpClientInterface;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 创建OKHttpClient对象
 */
public class OkHttpClientImpl implements OkHttpClientInterface {

    protected OkHttpClient mOkHttpClient;
    private static final String TAG = "OkHttpClientImpl";
    protected AppHandlerInterface mAppHandlerInterface;
    protected IntranetBypassInterface mIntranetBypassInterface;

    public OkHttpClientImpl(AppHandlerInterface mAppHandlerInterface, IntranetBypassInterface mIntranetBypassInterface) {
        this.mAppHandlerInterface = mAppHandlerInterface;
        this.mIntranetBypassInterface = mIntranetBypassInterface;
    }


    public void setAppHandlerInterface(AppHandlerInterface mAppHandlerInterface) {
        this.mAppHandlerInterface = mAppHandlerInterface;
    }

    public void setIntranetBypassInterface(IntranetBypassInterface mIntranetBypassInterface) {
        this.mIntranetBypassInterface = mIntranetBypassInterface;
    }

    @Override
    public OkHttpClient createOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (OkHttpClientImpl.class) {
                if (mOkHttpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.hostnameVerifier((hostname, session) -> true)
                            .readTimeout(10, TimeUnit.SECONDS)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS);
                    if (VariableConfig.isDebug) {
                        //输出日志
                        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(httpLoggingInterceptor);
                    }
                    if (mAppHandlerInterface != null && mAppHandlerInterface.createInterceptors() != null) {
                        for (Interceptor interceptor : mAppHandlerInterface.createInterceptors()) {
                            builder.addInterceptor(interceptor);
                        }
                    }
                    //内网绕过https
                    if (VariableConfig.isNeiWaiNetWork || mIntranetBypassInterface != null) {
                        builder = mIntranetBypassInterface.bypassHttps(builder);
                    }
                    mOkHttpClient = builder.build();
                }
            }
        }
        return mOkHttpClient;
    }
}
