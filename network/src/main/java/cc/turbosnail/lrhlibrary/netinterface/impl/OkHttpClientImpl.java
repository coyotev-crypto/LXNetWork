package cc.turbosnail.lrhlibrary.netinterface.impl;

import android.util.Log;

import java.util.concurrent.TimeUnit;
import cc.turbosnail.lrhlibrary.netinterface.OkHttpClientInterface;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 创建OKHttpClient对象
 */
public class OkHttpClientImpl implements OkHttpClientInterface {

    protected OkHttpClient mOkHttpClient;
    private static final String TAG = "OkHttpClientImpl";
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
                    mOkHttpClient = builder.build();
                }
            }
        }
        return mOkHttpClient;
    }
}
